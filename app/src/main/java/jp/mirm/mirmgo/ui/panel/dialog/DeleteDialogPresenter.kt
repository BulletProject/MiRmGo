package jp.mirm.mirmgo.ui.panel.dialog

import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.ActionManager
import jp.mirm.mirmgo.common.manager.LogoutManager
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment

class DeleteDialogPresenter(private val dialog: DeleteDialog) : AbstractPresenter() {

    fun onDeleteClick() {
        val loading = LoadingDialog.newInstance()
        loading.show(dialog.activity!!.supportFragmentManager, "delete")

        ActionManager()
            .onSuccess { finalize() }
            .onError { PanelFragment.getInstance().showSnackbar(R.string.error) }
            .onOutOfService { PanelFragment.getInstance().showSnackbar(R.string.out_of_service) }
            .onNetworkError { PanelFragment.getInstance().showSnackbar(R.string.network_error) }
            .onFinish { loading.dismiss() }
            .doAction(ActionResponse.ACTION_DELETE)
    }

    private fun finalize() {
        LogoutManager()
            .onSuccess { changeFragment(dialog.childFragmentManager, LoginFragment.newInstance()) }
            .onError { PanelFragment.getInstance().showSnackbar(R.string.error) }
            .onOutOfService { PanelFragment.getInstance().showSnackbar(R.string.out_of_service) }
            .onNetworkError { PanelFragment.getInstance().showSnackbar(R.string.network_error) }
            .doLogout()
    }

}