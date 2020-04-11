package jp.mirm.mirmgo.ui.create.confirm

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter

class ConfirmPresenter(private val fragment: ConfirmFragment) : AbstractPresenter() {

    private var gameMode = NewServer.GAMEMODE_UNKNOWN
    private var agreed = false

    fun init(gameMode: Int, agreed: Boolean) {
        this.gameMode = gameMode
        this.agreed = agreed
    }

    fun onPreviousClick() {
        CreateServerPresenter.setPage(1)
    }

    fun onCreateButtonClick() {
        if (agreed) {
            // TODO
        }
    }
}