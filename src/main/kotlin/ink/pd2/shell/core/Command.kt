package ink.pd2.shell.core

open class Command(private val name: String) {
	val events = HashSet<CommandEvent>(setOf())

	constructor(name: String, vararg events: CommandEvent) : this(name) {
		this.events += events
	}

	fun addEvent(event: CommandEvent) {
		events += event
	}
}
