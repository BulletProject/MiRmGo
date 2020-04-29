package jp.mirm.mirmgo.ui.panel.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_panel_about.*

class PanelAboutFragment : Fragment() {

    private lateinit var presenter: PanelAboutPresenter

    companion object {
        fun newInstance(): PanelAboutFragment {
            return PanelAboutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panel_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = PanelAboutPresenter(this)

        aboutTermsButton.setOnClickListener { presenter.onTermsClick() }
        aboutOSSButton.setOnClickListener { presenter.onOSSClick() }
        aboutHPButton.setOnClickListener { presenter.onHPClick() }
        aboutDeleteButton.setOnClickListener { presenter.onDeleteClick() }
    }
}