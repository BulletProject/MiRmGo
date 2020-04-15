package jp.mirm.mirmgo.common.network

object URLHolder {

    const val PROTOCOL = "https://"
    const val HOST = "x.mirm.jp"

    const val URL_TERMS = "https://raw.githubusercontent.com/BulletProject/MiRm-Terms/master/README.md"

    const val URL_LOGIN = "${PROTOCOL}${HOST}/login"
    const val URL_LOGOUT = "${PROTOCOL}${HOST}/logout"
    const val URL_AUTHENTICATE = "${PROTOCOL}${HOST}/authenticate"

    const val URL_SERVER_DATA = "${PROTOCOL}${HOST}/api/server_data"
    const val URL_SEND_COMMAND = "${PROTOCOL}${HOST}/api/command"
    const val URL_ACTION = "${PROTOCOL}${HOST}/api/action"
    const val URL_EXTEND = "${PROTOCOL}${HOST}/api/extend"
    const val URL_STATUS_PAGE = "${PROTOCOL}${HOST}/status_page.html?serverId="
    const val URL_SERVER_LIST = "${PROTOCOL}${HOST}/list"
    const val URL_SITE = "${PROTOCOL}mirm.jp"
    const val URL_RSS_FEEDS = "${PROTOCOL}mirm.jp/?feed=rss2"

}