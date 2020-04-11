package jp.mirm.mirmgo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import jp.mirm.mirmgo.R

abstract class AbstractPresenter {
    fun changeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transition = fragmentManager.beginTransaction()
        transition.addToBackStack(null)
        transition.replace(R.id.fragment, fragment)
        transition.commit()
    }
}