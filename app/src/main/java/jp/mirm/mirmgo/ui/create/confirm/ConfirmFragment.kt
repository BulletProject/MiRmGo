package jp.mirm.mirmgo.ui.create.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import kotlinx.android.synthetic.main.fragment_create_confirm.*

class ConfirmFragment : Fragment() {
    private lateinit var presenter: ConfirmPresenter

    companion object {
        fun newInstance(): ConfirmFragment {
            return ConfirmFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ConfirmPresenter(this)
        presenter.init()
    }

    override fun onResume() {
        super.onResume()

        confirmCreateServerButton.setOnClickListener {
            presenter.onCreateButtonClick()
        }

        create3PreviousButton.setOnClickListener {
            presenter.onPreviousClick()
        }

        confirmGamemodeTextView.text = when (CreateServerPresenter.getGamemode()) {
            NewServer.GAMEMODE_SURVIVAL -> this.activity?.getString(R.string.create_survival)
            NewServer.GAMEMODE_CREATIVE -> this.activity?.getString(R.string.create_creative)
            else -> ""
        }

        confirmTermsTextView.text = when (CreateServerPresenter.isAccepted()) {
            true -> this.activity?.getString(R.string.create_2_accept)
            else -> this.activity?.getText(R.string.create_2_not_accept)
        }

        confirmServerTypeTextView.text = this.activity?.getText(R.string.bds)

        confirmServerTypeTextView.setOnClickListener {
            presenter.onServerTypeClick()
        }

        presenter.update()
    }

    fun setCreateButtonEnabled(isEnabled: Boolean) {
        confirmCreateServerButton.isEnabled = isEnabled
    }

}