package jp.mirm.mirmgo.ui.panel.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.ui.login.LoginPresenter
import jp.mirm.mirmgo.util.Preferences
import kotlinx.android.synthetic.main.dialog_extend.*
import kotlinx.android.synthetic.main.dialog_extend.view.*
import kotlinx.android.synthetic.main.dialog_server_list.view.*

class ServerListDialog(val loginPresenter: LoginPresenter) : DialogFragment() {

    private val content = Preferences.getServersMap()

    companion object {
        fun newInstance(loginPresenter: LoginPresenter): ServerListDialog {
            return ServerListDialog(loginPresenter)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val dialog = Dialog(activity!!)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_server_list, null)
        view.loginServerList.setOnItemClickListener { parent, view, position, id ->
            this.dismiss()
            loginPresenter.onServerListItemClick(content.keys.toList()[position])
        }

        val adapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_list_item_1)
        content.keys.forEach {
            adapter.add(it)
        }
        view.loginServerList.adapter = adapter

        dialog.setContentView(view)
        return dialog
    }

}