package chatgroup.config

import chatgroup.main.ClientConnector
import chatgroup.model.Command

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 15:53
 */

object ClientConfig {
    val DEBUG = true
    var attachPath: String? = null
    var username: String? = null

    var inputMode: Command = Command.TEXT
}


object ServerConfig {
    var attachPath: String? = null
    val connectors = mutableListOf<ClientConnector>()
    val attachList = mutableListOf<String>()
}