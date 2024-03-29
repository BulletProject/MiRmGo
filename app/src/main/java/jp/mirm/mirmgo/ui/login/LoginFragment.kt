package jp.mirm.mirmgo.ui.login

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main_menu.*

class LoginFragment : Fragment() {

    private lateinit var presenter: LoginPresenter

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = LoginPresenter(this)

        loginTryLoginButton.setOnClickListener {
            presenter.onTryLoginButtonClick()
        }

        loginBackButton.setOnClickListener {
            presenter.onBackButtonClick()
        }

        loginToCreatedButton.setOnClickListener {
            presenter.onLoginToCreatedServerButtonClick()
        }

        setServerId(arguments?.getString("server_id") ?: "")
        setPassword(arguments?.getString("password") ?: "")

    }

    fun setTryLoginButtonVisibility(isVisible: Boolean) {
        loginTryLoginButton.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun setProgressBarVisibility(isVisible: Boolean) {
        loginProgressBar.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun setErrorTextViewVisibility(isVisible: Boolean) {
        loginErrorTextView.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun setBackButtonEnabled(isEnabled: Boolean) {
        loginBackButton.isEnabled = isEnabled
    }

    fun setErrorTextViewText(id: Int) {
        loginErrorTextView.text = MyApplication.getString(id)
    }

    fun getServerId(): String {
        return loginServerId.text.toString()
    }

    fun getPassword(): String {
        return loginPassword.text.toString()
    }

    fun isSaveDataChecked(): Boolean {
        return loginSavingCheckBox.isChecked
    }

    fun setServerId(serverId: String) {
        loginServerId.text = Editable.Factory.getInstance().newEditable(serverId)
    }

    fun setPassword(password: String) {
        loginPassword.text = Editable.Factory.getInstance().newEditable(password)
    }

}