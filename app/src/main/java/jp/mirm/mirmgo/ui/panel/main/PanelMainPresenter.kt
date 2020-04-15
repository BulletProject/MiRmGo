package jp.mirm.mirmgo.ui.panel.main

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.ui.panel.dialog.ExtendDialog
import kotlinx.coroutines.*

class PanelMainPresenter(private val fragment: PanelMainFragment) : AbstractPresenter() {

    private lateinit var rssFeeds: MutableMap<String, String>

    fun onUpdate() = GlobalScope.launch (Dispatchers.Main) {
        PanelFragment.getInstance().setRefreshButtonEnabled(false)
        PanelFragment.getInstance().setProgressBarIndetermined(true)
        PanelFragment.getInstance().setTime("-/-分")
        fragment.setIPAddress("-")
        fragment.setPort("-")
        fragment.setStatusEnabled(false)

        GlobalScope.async(Dispatchers.Default) {
            rssFeeds = MiRmAPI.getLatestRSSFeeds()
            MiRmAPI.getServerData()

        }.await().let {
            if (it == null) {
                changeFragment(fragment.childFragmentManager, LoginFragment.newInstance())
                return@launch
            }

            PanelFragment.getInstance().onTimeUpdate(it.time)
            PanelFragment.getInstance().setRefreshButtonEnabled(true)
            PanelFragment.getInstance().setProgressBarIndetermined(false)
            PanelFragment.getInstance().setProgressBarValue(it.time, 600)
            PanelFragment.getInstance().setTime("${it.time}/600分")
            fragment.setIPAddress(it.ip)
            fragment.setPort(it.port.toString())
            fragment.setStatusEnabled(true)
            fragment.setRSSListContents(rssFeeds.keys.toList())

            when (it.serverStatus) {
                true -> {
                    fragment.setStatus(R.string.status_running, R.color.pureApple)
                    fragment.setStatusChecked(true)
                }
                false -> {
                    fragment.setStatus(R.string.status_stopped, R.color.carminePink)
                    fragment.setStatusChecked(false)
                }
            }
        }
    }

    fun onJoinButtonClick() {

    }

    fun onOpenStatusPageButtonClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_STATUS_PAGE + MiRmAPI.serverId))
        MyApplication.getApplication().startActivity(intent)
    }

    fun onStatusSwitchChange(isChecked: Boolean) = GlobalScope.launch (Dispatchers.Main) {
        if (fragment.getStatusSwitchTag() != null) {
            fragment.setStatusSwitchTag(null)
            return@launch
        }

        fragment.setStatusEnabled(false)
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.action(if (isChecked) ActionResponse.ACTION_START else ActionResponse.ACTION_STOP)

        }.await().let {
            if (!it) {
                fragment.setStatusEnabled(true)
                fragment.setStatusChecked(!isChecked)
            } else {
                onUpdateStatus(isChecked)
            }
        }
    }

    private fun onUpdateStatus(isOn: Boolean) = GlobalScope.launch(Dispatchers.Main) {
        fragment.setStatus(

            if (isOn) R.string.status_running_processing else R.string.status_stopped_processing,
            if (isOn) R.color.pureApple else R.color.carminePink
        )

        GlobalScope.async(Dispatchers.Default) {
            if (!isOn) delay(10000)
            MiRmAPI.getServerData()

        }.await().let {
            if (it == null) {
                changeFragment(fragment.childFragmentManager, LoginFragment.newInstance())
                return@launch
            }

            when (it.serverStatus) {
                true -> {
                    fragment.setStatusChecked(true)
                    fragment.setStatus(R.string.status_running, R.color.pureApple)
                    if (isOn) onRunServer()
                }
                false -> {
                    fragment.setStatusEnabled(true)
                    fragment.setStatusChecked(false)
                    fragment.setStatus(R.string.status_stopped, R.color.carminePink)
                }
            }
        }
    }

    private fun onRunServer() {
        GlobalScope.launch(Dispatchers.Main) {
            fragment.setStatusEnabled(false)

            var sec = 0
            while (sec <= 32) {
                sec++
                delay(1000)
            }

            fragment.setStatusEnabled(true)
        }
    }

    fun onExtendButtonClick() {
        val dialog = ExtendDialog.newInstance()
        dialog.show(fragment.activity!!.supportFragmentManager, "extend")
    }

    fun onGotoListButtonClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_SERVER_LIST))
        MyApplication.getApplication().startActivity(intent)
    }

    fun onRSSContentClick(text: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rssFeeds[text]))
        MyApplication.getApplication().startActivity(intent)
    }

}