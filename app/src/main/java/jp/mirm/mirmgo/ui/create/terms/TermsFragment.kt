package jp.mirm.mirmgo.ui.create.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_create_1.view.*
import kotlinx.android.synthetic.main.fragment_create_2.*
import kotlinx.android.synthetic.main.fragment_create_2.view.*

class TermsFragment : Fragment() {

    private val presenter = TermsPresenter(this)

    companion object {
        fun newInstance(agreed: Boolean): TermsFragment {
            return TermsFragment().also {
                it.arguments = bundleOf("agreed" to agreed)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_create_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.init(arguments?.getBoolean("agreed", false) ?: false)

        termsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            presenter.onAgreeCheckBoxChange(isChecked)
        }

        create2NextButton.setOnClickListener {
            presenter.onNextButtonClick()
        }

        create2PreviousButton.setOnClickListener {
            presenter.onPreviousButtonClick()
        }
    }

}