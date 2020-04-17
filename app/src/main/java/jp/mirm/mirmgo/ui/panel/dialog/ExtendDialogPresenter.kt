package jp.mirm.mirmgo.ui.panel.dialog

import android.util.Log
import androidx.core.os.bundleOf
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import jp.mirm.mirmgo.MyApplication
import jp.mirm.mirmgo.R
import jp.mirm.mirmgo.common.network.MiRmAPI
import jp.mirm.mirmgo.ui.dialog.LoadingDialog
import jp.mirm.mirmgo.ui.panel.PanelFragment
import jp.mirm.mirmgo.util.CustomizedRewardedViewAdListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ExtendDialogPresenter(private val dialog: ExtendDialog) {

    fun onExtendClick() = GlobalScope.launch(Dispatchers.Main) {
        dialog.dismiss()
        onTryExtend()
    }

    private fun onTryExtend() {
        val loadingDialog = LoadingDialog.newInstance()
        loadingDialog.arguments = bundleOf("text" to MyApplication.getString(R.string.loading))
        loadingDialog.show(dialog.activity!!.supportFragmentManager, "loading")

        val rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(dialog.activity!!)
        rewardedVideoAd.rewardedVideoAdListener = object: CustomizedRewardedViewAdListener() {
            override fun onRewarded(item: RewardItem?) {
                extend()
            }

            override fun onRewardedVideoAdLoaded() {
                loadingDialog.dismiss()
                if (rewardedVideoAd.isLoaded) rewardedVideoAd.show()
            }

            override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
                loadingDialog.dismiss()
                PanelFragment.getInstance().showSnackbar(R.string.dialog_extend_failed)
                Log.e("LoadAd", "Error code: $errorCode")
            }
        }
        rewardedVideoAd.loadAd(MyApplication.getString(R.string.admob_movie_extend), AdRequest.Builder().build())
    }

    private fun extend() = GlobalScope.launch(Dispatchers.Main) {
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.extendNormally()

        }.await().let {
            if (it) {
                PanelFragment.getMainFragment().onUpdate()
                PanelFragment.getInstance().showSnackbar(R.string.dialog_extend_success)
            } else {
                PanelFragment.getInstance().showSnackbar(R.string.dialog_extend_error)
            }
        }
    }

}