package jp.mirm.mirmgo.ui.panel.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.dialog_extend.*
import kotlinx.android.synthetic.main.dialog_extend.view.*

class ExtendDialog : DialogFragment() {

    private lateinit var presenter: ExtendDialogPresenter

    companion object {
        fun newInstance(): ExtendDialog {
            return ExtendDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        presenter = ExtendDialogPresenter(this)

        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_extend, null)
        view.dialogExtendButton.setOnClickListener {
            presenter.onExtendClick()
        }
        view.dialogBackButton.setOnClickListener {
            this.dismiss()
        }

        dialog.setContentView(view)
        return dialog
    }

}