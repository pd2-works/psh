package ink.pd2.shell.core;

import java.util.HashMap;
import java.util.Map;

public class Mark {
	public final static Mark INS = new Mark();

	private final static Map<String, MarkProvider> marks = new HashMap<>();

	public void registerMarkProvider(MarkProvider provider) {
		marks.put(provider.getSign(), provider);
	}

	/**
	 * <h2>update() 02 | 按标记规则更新</h2>
	 *
	 * 用于按照指定的标记规则更新字符串(该标记规则需已注册, 参见本类 `regMarkProvider()` )
	 *
	 * @param s 要更新的字符串
	 * @param sign 要被更新的记号
	 *
	 * @return 更新后的字符串
	 *
	 * @author Maxel Black (maxelblack@outlook.com)
	 */

	public String update(String sign, String s) {
		//TODO
		return null;
	}

	/**
	 * <h2>update() 01 | 按标记规则更新</h2>
	 *
	 * 用于按照所有已注册的标记规则更新字符串
	 *
	 * 该方法默认替换掉转义字符'\&'
	 *
	 * @param s 要更新的字符串
	 *
	 * @return 更新后的字符串
	 *
	 * @author Maxel Black (maxelblack@outlook.com)
	 */

	public String update(String s) {
		return update(s, true);
	}

	/**
	 * <h2>update() 01 | 按标记规则更新</h2>
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

	public String update(String s, boolean isTruth) {
		//优化: 若有&nomark&标记则直接返回去掉标记的原字符串 (因效率提升不高且综合效率下降已注释)
//		if (s.startsWith("&nomark&")) return s.substring(8);
		if (s == null) return null;
		StringBuilder str = new StringBuilder(s);
		int l, i; //字符串总长, 查找进度
		while (true) {
			i = 0;
			l = str.length();
			//=========================================================
			// 查找前后两个&的位置
			//---------------------------------------------------------
			int i1 = 0, i2 = 0; //第一, 二个&符号的位置
			boolean b1 = false, b2 = false; //第一, 二个&符号是否找到
			// 查找第一个
			while (i < l) {
				i1 = str.indexOf("&", i);
				if (i1 == -1) break;
				int i1c = i1 - 1;
				if (i1 != 0 && str.charAt(i1c) == '\\') {
					i = i1;
					if (isTruth) {
						str.deleteCharAt(i1c);
						l--;
					}
					continue;
				}
				b1 = true; i = i1 + 1; break;
			}
			// 查找第二个
			while (b1 && i < l) {
				i2 = str.indexOf("&", i);
				if (i2 == -1) break;
				int i2c = i2 - 1;
				if (str.charAt(i2c) == '\\') {
					i = i2;
					if (isTruth) {
						str.deleteCharAt(i2c);
						l--;
					}
					continue;
				}
				b2 = true; break;
			}
			//=========================================================
			// 判断格式是否正确并分离记号和被标记的字符串
			//---------------------------------------------------------
			if (b1 && b2 && (i1 + 2 < i2)) {
				String[] ss = str.substring(i1 + 1, i2).split(":", 2);
				if (ss.length == 2) {
					MarkProvider mark = marks.get(ss[0]);
					if (mark != null) {
						String r = mark.onMarkUpdate(this, ss[1]); //用于替换的字符串
						str.replace(i1, i2 + 1, r); //替换标记
						//查找转义字符 '\&' 并替换 (因算法死循环问题已注释)
//						if (isTruth) {
//							str_l = str_i + r.length();
//							while (str_i < str_l) {
//								str_i = str.indexOf("\\&", str_i);
//								if (str_i == -1 || str_i >= str_l) break;
//								str.deleteCharAt(str_i);
//								str_i++; str_l--;
//							}
//						}
						continue; //在当前i位置之后继续查找
					}
				} else if (ss.length == 1) {
					if (ss[0].equals("nomark")) {
						str.delete(i1, i2 + 1);
						break;
					}
					str.replace(i1, i2 + 1,
							marks.get(ss[0]).onMarkUpdate(this, ""));
				}
			}
			//=========================================================
			break;
		}
		return str.toString();
	}

}
