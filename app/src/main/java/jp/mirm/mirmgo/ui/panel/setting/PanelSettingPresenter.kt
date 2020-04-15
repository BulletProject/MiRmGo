package jp.mirm.mirmgo.ui.panel.setting

import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PanelSettingPresenter(private val fragment: PanelSettingFragment) {

    fun onOPButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_op_title))

        GlobalScope.async(Dispatchers.Default) {
            val array = MyApplication.getApplication().resources.getStringArray(R.array.op_list)
            val op = when (fragment.getOPSpinnerContent()) {
                array[0] -> "op"
                array[1] -> "deop"
                else -> throw IllegalArgumentException()
            }

            MiRmAPI.sendCommand("$op ${fragment.getOPName()}")

        }.await().let {
            fragment.showSnackBar(getSnackbarText(if (it) R.string.panel_setting_success else R.string.panel_setting_error, R.string.panel_op_title))
        }
    }

    fun onTimeButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_time_title))

        GlobalScope.async(Dispatchers.Default) {
            val array = MyApplication.getApplication().resources.getStringArray(R.array.time_list)
            val time = when (fragment.getTimeSpinnerContent()) {
                array[0] -> "day"
                array[1] -> "noon"
                array[2] -> "night"
                array[3] -> "midnight"
                else -> throw IllegalArgumentException()
            }

            MiRmAPI.sendCommand("time set $time")

        }.await().let {
            fragment.showSnackBar(getSnackbarText(if (it) R.string.panel_setting_success else R.string.panel_setting_error, R.string.panel_time_title))
        }
    }

    fun onGameModeButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        fragment.showSnackBar(getSnackbarText(R.string.panel_setting_start, R.string.panel_gamemode_title))

        GlobalScope.async(Dispatchers.Default) {
            val array = MyApplication.getApplication().resources.getStringArray(R.array.gamemode_list)
            val gameMode = when (fragment.getGameModeSpinnerContent()) {
                array[0] -> 0
                array[1] -> 1
                else -> throw IllegalArgumentException()
            }

            MiRmAPI.sendCommand("gamemode $gameMode ${fragment.getGameModeName()}")

        }.await().let {
            fragment.showSnackBar(getSnackbarText(if (it) R.string.panel_setting_success else R.string.panel_setting_error, R.string.panel_gamemode_title))
        }
    }

    fun onWhiteListButtonClick() = GlobalScope.launch(Dispatchers.Main) {
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
    }

    private fun getSnackbarText(id: Int, title: Int): String {
        return "${MyApplication.getApplication().getText(id)}${MyApplication.getApplication().getText(title)}"
    }

}