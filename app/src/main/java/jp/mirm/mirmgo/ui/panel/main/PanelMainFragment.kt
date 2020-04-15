package jp.mirm.mirmgo.ui.panel.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.panel.PanelFragment
import kotlinx.android.synthetic.main.fragment_panel_main.*

class PanelMainFragment : Fragment() {

    private lateinit var presenter: PanelMainPresenter

    companion object {
        fun newInstance(): PanelMainFragment {
            return PanelMainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panel_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = PanelMainPresenter(this)

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

        panelOpenStatusPageButton.setOnClickListener {
            presenter.onOpenStatusPageButtonClick()
        }

        presenter.onUpdate()
    }

    fun setIPAddress(address: String) {
        if (PanelFragment.getCurrentPage() == 0) panelIPAddressView.text = address
    }

    fun setPort(port: String) {
        if (PanelFragment.getCurrentPage() == 0) panelPortView.text = port
    }

    fun setStatus(textId: Int, colorId: Int) {
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.text = MyApplication.getApplication().getText(textId)
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.setTextColor(MyApplication.getApplication().resources.getColor(colorId))
    }

    fun setStatusChecked(isChecked: Boolean) {
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.tag = "TAG"
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.isChecked = isChecked
    }

    fun setStatusEnabled(isEnabled: Boolean) {
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.isEnabled = isEnabled
    }

    fun setStatusSwitchTag(tag: String?) {
        if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.tag = tag
    }

    fun getStatusSwitchTag(): Any? {
        return if (PanelFragment.getCurrentPage() == 0) panelStatusSwitch.tag else null
    }

    fun onExtendButtonClick() {
        presenter.onExtendButtonClick()
    }

    fun onUpdate() {
        presenter.onUpdate()
    }
}