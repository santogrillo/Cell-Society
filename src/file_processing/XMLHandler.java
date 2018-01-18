package file_processing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLHandler {

	protected final String TYPE_ATTRIBUTE;

	public XMLHandler(String type) {
		TYPE_ATTRIBUTE = type;
	}

	// Helper method to do the boilerplate code needed to make a documentBuilder.
	protected DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}

	protected Transformer getTransformer() {
		try {
			return TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			throw new XMLException(e);
		}
	}

	/*
	 * Returns NodeList of elements of a specified tagName
	 */
	protected NodeList getChildren(Element root, String tagName) {
		NodeList nodeList = root.getElementsByTagName(tagName);
		return nodeList.item(0).getChildNodes();
	}

	// Get value of Element's attribute
	protected String getAttribute(Element e, String attributeName) {
		return e.getAttribute(attributeName);
	}

	// Returns if this is a valid XML file for the specified object type
	protected boolean isValidFile(Element root, String type) {
		return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
	}
}