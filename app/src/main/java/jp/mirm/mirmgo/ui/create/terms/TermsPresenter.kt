package jp.mirm.mirmgo.ui.create.terms

import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import jp.mirm.mirmgo.ui.create.confirm.ConfirmFragment
import jp.mirm.mirmgo.ui.create.difficulty.DifficultyFragment
import kotlinx.android.synthetic.main.fragment_create_terms.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TermsPresenter(private val fragment: TermsFragment) : AbstractPresenter() {

    private var terms: String? = null

    init {
        if (terms == null) {
            onInitTerms()
        } else {
            fragment.setTerms(terms!!)
            fragment.setAgreedCheckBoxEnabled(true)
            fragment.setProgressBarVisibility(false)
        }
        fragment.setAgreedChecked(NewServer.accepted)
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

    fun onAgreeCheckBoxChange(accepted: Boolean) {
        NewServer.accepted = accepted
        if (accepted) {
            CreateServerPresenter.setPage(ConfirmFragment.PAGE_NO)
        }
    }

    fun onNextButtonClick() {
        CreateServerPresenter.setPage(ConfirmFragment.PAGE_NO)
    }

    fun onPreviousButtonClick() {
        CreateServerPresenter.setPage(DifficultyFragment.PAGE_NO)
    }

}