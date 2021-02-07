package ink.pd2.shell.buildin

import ink.pd2.shell.core.MarkProvider

object VariableMarkProvider : MarkProvider {
    val variables = HashMap<String, String>()

    override fun getSign(): String {
        return "v"
    }

    override fun onMarkUpdate(value: String): String {
        val variable = variables[value]
        variable?. let {
            return it
        }
        return "&v:$value&"
    }
}
