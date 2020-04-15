package jp.mirm.mirmgo.ui.panel

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.common.network.URLHolder
import jp.mirm.mirmgo.ui.AbstractPresenter
import kotlinx.coroutines.*

class PanelPresenter(private val fragment: PanelFragment) : AbstractPresenter() {

    private var time = 0

    init {
        GlobalScope.launch (Dispatchers.Main) {
            while (true) {
                fragment.setProgressBarValue(time, 600)
                fragment.setTime("$time/600分")
                delay(60000)
                time--
            }
        }
    }

    fun setTime(time: Int) {
        this.time = time
    }

}