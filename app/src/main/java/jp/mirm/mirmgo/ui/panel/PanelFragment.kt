package jp.mirm.mirmgo.ui.panel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_panel.*

class PanelFragment : Fragment() {

    private lateinit var presenter: PanelPresenter

    companion object {
        fun newInstance(): PanelFragment {
            return PanelFragment()
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

        panelStatusSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.onStatusSwitchChange(isChecked)
        }

        panelStatusSwitch.setOnTouchListener { _, _ ->
            panelStatusSwitch.tag = null
            false
        }

        panelJoinButton.setOnClickListener {
            presenter.onJoinButtonClick()
        }

        panelExtendButton.setOnClickListener {
            presenter.onExtendButtonClick()
        }

        panelRefreshButton.setOnClickListener {
            presenter.onUpdate()
        }

        panelOpenStatusPageButton.setOnClickListener {
            presenter.onOpenStatusPageButtonClick()
        }

        panelViewPager.adapter = PanelViewPagerAdapter(childFragmentManager)
        panelViewPager.currentItem = 0

        presenter.onUpdate()
    }

    fun setIPAddress(address: String) {
        panelIPAddressView.text = address
    }

    fun setPort(port: String) {
        panelPortView.text = port
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

    fun setStatus(textId: Int, colorId: Int) {
        panelStatusSwitch.text = MyApplication.getApplication().getText(textId)
        panelStatusSwitch.setTextColor(MyApplication.getApplication().resources.getColor(colorId))
    }

    fun setStatusChecked(isChecked: Boolean) {
        panelStatusSwitch.tag = "TAG"
        panelStatusSwitch.isChecked = isChecked
    }

    fun setStatusEnabled(isEnabled: Boolean) {
        panelStatusSwitch.isEnabled = isEnabled
    }

    fun setStatusSwitchTag(tag: String?) {
        panelStatusSwitch.tag = tag
    }

    fun getStatusSwitchTag(): Any? {
        return panelStatusSwitch.tag
    }

}