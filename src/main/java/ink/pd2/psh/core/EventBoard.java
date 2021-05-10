package ink.pd2.psh.core;

import java.util.HashMap;
import java.util.LinkedHashSet;

public final class EventBoard {
	private final static HashMap<String, LinkedHashSet<Event>> eventsMap
			= new HashMap<>();

	public static LinkedHashSet<Event> addKey(int cid, String key) {
		if (!Permission.check(cid, Permission.ADD_EVENT_KEY)) return null;
		return addKey(key);
	}
	protected static LinkedHashSet<Event> addKey(String key) {
		return eventsMap.computeIfAbsent(key, k -> new LinkedHashSet<>());
	}

	public static LinkedHashSet<Event> removeKey(int cid, String key) {
		if (!Permission.check(cid, Permission.MANAGE_EVENTS)) return null;
		return removeKey(key);
	}
	protected static LinkedHashSet<Event> removeKey(String key) {
		return eventsMap.remove(key);
	}

	public static boolean addEvent(int cid, String key, Event event)
			throws NoSuchEventKeyException {
		if (!Permission.check(cid, Permission.ADD_EVENT)) return false;
		return addEvent(key, event);
	}
	protected static boolean addEvent(String key, Event event)
			throws NoSuchEventKeyException {
		LinkedHashSet<Event> set = eventsMap.get(key);
		if (set == null) throw new NoSuchEventKeyException(
				"The key '" + key + "' is not found. Have you added it?");
		return set.add(event);
	}

	public static Event[] getEvents(int cid, String key) {
		if (!Permission.check(cid, Permission.READ_EVENTS)) return null;
		return getEvents(key);
	}
	protected static Event[] getEvents(String key) {
		LinkedHashSet<Event> set = eventsMap.get(key);
		if (set == null) return null;
		return set.toArray(new Event[0]);
	}

	public static boolean removeEvent(int cid, String key, Event event) {
		if (!Permission.check(cid, Permission.MANAGE_EVENTS)) return false;
		return removeEvent(key, event);
	}
	protected static boolean removeEvent(String key, Event event) {
		LinkedHashSet<Event> set = eventsMap.get(key);
		if (set == null) return false;
		return set.remove(event);
	}
}
