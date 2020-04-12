package jp.mirm.mirmgo.ui.create.gamemode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_gamemode.*

class SetGamemodeFragment : Fragment() {

    private lateinit var presenter: SetGamemodePresenter

    companion object {
        fun newInstance(): SetGamemodeFragment {
            return SetGamemodeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_gamemode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SetGamemodePresenter(this)
        presenter.init()

        setSurvivalButton.setOnClickListener {
            presenter.onSurvivalButtonClick()
        }

        setCreativeButton.setOnClickListener {
            presenter.onCreativeButtonClick()
        }

        create1NextButton.setOnClickListener {
            presenter.onNextButtonClick()
        }

        create1PreviousButton.setOnClickListener {
            presenter.changeFragment(fragment.activity?.supportFragmentManager!!, MainMenuFragment.newInstance())
        }

    }
}