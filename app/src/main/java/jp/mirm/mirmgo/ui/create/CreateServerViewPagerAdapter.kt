package jp.mirm.mirmgo.ui.create

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import jp.mirm.mirmgo.ui.create.confirm.ConfirmFragment
import jp.mirm.mirmgo.ui.create.difficulty.DifficultyFragment
import jp.mirm.mirmgo.ui.create.gamemode.SetGamemodeFragment
import jp.mirm.mirmgo.ui.create.terms.TermsFragment

class CreateServerViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            SetGamemodeFragment.PAGE_NO -> SetGamemodeFragment.newInstance()
            DifficultyFragment.PAGE_NO -> DifficultyFragment.newInstance()
            TermsFragment.PAGE_NO -> TermsFragment.newInstance()
            ConfirmFragment.PAGE_NO -> ConfirmFragment.newInstance()
            else -> SetGamemodeFragment()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

}