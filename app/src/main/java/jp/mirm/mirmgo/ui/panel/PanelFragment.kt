package jp.mirm.mirmgo.ui.panel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.panel.main.PanelMainFragment
import kotlinx.android.synthetic.main.fragment_panel.*
import kotlinx.android.synthetic.main.fragment_panel_main.*

class PanelFragment : Fragment() {

    private lateinit var presenter: PanelPresenter

    companion object {
        private var panelMainFragment: PanelMainFragment? = null
        private var instance: PanelFragment? = null
        private var adapter: PanelViewPagerAdapter? = null

        fun getInstance(): PanelFragment {
            return instance ?: PanelFragment().also {
                instance = it
            }
        }

        fun getMainFragment(): PanelMainFragment {
            return panelMainFragment ?: PanelMainFragment().also {
                panelMainFragment = it
            }
        }

        fun getCurrentPage(): Int {
            return (getInstance().view ?: return -1).findViewById<ViewPager>(R.id.panelViewPager).currentItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = PanelPresenter(this)
        adapter = PanelViewPagerAdapter(childFragmentManager)

        panelExtendButton.setOnClickListener {
            getMainFragment().onExtendButtonClick()
        }

        panelRefreshButton.setOnClickListener {
            getMainFragment().onUpdate()
        }

        panelGotoListButton.setOnClickListener {
            presenter.onGotoListButtonClick()
        }

        panelViewPager.adapter = adapter
        panelViewPager.currentItem = 0
    }

    fun setProgressBarValue(value: Int, max: Int) {
        panelProgressBar.max = max
        panelProgressBar.progress = value
    }

    fun setTime(text: String) {
        panelTimeView.text = text
    }

    fun setProgressBarIndetermined(isIndetermined: Boolean) {
        panelProgressBar.isIndeterminate = isIndetermined
    }

    fun setRefreshButtonEnabled(isEnabled: Boolean) {
        panelRefreshButton.isEnabled = isEnabled
    }

    fun onTimeUpdate(time: Int) {
        presenter.setTime(time)
    }

}