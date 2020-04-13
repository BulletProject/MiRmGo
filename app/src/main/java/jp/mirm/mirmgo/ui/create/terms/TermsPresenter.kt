package jp.mirm.mirmgo.ui.create.terms

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_terms.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TermsPresenter(private val fragment: TermsFragment) : AbstractPresenter() {

    private var terms: String? = null

    fun init() {
        if (terms == null) {
            onInitTerms()
        } else {
            fragment.setTerms(terms!!)
            fragment.setAgreedCheckBoxEnabled(true)
            fragment.setProgressBarVisibility(false)
        }
        fragment.setAgreedChecked(CreateServerPresenter.isAccepted())
    }

    private fun onInitTerms() = GlobalScope.launch(Dispatchers.Main) {
        GlobalScope.async (Dispatchers.Default) {
            MiRmAPI.getTerms()
        }.await().let {
            if (it != null) {
                terms = it
                fragment.setTerms(terms!!)
                fragment.setAgreedCheckBoxEnabled(true)
                fragment.setProgressBarVisibility(false)
            }
        }
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