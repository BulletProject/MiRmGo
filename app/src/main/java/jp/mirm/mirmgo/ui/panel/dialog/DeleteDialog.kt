package jp.mirm.mirmgo.ui.panel.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.dialog_extend.*
import kotlinx.android.synthetic.main.dialog_extend.view.*

class DeleteDialog : DialogFragment() {

    private lateinit var presenter: DeleteDialogPresenter

    companion object {
        fun newInstance(): DeleteDialog {
            return DeleteDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        presenter = DeleteDialogPresenter(this)

        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_extend, null)
        view.dialogExtendButton.setOnClickListener {
            presenter.onDeleteClick()
        }
        view.dialogBackButton.setOnClickListener {
            this.dismiss()
        }
        view.extendDialogTitle.text = MyApplication.getString(R.string.panel_about_delete)
        view.extendDialogText.text = MyApplication.getString(R.string.panel_about_delete_text)
        view.dialogExtendButton.text = MyApplication.getString(R.string.panel_about_delete)
        view.dialogExtendButton.setTextColor(MyApplication.getApplication().resources.getColor(R.color.carminePink))

        dialog.setContentView(view)
        return dialog
    }

}