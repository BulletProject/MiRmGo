package jp.mirm.mirmgo.ui.create.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.model.NewServer
import kotlinx.android.synthetic.main.fragment_create_3.*
import kotlinx.android.synthetic.main.fragment_create_3.view.*

class ConfirmFragment : Fragment() {
    private val presenter = ConfirmPresenter(this)

    companion object {
        fun newInstance(gameMode: Int, agreed: Boolean): ConfirmFragment {
            return ConfirmFragment().also {
                it.arguments = bundleOf("agreed" to agreed, "gameMode" to gameMode)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.init(
            arguments?.getInt("gameMode", NewServer.GAMEMODE_UNKNOWN) ?: NewServer.GAMEMODE_UNKNOWN,
            arguments?.getBoolean("agreed", false) ?: false
        )

        confirmCreateServerButton.setOnClickListener {
            presenter.onCreateButtonClick()
        }

        create3PreviousButton.setOnClickListener {
            presenter.onPreviousClick()
        }
    }


}