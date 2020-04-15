package jp.mirm.mirmgo.ui.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : Fragment() {

    private lateinit var presenter: MainMenuPresenter

    companion object {
        fun newInstance(): MainMenuFragment {
            return MainMenuFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MainMenuPresenter(this)
        presenter.init()

        createServerButton.setOnClickListener {
            presenter.onCreateServerClick()
        }

        loginButton.setOnClickListener {
            presenter.onLoginClick()
        }
    }

    fun showLoginSnackbar(id: Int, length: Int): Snackbar {
        val snackbar = Snackbar.make(loginButton, id, length)
        snackbar.show()
        return snackbar
    }

}