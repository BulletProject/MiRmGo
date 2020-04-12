package jp.mirm.mirmgo.ui.create.terms

import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_terms.*

class TermsPresenter(private val fragment: TermsFragment) : AbstractPresenter() {

    fun init() {
        fragment.setAgreedChecked(CreateServerPresenter.isAccepted())
    }

    fun onAgreeCheckBoxChange(agreed: Boolean) {
        CreateServerPresenter.setAccepted(agreed)
        if (agreed) {
            CreateServerPresenter.setPage(2)
        }
    }

    fun onNextButtonClick() {
        CreateServerPresenter.setPage(2)
    }

    fun onPreviousButtonClick() {
        CreateServerPresenter.setPage(0)
    }

}