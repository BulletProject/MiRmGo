package jp.mirm.mirmgo.ui.panel.dialog

import android.view.View

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.dialog_extend.view.*
class ServerInfoDialog : DialogFragment() {

    private lateinit var presenter: DeleteDialogPresenter

    companion object {
        fun newInstance(): ServerInfoDialog {
            return ServerInfoDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val serverId = arguments?.getString("server_id")
        val password = arguments?.getString("password")

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_extend, null)
        view.dialogExtendButton.setOnClickListener {
            this.dismiss()
        }
        view.dialogBackButton.visibility = View.GONE
        view.extendDialogTitle.text = getString(R.string.popup_info)
        view.extendDialogText.text = getString(R.string.dialog_info_text, serverId, password)
        view.dialogExtendButton.text = MyApplication.getString(R.string.back)

        dialog.setContentView(view)
        return dialog
    }

}