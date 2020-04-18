package jp.mirm.mirmgo.ui.create.difficulty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_create_difficulty.*

class DifficultyFragment : Fragment() {

    private lateinit var presenter: DifficultyPresenter

    companion object {
        const val PAGE_NO = 1

        fun newInstance(): DifficultyFragment {
            return DifficultyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_difficulty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = DifficultyPresenter(this)

        createPeacefulCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onPeacefulCheck()
        }

        createEasyCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onEasyCheck()
        }

        createNormalCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onNormalCheck()
        }

        createHardCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) presenter.onHardCheck()
        }

        create4NextButton.setOnClickListener {
            presenter.onNextButtonClick()
        }

        create4PreviousButton.setOnClickListener {
            presenter.onPreviousButtonClick()
        }
    }

    fun setPeacefulChecked(isChecked: Boolean) {
        createPeacefulCheckBox.isChecked = isChecked
    }

    fun setEasyChecked(isChecked: Boolean) {
        createEasyCheckBox.isChecked = isChecked
    }

    fun setNormalChecked(isChecked: Boolean) {
        createNormalCheckBox.isChecked = isChecked
    }

    fun setHardChecked(isChecked: Boolean) {
        createHardCheckBox.isChecked = isChecked
    }

}