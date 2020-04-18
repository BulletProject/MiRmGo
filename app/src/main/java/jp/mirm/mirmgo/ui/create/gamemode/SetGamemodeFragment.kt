package jp.mirm.mirmgo.ui.create.gamemode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.mainmenu.MainMenuFragment
import kotlinx.android.synthetic.main.fragment_create_gamemode.*

class SetGamemodeFragment : Fragment() {

    private lateinit var presenter: SetGamemodePresenter

    companion object {
        const val PAGE_NO = 0

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

        createSurvivalCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onSurvivalCheck()
        }

        createCreativeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onCreativeCheck()
        }

        create1NextButton.setOnClickListener {
            presenter.onNextButtonClick()
        }

        create1PreviousButton.setOnClickListener {
            presenter.changeFragment(activity?.supportFragmentManager!!, MainMenuFragment.newInstance())
        }

    }

    fun setSurvivalChecked(isChecked: Boolean) {
        createSurvivalCheckbox.isChecked = isChecked
    }

    fun setCreativeChecked(isChecked: Boolean) {
        createCreativeCheckBox.isChecked = isChecked
    }
}