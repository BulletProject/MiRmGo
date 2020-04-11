package jp.mirm.mirmgo.ui.create

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jp.mirm.mirmgo.ui.create.confirm.ConfirmFragment
import jp.mirm.mirmgo.ui.create.gamemode.SetGamemodeFragment
import jp.mirm.mirmgo.ui.create.terms.TermsFragment

class CreateServerViewPagerAdapter(fragmentManager: FragmentManager)  : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SetGamemodeFragment()
            1 -> TermsFragment()
            2 -> ConfirmFragment()
            else -> SetGamemodeFragment()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

}