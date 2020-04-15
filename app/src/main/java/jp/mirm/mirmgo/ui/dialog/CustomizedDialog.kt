package jp.mirm.mirmgo.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.dialog_loggingin.*
import kotlinx.android.synthetic.main.dialog_loggingin.view.*

class CustomizedDialog : DialogFragment() {

    companion object {
        fun newInstance(): CustomizedDialog {
            return CustomizedDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_loggingin, null)
        if (arguments != null && (arguments as Bundle).containsKey("text")) {
            view.dialogText.text = arguments!!.getString("text")
        }

        dialog.setContentView(view)

        this.isCancelable = false
        return dialog
    }

}