package jp.mirm.mirmgo.ui.panel.dialog

import androidx.core.os.bundleOf
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
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

    fun onExtendClick() {
        val loadingDialog = LoadingDialog.newInstance()
        loadingDialog.arguments = bundleOf("text" to MyApplication.getString(R.string.dialog_extend_loading))
        loadingDialog.show(dialog.activity!!.supportFragmentManager, "loading")

        MobileAds.initialize(dialog.activity!!, MyApplication.getString(R.string.movie_id_extend))

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
                println("======================================E$errorCode")
            }
        }
        rewardedVideoAd.loadAd(MyApplication.getString(R.string.movie_id_extend), AdRequest.Builder().build())
    }

    private fun extend() = GlobalScope.launch(Dispatchers.Main) {
        GlobalScope.async(Dispatchers.Default) {
            MiRmAPI.extendNormally()

        }.await().let {
            PanelFragment.getMainFragment().onUpdate()
        }
    }

}