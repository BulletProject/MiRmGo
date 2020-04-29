package jp.mirm.mirmgo.ui.panel.main

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.ActionManager
import jp.mirm.mirmgo.common.manager.GetRSSFeedsManager
import jp.mirm.mirmgo.common.manager.GetServerDataManager
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import kotlinx.coroutines.*
import java.lang.Exception

class PanelMainPresenter(private val fragment: PanelMainFragment) : AbstractPresenter() {

    private lateinit var rssFeeds: MutableMap<String, String>

    fun onUpdate() {
        GetServerDataManager()
            .onSuccess {
                PanelFragment.getInstance().onTimeUpdate(it.time)
                PanelFragment.getInstance().setRefreshButtonEnabled(true)
                PanelFragment.getInstance().setProgressBarIndetermined(false)
                PanelFragment.getInstance().setProgressBarValue(it.time, it.maxTime ?: 600)
                fragment.setIPAddress(it.ip)
                fragment.setPort(it.port.toString())
                fragment.setStatusEnabled(true)

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

                onUpdateRSS()
            }
            .onInitialize {
                PanelFragment.getInstance().setRefreshButtonEnabled(false)
                PanelFragment.getInstance().setProgressBarIndetermined(true)
                PanelFragment.getInstance().setProgressBarValue(0, 0)
                fragment.setIPAddress("-")
                fragment.setPort("-")
                fragment.setStatusEnabled(false)
            }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .doGet()
    }

    private fun onUpdateRSS() {
        GetRSSFeedsManager()
            .onSuccess {
                rssFeeds = it
                fragment.setRSSListContents(rssFeeds.keys.toList())
            }
            .doGet()
    }

    fun onStatusSwitchChange(isChecked: Boolean) {
        if (fragment.getStatusSwitchTag() != null) {
            fragment.setStatusSwitchTag(null)
            return
        }

        ActionManager()
            .onSuccess {
                if (!it.couldExecute) {
                    fragment.setStatusEnabled(true)
                    fragment.setStatusChecked(!isChecked)
                } else {
                    onUpdateStatus(isChecked)
                }
            }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .doAction(if (isChecked) ActionResponse.ACTION_START else ActionResponse.ACTION_STOP)
    }

    private fun onUpdateStatus(isOn: Boolean) {
        GetServerDataManager()
            .onSuccess {
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
            .onInitialize {
                fragment.setStatus(
                    if (isOn) R.string.status_running_processing else R.string.status_stopped_processing,
                    if (isOn) R.color.pureApple else R.color.carminePink
                )
                runBlocking {
                    if (!isOn) delay(15000)
                }
            }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .doGet()
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

    fun onGotoListButtonClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_SERVER_LIST))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onRSSContentClick(text: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rssFeeds[text]))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onOpenStatusPageButtonClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URLHolder.URL_STATUS_PAGE + MiRmAPI.serverId))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        MyApplication.getApplication().startActivity(intent)
    }

    fun onJoinButtonClick() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("minecraft:?addExternalServer=${MiRmAPI.serverId}|${URLHolder.HOST}:${MiRmAPI.port}")
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApplication.getApplication().startActivity(intent)
        } catch (e: Exception) {
            fragment.showSnackbar(R.string.panel_install_minecraft)
        }
    }
}