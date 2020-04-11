package jp.mirm.mirmgo.ui.create

import androidx.viewpager.widget.ViewPager
import jp.mirm.mirmgo.ui.AbstractPresenter
import kotlinx.android.synthetic.main.fragment_create_server.*

class CreateServerPresenter(private val fragment: CreateServerFragment) : AbstractPresenter() {

    companion object {
        var pager: ViewPager? = null

        fun setPage(page: Int) {
            pager?.currentItem = page
        }

    }

    init {
        pager = fragment.createServerViewPager
    }

}