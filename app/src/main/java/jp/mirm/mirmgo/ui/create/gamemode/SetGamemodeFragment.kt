package jp.mirm.mirmgo.ui.create.gamemode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.model.NewServer
import kotlinx.android.synthetic.main.fragment_create_1.*
import kotlinx.android.synthetic.main.fragment_create_1.view.*

class SetGamemodeFragment : Fragment() {

    private val presenter = SetGamemodePresenter(this)

    companion object {
        fun newInstance(gameMode: Int): SetGamemodeFragment {
            return SetGamemodeFragment().also {
                it.arguments = bundleOf("gameMode" to gameMode)
            }
        }
    }

    init {
        presenter.init(arguments?.getInt("gameMode", NewServer.GAMEMODE_UNKNOWN) ?: NewServer.GAMEMODE_UNKNOWN)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_create_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.init(arguments?.getInt("gameMode", NewServer.GAMEMODE_UNKNOWN) ?: NewServer.GAMEMODE_UNKNOWN)

        setSurvivalButton.setOnClickListener {
            presenter.onSurvivalButtonClick()
        }

        setCreativeButton.setOnClickListener {
            presenter.onCreativeButtonClick()
        }

        create1NextButton.setOnClickListener {
            presenter.onNextButtonClick()
        }

    }
}