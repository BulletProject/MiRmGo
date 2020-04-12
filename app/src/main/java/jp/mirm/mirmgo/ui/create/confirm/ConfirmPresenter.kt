package jp.mirm.mirmgo.ui.create.confirm

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter

class ConfirmPresenter(private val fragment: ConfirmFragment) : AbstractPresenter() {

    fun init() {

    }

    fun update() {
        fragment.setCreateButtonEnabled(CreateServerPresenter.isAccepted())
    }

    fun onPreviousClick() {
        CreateServerPresenter.setPage(1)
    }

    fun onCreateButtonClick() {
        if (CreateServerPresenter.isAccepted()) {
            // TODO create server
        }
    }

    fun onServerTypeClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.minecraft.net/ja-jp/download/server/bedrock/"))
        fragment.activity?.startActivity(intent)
    }
}