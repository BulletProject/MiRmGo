package jp.mirm.mirmgo.ui.create.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.model.NewServer
import kotlinx.android.synthetic.main.fragment_create_confirm.*

class ConfirmFragment : Fragment() {
    private lateinit var presenter: ConfirmPresenter

    companion object {
        const val PAGE_NO = 3

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
    }

    override fun onResume() {
        super.onResume()

        presenter = ConfirmPresenter(this)

        confirmCreateServerButton.setOnClickListener {
            presenter.onCreateButtonClick()
        }

        create3PreviousButton.setOnClickListener {
            presenter.onPreviousClick()
        }

        confirmGamemodeTextView.text = when (NewServer.gameMode) {
            NewServer.GAMEMODE_SURVIVAL -> MyApplication.getString(R.string.create_survival)
            NewServer.GAMEMODE_CREATIVE -> MyApplication.getString(R.string.create_creative)
            else -> ""
        }

        confirmDifficultyTextView.text = when (NewServer.difficulty) {
            NewServer.DIFFICULTY_PEACEFUL -> MyApplication.getString(R.string.create_difficulty_peaceful)
            NewServer.DIFFICULTY_EASY -> MyApplication.getString(R.string.create_difficulty_easy)
            NewServer.DIFFICULTY_NORMAL -> MyApplication.getString(R.string.create_difficulty_normal)
            NewServer.DIFFICULTY_HARD -> MyApplication.getString(R.string.create_difficulty_hard)
            else -> ""
        }

        confirmTermsTextView.text = when (NewServer.accepted) {
            true -> this.activity?.getString(R.string.create_terms_accept)
            else -> this.activity?.getText(R.string.create_terms_not_accept)
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