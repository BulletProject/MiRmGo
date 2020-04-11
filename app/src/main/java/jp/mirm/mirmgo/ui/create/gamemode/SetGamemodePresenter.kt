package jp.mirm.mirmgo.ui.create.gamemode

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_1.*

class SetGamemodePresenter(val fragment: SetGamemodeFragment) : AbstractPresenter() {

    private var gameMode = NewServer.GAMEMODE_UNKNOWN

    fun init(gameMode: Int) {
        this.gameMode = gameMode
        when (gameMode) {
            NewServer.GAMEMODE_CREATIVE -> onCreative()
            NewServer.GAMEMODE_SURVIVAL -> onSurvival()
        }
    }

    fun onSurvivalButtonClick() {
        onSurvival()
    }

    fun onCreativeButtonClick() {
        onCreative()
    }

    fun onNextButtonClick() {
        if (gameMode != NewServer.GAMEMODE_UNKNOWN) CreateServerPresenter.setPage(1)
    }

    private fun onSurvival() {
        fragment.setSurvivalButton.isEnabled = false
        fragment.setCreativeButton.isEnabled = true
        fragment.create1NextButton.isEnabled = true
        gameMode = NewServer.GAMEMODE_SURVIVAL
    }

    private fun onCreative() {
        fragment.setCreativeButton.isEnabled = false
        fragment.setSurvivalButton.isEnabled = true
        fragment.create1NextButton.isEnabled = true
        gameMode = NewServer.GAMEMODE_CREATIVE
    }

}