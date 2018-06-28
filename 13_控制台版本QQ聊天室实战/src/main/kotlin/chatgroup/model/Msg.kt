package chatgroup.model

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 15:26
 */


enum class MsgType(val type: String) {
    TEXT("text"),
    FILE("file"),
    ATTACH("attach"),
    DOWN("down"),
    OFFLINE("offline"),
    ONLINE("online");

    override fun toString(): String {
        return type
    }

    companion object {
        fun convertToMsgType(type: String) = values().firstOrNull { it.type == type } ?: TEXT
    }
}

// sender=Scott Smith&index=1&a=b
data class Msg(val type: MsgType, val info: Map<String, String>, val data: ByteArray)