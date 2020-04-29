package jp.mirm.mirmgo.ui.create.confirm

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.manager.CreateBDSServerManager
import jp.mirm.mirmgo.common.manager.LoginManager
import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import jp.mirm.mirmgo.ui.create.terms.TermsFragment
import jp.mirm.mirmgo.firebase.FirebaseEventManager
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.util.PasswordManager
import jp.mirm.mirmgo.util.Preferences
import jp.mirm.mirmgo.util.ServerCreationTools
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class ConfirmPresenter(private val fragment: ConfirmFragment) : AbstractPresenter() {

    fun update() {
        fragment.setCreateButtonEnabled(NewServer.canCreateServer())
    }

    fun onPreviousClick() {
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onCreateButtonClick() {
        if (NewServer.canCreateServer()) {
            createServer(buildLoginData(NewServer))
        }
    }

    fun onServerTypeClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.minecraft.net/ja-jp/download/server/bedrock/"))
        fragment.activity?.startActivity(intent)
    }

    fun createServer(server: NewServer) {
        val dialog = LoadingDialog.newInstance()
        dialog.arguments = bundleOf("text" to MyApplication.getString(R.string.loading))

        CreateBDSServerManager()
            .onSuccess {
                FirebaseEventManager.onCreateServer()
                Preferences.addServer(server.serverId!!, PasswordManager.decrypt(server.password!!))
                tryLogin(server.serverId!!, PasswordManager.decrypt(server.password!!))
            }
            .onInitialize { dialog.show(fragment.activity!!.supportFragmentManager, "creating_server") }
            .onFinish { dialog.dismiss() }
            .onAlreadyExists { createServer(buildLoginData(server)) }
            .onInvalidPassword { fragment.showSnackbar(R.string.error) }
            .onInvalidServerId { fragment.showSnackbar(R.string.error) }
            .onFailed { fragment.showSnackbar(R.string.error) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .create(server)
    }

    private fun buildLoginData(server: NewServer): NewServer {
        server.serverId = ServerCreationTools.buildServerId()
        server.password = ServerCreationTools.buildPassword()
        return server
    }

    private fun tryLogin(serverId: String, password: String) {
        val fragmentManager: FragmentManager = fragment.activity?.supportFragmentManager ?: fragment.fragmentManager ?:fragment.requireFragmentManager()
        val dialog = LoadingDialog.newInstance()

        LoginManager()
            .onLoginSuccess {
                FirebaseEventManager.onLogin("create_login")
                saveLoginData(serverId, password)
                changeFragment(fragmentManager, PanelFragment.getInstance().also {
                    it.arguments = bundleOf("show_info" to true)
                })
            }
            .onDeleted { fragment.showSnackbar(R.string.main_login_deleted) }
            .onUserDeleted { fragment.showSnackbar(R.string.main_login_user_deleted) }
            .onLoginFailed { fragment.showSnackbar(R.string.main_login_failed) }
            .onOutOfService { fragment.showSnackbar(R.string.out_of_service) }
            .onNetworkError { fragment.showSnackbar(R.string.network_error) }
            .onError { fragment.showSnackbar(R.string.error) }
            .onInitialize { dialog.show(fragmentManager, "logging_in") }
            .onFinish { dialog.dismiss() }
            .doLogin(serverId, password)
    }

    private fun saveLoginData(serverId: String, password: String) {
        Preferences.addServer(serverId, password)
        Preferences.setCurrentServer(serverId)
    }
}