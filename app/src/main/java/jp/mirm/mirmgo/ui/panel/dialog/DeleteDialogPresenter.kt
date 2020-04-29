package jp.mirm.mirmgo.ui.panel.dialog

import android.content.Intent
import androidx.core.os.bundleOf
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.ActionManager
import jp.mirm.mirmgo.common.manager.LogoutManager
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.common.network.model.ActionResponse
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.MainActivity
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.login.LoginFragment
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.util.Preferences

class DeleteDialogPresenter(private val dialog: DeleteDialog) : AbstractPresenter() {

    fun onDeleteClick() {
        val loading = LoadingDialog.newInstance()
        loading.arguments = bundleOf("text" to MyApplication.getString(R.string.loading))
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
        Preferences.removeCurrentServer()
        Preferences.removeServer(MiRmAPI.serverId)

        LogoutManager()
            .onSuccess {
                changeFragment(PanelFragment.getInstance().activity!!.supportFragmentManager, LoginFragment.newInstance())
            }
            .onError { PanelFragment.getInstance().showSnackbar(R.string.error) }
            .onOutOfService { PanelFragment.getInstance().showSnackbar(R.string.out_of_service) }
            .onNetworkError { PanelFragment.getInstance().showSnackbar(R.string.network_error) }
            .doLogout()
    }

}