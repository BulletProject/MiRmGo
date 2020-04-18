package jp.mirm.mirmgo.ui.create.gamemode

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import jp.mirm.mirmgo.ui.create.difficulty.DifficultyFragment
import kotlinx.android.synthetic.main.fragment_create_gamemode.*

class SetGamemodePresenter(val fragment: SetGamemodeFragment) : AbstractPresenter() {

    init {
        when (NewServer.gameMode) {
            NewServer.GAMEMODE_CREATIVE -> onCreative()
            NewServer.GAMEMODE_SURVIVAL -> onSurvival()
        }
    }

    fun onSurvivalCheck() {
        onSurvival()
        CreateServerPresenter.setPage(DifficultyFragment.PAGE_NO)
    }

    fun onCreativeCheck() {
        onCreative()
        CreateServerPresenter.setPage(DifficultyFragment.PAGE_NO)
    }

    fun onNextButtonClick() {
        CreateServerPresenter.setPage(DifficultyFragment.PAGE_NO)
    }

    private fun onSurvival() {
        fragment.setCreativeChecked(false)
        fragment.setSurvivalChecked(true)
        NewServer.gameMode = NewServer.GAMEMODE_SURVIVAL
    }

    private fun onCreative() {
        fragment.setSurvivalChecked(false)
        fragment.setCreativeChecked(true)
        NewServer.gameMode = NewServer.GAMEMODE_CREATIVE
    }

}