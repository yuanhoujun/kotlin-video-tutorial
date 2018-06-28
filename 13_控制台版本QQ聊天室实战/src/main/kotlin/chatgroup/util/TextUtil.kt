package chatgroup.util

import chatgroup.config.ClientConfig

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 15:57
 */

fun log(msg: String) {
    if (ClientConfig.DEBUG) {
        println()
        println("==================")
        println("[ChatGroup] $msg")
        println("==================")
        println()
    }
}
