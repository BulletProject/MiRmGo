package jp.mirm.mirmgo.ui.create.gamemode

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_gamemode.*

class SetGamemodePresenter(val fragment: SetGamemodeFragment) : AbstractPresenter() {

    fun init() {
        when (CreateServerPresenter.getGamemode()) {
            NewServer.GAMEMODE_CREATIVE -> onCreative()
            NewServer.GAMEMODE_SURVIVAL -> onSurvival()
            else -> onSurvival()
        }
    }

    fun onSurvivalButtonClick() {
        onSurvival()
        CreateServerPresenter.setPage(1)
    }

    fun onCreativeButtonClick() {
        onCreative()
        CreateServerPresenter.setPage(1)
    }

    fun onNextButtonClick() {
        CreateServerPresenter.setPage(1)
    }

    private fun onSurvival() {
        fragment.setSurvivalButton.isEnabled = false
        fragment.setCreativeButton.isEnabled = true
        CreateServerPresenter.setGameMode(NewServer.GAMEMODE_SURVIVAL)
    }

    private fun onCreative() {
        fragment.setCreativeButton.isEnabled = false
        fragment.setSurvivalButton.isEnabled = true
        CreateServerPresenter.setGameMode(NewServer.GAMEMODE_CREATIVE)
    }

}