package jp.mirm.mirmgo.ui.mainmenu

import androidx.fragment.app.FragmentManager
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerFragment

class MainMenuPresenter(private val fragment: MainMenuFragment) : AbstractPresenter() {

    private val fragmentManager: FragmentManager = fragment.activity?.supportFragmentManager ?: fragment.fragmentManager ?:fragment.requireFragmentManager()

    fun onCreateServerClick() {
        changeFragment(fragmentManager, CreateServerFragment.newInstance())
    }

}