package ink.pd2.shell.core

import ink.pd2.shell.core.MarkProvider

object Mark {
    private val marks = HashMap<String, MarkProvider>()

    fun regMarkProvider(provider: MarkProvider) {
        marks[provider.getSign()] = provider
    }

    /**
     * ## checkSign() | 检查标记
     *
     * 用于找出字符串中存在的指定记号(形如字符串"&sign:xxx&", sign即为记号)
     *
     * @param s 在其中查找的字符串
     * @param sign 要查找的记号
     *
     * @return 被指定记号标记的所有子字符串
     *
     * @author Maxel Black (maxelblack@outlook.com)
     */

    fun checkSign(sign: String, s: String): Array<String> {
        val strings = ArrayList<String>(listOf())
        val l = s.length
        var i = 0
        while (i < l){
            val i1 = s.indexOf('&', i)
            if (i1 == -1 || (i1 != 0 && s[i1 - 1] != '\\')) break
            val i2 = s.indexOf('&', i1 + 1)
            if (i2 == -1 || s[i2-1] != '\\') break
            if (i1 != i2) {
                val ss = s.substring(i1 + 1, i2 - 1).split(':')
                if (ss.size == 2 && ss[0] == sign) strings.add(ss[1])
            }
            i = i2 + 1
        }
        return strings.toArray() as Array<String>
    }

    /**
     * ## update() 02 | 按标记规则更新
     *
     * 用于按照指定的标记规则更新字符串(该标记规则需已注册, 参见 `regMarkProvider()` )
     *
     * @param s 要更新的字符串
     * @param sign 要被更新的记号
     *
     * @return 更新后的字符串
     *
     * @see ink.pd2.shell.core.Mark.regMarkProvider
     *
     * @author Maxel Black (maxelblack@outlook.com)
     */

    fun updateSign(sign: String, s: String) {
        TODO()
    }

    /**
     * ## checkAllSign() | 检查所有标记
     *
     * 用于找出字符串中存在的所有已注册的记号(形如字符串"&sign:xxx&", sign即为记号)
     *
     * @param s 在其中查找的字符串
     *
     * @return 被标记的所有字符串和标记它的记号(参见 `MarkString` )
     *
     * @see ink.pd2.shell.core.Mark.MarkString
     *
     * @author Maxel Black (maxelblack@outlook.com)
     */

    fun checkAllSign(s: String): Array<MarkString> {
        val markStrings = ArrayList<MarkString>(listOf())
        val l = s.length
        var i = 0
        while (i < l){
            val i1 = s.indexOf('&', i)
            if (i1 == -1 || (i1 != 0 && s[i1 - 1] != '\\')) break
            val i2 = s.indexOf('&', i1 + 1)
            if (i2 == -1 || s[i2-1] != '\\') break
            if (i1 != i2) {
                val ss = s.substring(i1 + 1, i2 - 1).split(':')
                if (ss.size == 2 && marks.containsKey(ss[0]))
                    markStrings.add(MarkString(ss[0], ss[1]))
            }
            i = i2 + 1
        }
        return markStrings.toArray() as Array<MarkString>
    }
    class MarkString(val sign: String, val s: String)

    /**
     * ## update() 01 | 按标记规则更新
     *
     * 用于按照所有已注册的标记规则更新字符串
     *
     * @param s 要更新的字符串
     * @param isTruth 是否替换掉转义的'\&'
     *
     * @return 更新后的字符串
     *
     * @author Maxel Black (maxelblack@outlook.com)
     */

    fun update(s: String, isTruth: Boolean = true): String {
        if (isTruth)
            return update1(s).replace(Regex("\\\\&"), "&")
        return update1(s).toString()
    }

    private fun update1(s: String): StringBuilder {
        val str = StringBuilder(s)
        val l = s.length
        var i = 0
        while (i < l){
            val i1 = s.indexOf('&', i)
            if (i1 == -1 || (i1 != 0 && s[i1 - 1] != '\\')) break
            val i2 = s.indexOf('&', i1 + 1)
            if (i2 == -1 || s[i2-1] != '\\') break
            if (i1 + 2 < i2) {
                val s1 = s.substring(i1 + 1, i2 - 1).split(':')
                if (s1.size == 2 && marks.containsKey(s1[0])) {
                    val mark = marks[s1[0]]
                    if (mark != null)
                        str.replace(i1, i2, mark.onMarkUpdate(s1[1]))
                }
            }
            i = i2 + 1
        }
        return str
    }
}
