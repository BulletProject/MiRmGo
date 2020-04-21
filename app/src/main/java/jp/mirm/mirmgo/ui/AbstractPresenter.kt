package jp.mirm.mirmgo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.R

abstract class AbstractPresenter {

    fun changeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transition = fragmentManager.beginTransaction()
        //transition.addToBackStack(null)
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.replace(R.id.fragment, fragment)
        transition.commit()
    }

}