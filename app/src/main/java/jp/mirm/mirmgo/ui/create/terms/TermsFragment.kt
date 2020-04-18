package jp.mirm.mirmgo.ui.create.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_create_terms.*

class TermsFragment : Fragment() {

    private lateinit var presenter: TermsPresenter

    companion object {
        const val PAGE_NO = 2

        fun newInstance(): TermsFragment {
            return TermsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_terms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = TermsPresenter(this)

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

    fun setAgreedChecked(isChecked: Boolean) {
        termsCheckBox.isChecked = isChecked
    }

    fun setTerms(terms: String) {
        termsTextView.text = terms
    }

    fun setAgreedCheckBoxEnabled(isEnabled: Boolean) {
        termsCheckBox.isEnabled = isEnabled
    }

    fun setProgressBarVisibility(isVisible: Boolean) {
        termsProgressBar.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

}