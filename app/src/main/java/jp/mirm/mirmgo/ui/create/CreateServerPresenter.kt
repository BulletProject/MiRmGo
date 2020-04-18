package jp.mirm.mirmgo.ui.create

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import kotlinx.android.synthetic.main.fragment_create_server.*

class CreateServerPresenter(private val fragment: CreateServerFragment) : AbstractPresenter() {

    companion object {
        private var pager: CustomViewPager? = null

        fun setPage(page: Int) {
            pager?.currentItem = page
        }
    }

    init {
        pager = fragment.createServerViewPager
        NewServer.reset()
    }

}