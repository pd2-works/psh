package ink.pd2.shell.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public final class XMLUtils {
	private XMLUtils() {}
	public final static XMLUtils INS = new XMLUtils();

	public Document readFile(File file) throws DocumentException {
		return new SAXReader().read(file);
	}
	public Document readFile(InputStream stream) throws DocumentException {
		return new SAXReader().read(stream);
	}
	public Element[] getAllChildElements(Element element, String name) {
		ArrayList<Element> elements = new ArrayList<>();
		Element temp;
		for (Iterator<Element> i = element.elementIterator(name); i.hasNext();) {
			temp = i.next();
			elements.add(temp);
		}
		return elements.toArray(new Element[0]);
	}
}
