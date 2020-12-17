package ink.pd2.shell

abstract class Command(private val name: String) {
    private var event: CommandEvent? = null

    constructor(event: CommandEvent) {
        this.event = event
    }
}