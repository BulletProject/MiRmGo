package jp.mirm.mirmgo.util

import jp.mirm.mirmgo.MyApplication

object DisplaySizeCaculator {

    fun dpToPx(dp: Int): Int {
        val scale = MyApplication.getApplication().resources.displayMetrics.density
        return (dp * scale + 0.5F).toInt()
    }

}