package jp.mirm.mirmgo.ui.panel.setting

import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.SendCommandManager
import jp.mirm.mirmgo.common.network.MiRmAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PanelSettingPresenter(private val fragment: PanelSettingFragment) {

    fun onOPButtonClick() {
        val array = MyApplication.getApplication().resources.getStringArray(R.array.op_list)
        val op = when (fragment.getOPSpinnerContent()) {
            array[0] -> "op"
            array[1] -> "deop"
            else -> throw IllegalArgumentException()
        }

        SendCommandManager()
            .onSuccess {
                if (it.statusCode == 0) {
                    fragment.showSnackBar(
                        getSnackbarText(
                            R.string.panel_setting_success,
                            R.string.panel_op_title
                        )
                    )
                } else {
                    fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_op_title))
                }
            }
            .onInitialize { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_op_title)) }
            .onError { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_op_title)) }
            .onNetworkError { fragment.showSnackBar(getSnackbarText(R.string.network_error, R.string.panel_op_title)) }
            .onOutOfService { fragment.showSnackBar(getSnackbarText(R.string.out_of_service, R.string.panel_op_title)) }
            .doSend("$op ${fragment.getOPName()}")
    }

    fun onTimeButtonClick() {
        val array = MyApplication.getApplication().resources.getStringArray(R.array.time_list)
        val time = when (fragment.getTimeSpinnerContent()) {
            array[0] -> "day"
            array[1] -> "noon"
            array[2] -> "night"
            array[3] -> "midnight"
            else -> throw IllegalArgumentException()
        }

        SendCommandManager()
            .onSuccess {
                if (it.statusCode == 0) {
                    fragment.showSnackBar(
                        getSnackbarText(
                            R.string.panel_setting_success,
                            R.string.panel_time_title
                        )
                    )
                } else {
                    fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_time_title))
                }
            }
            .onInitialize { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_time_title)) }
            .onError { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_time_title)) }
            .onNetworkError { fragment.showSnackBar(getSnackbarText(R.string.network_error, R.string.panel_time_title)) }
            .onOutOfService { fragment.showSnackBar(getSnackbarText(R.string.out_of_service, R.string.panel_time_title)) }
            .doSend("time set $time")
    }

    fun onGameModeButtonClick() {
        val array = MyApplication.getApplication().resources.getStringArray(R.array.gamemode_list)
        val gameMode = when (fragment.getGameModeSpinnerContent()) {
            array[0] -> 0
            array[1] -> 1
            else -> throw IllegalArgumentException()
        }

        SendCommandManager()
            .onSuccess {
                if (it.statusCode == 0) {
                    fragment.showSnackBar(
                        getSnackbarText(
                            R.string.panel_setting_success,
                            R.string.panel_gamemode_title
                        )
                    )
                } else {
                    fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_gamemode_title))
                }
            }
            .onInitialize { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_gamemode_title)) }
            .onError { fragment.showSnackBar(getSnackbarText(R.string.panel_setting_error, R.string.panel_gamemode_title)) }
            .onNetworkError { fragment.showSnackBar(getSnackbarText(R.string.network_error, R.string.panel_gamemode_title)) }
            .onOutOfService { fragment.showSnackBar(getSnackbarText(R.string.out_of_service, R.string.panel_gamemode_title)) }
            .doSend("gamemode $gameMode ${fragment.getGameModeName()}")
    }

    fun onWhiteListButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        /*
        fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_wl_title))

        GlobalScope.async(Dispatchers.Default) {
            val whileList = when (fragment.isWhiteListChecked()) {
                true -> "on"
                false -> "off"
            }

            MiRmAPI.sendCommand("whitelist $whileList")

        }.await().let {
            fragment.showSnackBar(getSnackbarText(if (it) R.string.panel_setting_success else R.string.panel_setting_error, R.string.panel_wl_title))
        }
         */
    }

    private fun getSnackbarText(id: Int, title: Int): String {
        return "[${MyApplication.getApplication().getText(title)}] ${MyApplication.getApplication().getText(id)}"
    }

}