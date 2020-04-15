package jp.mirm.mirmgo.ui.panel.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_panel_settings.*

class PanelSettingFragment : Fragment() {

    private lateinit var presenter: PanelSettingPresenter

    companion object {
        fun newInstance(): PanelSettingFragment {
            return PanelSettingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_panel_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = PanelSettingPresenter(this)

        opButton.setOnClickListener {
            presenter.onOPButtonClick()
        }

        timeButton.setOnClickListener {
            presenter.onTimeButtonClick()
        }

        gameModeButton.setOnClickListener {
            presenter.onGameModeButtonClick()
        }

        whiteListButton.setOnClickListener {
            presenter.onWhiteListButtonClick()
        }

    }

    fun getOPName() = opName.text

    fun getOPSpinnerContent() = opSpinner.selectedItem as String

    fun getTimeSpinnerContent() = timeSpinner.selectedItem as String

    fun getGameModeName() = gameModeName.text

    fun getGameModeSpinnerContent() = gameModeSpinner.selectedItem as String

    fun isWhiteListChecked() = whiteListCheckBox.isChecked

    fun setButtonEnabled(isEnabled: Boolean) {
        opButton.isEnabled = isEnabled
        gameModeButton.isEnabled = isEnabled
        timeButton.isEnabled = isEnabled
        whiteListButton.isEnabled = isEnabled
    }

    fun showSnackBar(text: String) {
        Snackbar.make(whiteListButton, text, Snackbar.LENGTH_SHORT).show()
    }
}