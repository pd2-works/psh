package ink.pd2.shell.buildin

import ink.pd2.shell.core.MarkProvider
import org.fusesource.jansi.Ansi

object ColorMarkProvider : MarkProvider {
    private val colors = HashMap<String, Int>()
    fun putColor(key: String, value: Int) {
        colors[key] = value
    }
    fun removeColor(key: String) {
        colors.remove(key)
    }

    override fun getSign(): String {
        return "color"
    }

    override fun onMarkUpdate(value: String): String {
        //================================================
        //||  "&color:fg.bg&"                           ||
        //||  "&color:fg.bg[colored_text]normal_text&"  ||
        //================================================
        val i1 = value.indexOf('[')
        if (i1 == -1) {
            val color = value.split('.')
            if (color.size == 2) {
                var a = Ansi.ansi()
                if (colors.containsKey(color[0])) a = a.fg(colors[color[0]]!!)
                if (colors.containsKey(color[1])) a = a.bg(colors[color[1]]!!)
                return a.toString()
            }
        }
        val i2 = value.lastIndexOf(']')
        if (i1 < i2) {
            val color   = value.substring(0, i1 - 1).split('.')
            val ansi    = value.substring(i1 + 1, i2 - 1)
            val normal  = value.substring(i2 + 1)
            if (color.size == 2) {
                var a = Ansi.ansi()
                if (colors.containsKey(color[0])) a = a.fg(colors[color[0]]!!)
                if (colors.containsKey(color[1])) a = a.bg(colors[color[1]]!!)
                return a.a(ansi).reset().a(normal).toString()
            }
        }
        return value
    }
}
