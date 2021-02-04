package ink.pd2.shell.core

object Mark {
	private val marks = HashMap<String, MarkProvider>()

	fun regMarkProvider(provider: MarkProvider) {
		marks[provider.getSign()] = provider
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
		val l = str.length //字符串总长
		var i = 0 //查找进度
		while (i < l) {
			//=========================================================
			// 查找前后两个&的位置
			//---------------------------------------------------------
			var i1 = 0;
			var b1 = false //第一个&位置(i1)和是否找到(b1)
			var i2 = 0;
			var b2 = false //第二个&位置(i2)和是否找到(b2)
			// 查找第一个
			while (i < l) {
				i1 = str.indexOf('&', i)
				if (i1 == -1) break
				if (i1 != 0 && str[i1 - 1] == '\\') {
					i = i1 + 1
					continue
				}
				b1 = true; i = i1 + 1; break
			}
			// 查找第二个
			while (b1 && i < l) {
				i2 = str.indexOf('&', i)
				if (str[i2 - 1] == '\\') {
					i = i2 + 1
					continue
				}
				b2 = true; break
			}
			//=========================================================
			// 判断格式是否正确并分离记号和被标记的字符串
			//---------------------------------------------------------
			if (b1 && b2 && i1 + 2 < i2) {
				val ss = str.substring(i1 + 1, i2).split(':')
				if (ss.size == 2) {
					val mark = marks[ss[0]]
					if (mark != null) {
						str.replace(i1, i2 + 1, mark.onMarkUpdate(ss[1])) //替换
						continue //在当前i位置之后继续查找
					}
				}
			}
			//=========================================================
			break
		}
		return str
	}
}
