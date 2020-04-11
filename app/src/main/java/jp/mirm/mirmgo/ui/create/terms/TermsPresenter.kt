package jp.mirm.mirmgo.ui.create.terms

import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_2.*

class TermsPresenter(private val fragment: TermsFragment) : AbstractPresenter() {

    private var agreed = false

    fun init(agreed: Boolean) {
        fragment.termsCheckBox.isChecked = agreed
        this.agreed = agreed
        if (agreed) fragment.create2NextButton.isEnabled = true
    }

    fun onAgreeCheckBoxChange(agreed: Boolean) {
        this.agreed = agreed
        if (agreed) fragment.create2NextButton.isEnabled = true
    }

    fun onNextButtonClick() {
        if (agreed) CreateServerPresenter.setPage(2)
    }

    fun onPreviousButtonClick() {
        CreateServerPresenter.setPage(0)
    }

}