package jp.mirm.mirmgo.ui.create.confirm

import android.content.Intent
import android.net.Uri
import jp.mirm.mirmgo.model.NewServer
import jp.mirm.mirmgo.ui.AbstractPresenter
import jp.mirm.mirmgo.ui.create.CreateServerPresenter
import jp.mirm.mirmgo.ui.create.terms.TermsFragment
import jp.mirm.mirmgo.firebase.FirebaseEventManager

class ConfirmPresenter(private val fragment: ConfirmFragment) : AbstractPresenter() {

    fun update() {
        fragment.setCreateButtonEnabled(NewServer.canCreateServer())
    }

    fun onPreviousClick() {
        CreateServerPresenter.setPage(TermsFragment.PAGE_NO)
    }

    fun onCreateButtonClick() {
        if (NewServer.canCreateServer()) {
            // TODO create server
            FirebaseEventManager.onCreateServer()
        }
    }

    fun onServerTypeClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.minecraft.net/ja-jp/download/server/bedrock/"))
        fragment.activity?.startActivity(intent)
    }
}