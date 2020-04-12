package jp.mirm.mirmgo.ui.create

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

// https://qiita.com/aluceps/items/49d2941274a22b972a56
class CustomViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {

    private var isSwipeEnable = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean = when (isSwipeEnable) {
        true -> super.onTouchEvent(ev)
        else -> false
    }

    fun setSwipeEnabled(isSwipeEnable: Boolean) {
        this.isSwipeEnable = isSwipeEnable
    }

}