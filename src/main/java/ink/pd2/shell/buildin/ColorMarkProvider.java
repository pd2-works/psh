package ink.pd2.shell.buildin;

import ink.pd2.shell.api.Extension;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.MarkProvider;
import org.fusesource.jansi.Ansi;

import java.util.HashMap;
import java.util.Properties;

public class ColorMarkProvider implements MarkProvider {
	public final static ColorMarkProvider INS = new ColorMarkProvider();

	private final HashMap<String, Integer> colors = new HashMap<>();
	public void putColor(String key, int value) {
		colors.put(key, value);
	}
	public void removeColor(String key) {
		colors.remove(key);
	}

	@Override
	public String getSign() {
		return "color";
	}

	@Override
	public String onMarkUpdate(Mark mark, String value) {
		//================================================
		//||  "&color:fg.bg&"                           ||
		//||  "&color:fg.bg[colored_text]normal_text&"  ||
		//================================================
		int i1 = value.indexOf('[');
		if (i1 == -1) {
			String[] color = value.split("\\.");
			if (color.length == 2) {
				Ansi a = Ansi.ansi();
				if (colors.containsKey(color[0])) a = a.fg(colors.get(color[0]));
				if (colors.containsKey(color[1])) a = a.bg(colors.get(color[1]));
				return a.toString();
			}
		}
		int i2 = value.lastIndexOf(']');
		if (i1 < i2) {
			String[] color  = value.substring(0, i1 - 1).split("\\.");
			String   ansi   = value.substring(i1 + 1, i2);
			String   normal = value.substring(i2 + 1);
			if (color.length == 2) {
				Ansi a = Ansi.ansi();
				if (colors.containsKey(color[0])) a = a.fg(colors.get(color[0]));
				if (colors.containsKey(color[1])) a = a.bg(colors.get(color[1]));
				return a.a(ansi).reset().a(normal).toString();
			}
		}
		return value;
	}

}
