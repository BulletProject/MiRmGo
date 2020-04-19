package jp.mirm.mirmgo.ui.panel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.panel.setting.PanelSettingFragment
import jp.mirm.mirmgo.ui.panel.about.PanelAboutFragment
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PanelViewPagerAdapter(fragmentManager: FragmentManager)  : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PanelFragment.getMainFragment()
            1 -> PanelSettingFragment.newInstance()
            2 -> PanelAboutFragment.newInstance()
            else -> throw IllegalStateException()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> MyApplication.getApplication().getString(R.string.panel_main_title)
            1 -> MyApplication.getApplication().getString(R.string.panel_setting_title)
            2 -> MyApplication.getApplication().getString(R.string.panel_info_ways)
            else -> throw IllegalArgumentException()
        }
    }

}