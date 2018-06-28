package chatgroup.model

/**
 * 请描述使用该类使用方法！！！
 *
 * @author Scott Smith 2018-06-28 14:36
 */


enum class Command(val desc: String) {
    TEXT(":text"),
    FILE(":file"),
    ATTACH(":attach"), // 拉取附件列表
    DOWN(":down"), // :down#1
    OFFLINE(":offline");

    override fun toString(): String {
        return desc
    }

    fun desc(): String = when(this) {
        TEXT -> "文本输入模式"
        FILE -> "文件发送模式"
        ATTACH -> "附件列表拉取"
        DOWN -> "附件下载模式"
        OFFLINE -> "离线模式"
    }

    fun value() = desc.substring(1)

    companion object {
        fun convertToCmd(cmd: String): Command {
            if (cmd.matches(Regex(":down#[0-9]*"))) return DOWN
            return values().firstOrNull { it.desc == cmd.trim() } ?: Command.TEXT
        }
    }
}