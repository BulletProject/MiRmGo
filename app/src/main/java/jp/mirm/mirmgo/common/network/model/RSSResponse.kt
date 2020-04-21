package jp.mirm.mirmgo.common.network.model

data class RSSResponse(
    val rssFeeds: MutableMap<String, String>,
    private val apiStatus: Int?
) : AbstractResponse(apiStatus)