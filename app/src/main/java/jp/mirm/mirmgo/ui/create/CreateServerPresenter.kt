package jp.mirm.mirmgo.ui.create

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import kotlinx.android.synthetic.main.fragment_create_server.*

class CreateServerPresenter(private val fragment: CreateServerFragment) : AbstractPresenter() {

    companion object {
        private var pager: CustomViewPager? = null
        private val serverData = mutableMapOf<String, Any>()

        fun setPage(page: Int) {
            pager?.currentItem = page
        }

        fun setGameMode(i: Int) {
            serverData["gameMode"] = i
        }

        fun setAccepted(bool: Boolean) {
            serverData["accepted"] = bool
        }

        fun getGamemode(): Int = (serverData["gameMode"] ?: NewServer.GAMEMODE_UNKNOWN) as Int

        fun isAccepted(): Boolean = (serverData["accepted"] ?: false) as Boolean
    }

    init {
        pager = fragment.createServerViewPager
    }

}