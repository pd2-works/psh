package ink.pd2.shell

interface CommandEvent {
    fun run(parameter: CommandParameter)
}