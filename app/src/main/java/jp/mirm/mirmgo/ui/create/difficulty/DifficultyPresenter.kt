package jp.mirm.mirmgo.ui.create.difficulty

import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import jp.mirm.mirmgo.ui.create.gamemode.SetGamemodeFragment
import jp.mirm.mirmgo.ui.create.terms.TermsFragment

class DifficultyPresenter(private val fragment: DifficultyFragment) {

    init {
        fragment.setPeacefulChecked(false)
        fragment.setEasyChecked(false)
        fragment.setNormalChecked(false)
        fragment.setHardChecked(false)

        when (NewServer.difficulty) {
            NewServer.DIFFICULTY_PEACEFUL -> fragment.setPeacefulChecked(true)
            NewServer.DIFFICULTY_EASY -> fragment.setPeacefulChecked(true)
            NewServer.DIFFICULTY_NORMAL -> fragment.setNormalChecked(true)
            NewServer.DIFFICULTY_HARD -> fragment.setHardChecked(true)
        }
    }

    fun onPeacefulCheck() {
        fragment.setPeacefulChecked(true)
        fragment.setEasyChecked(false)
        fragment.setNormalChecked(false)
        fragment.setHardChecked(false)
        NewServer.difficulty = NewServer.DIFFICULTY_PEACEFUL
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onEasyCheck() {
        fragment.setNormalChecked(false)
        fragment.setHardChecked(false)
        NewServer.difficulty = NewServer.DIFFICULTY_EASY
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onNormalCheck() {
        fragment.setPeacefulChecked(false)
        fragment.setEasyChecked(false)
        fragment.setNormalChecked(true)
        fragment.setHardChecked(false)
        NewServer.difficulty = NewServer.DIFFICULTY_NORMAL
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onHardCheck() {
        fragment.setPeacefulChecked(false)
        fragment.setEasyChecked(false)
        fragment.setNormalChecked(false)
        fragment.setHardChecked(true)
        NewServer.difficulty = NewServer.DIFFICULTY_HARD
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onPreviousButtonClick() {
        CreateServerPresenter.setPage(SetGamemodeFragment.PAGE_NO)
    }

    fun onNextButtonClick() {
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }
}