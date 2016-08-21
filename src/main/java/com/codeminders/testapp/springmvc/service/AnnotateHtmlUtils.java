package com.codeminders.testapp.springmvc.service;

import java.util.List;
import java.util.Map;

import com.codeminders.testapp.springmvc.model.NameLink;
import com.jaunt.Element;
import com.jaunt.Node;
import com.jaunt.ResponseException;
import com.jaunt.Text;
import com.jaunt.UserAgent;

public class AnnotateHtmlUtils {

	public static String annotateHtmlForAllNameLinks(String htmlBody, Map<String, NameLink> nameLinks) {
		UserAgent userAgent = new UserAgent();
		try {
			userAgent.openContent(htmlBody);
			Element root = userAgent.doc.getRoot();
			updateElementNodes(root, nameLinks);

			return userAgent.doc.innerHTML();
		} catch (ResponseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void updateElementNodes(Element element, Map<String, NameLink> nameLinks) {
		List<Node> childNodes = element.getChildNodes();
		for (Node node : childNodes) {
			if (node.getType() == Node.ELEMENT_TYPE) {
				Element nodeElement = (Element) node;
				if (!isHtmlLinkTag(nodeElement)) {
					updateElementNodes(nodeElement, nameLinks);
				}
			} else if (node.getType() == Node.TEXT_TYPE) {
				Text textNode = (Text) node;
				for (Map.Entry<String, NameLink> entry : nameLinks.entrySet()) {
					String newValue = replaceWords(textNode.getValue(), entry.getValue());
					textNode.setValue(newValue);
				}
			}
		}
	}

	private static String replaceWords(String text, NameLink nameLink) {
		return text.replaceAll("\\b" + nameLink.getName() + "\\b", formReplacementString(nameLink));
	}

	private static String formReplacementString(NameLink nameLink) {
		return "<a href=" + nameLink.getUrl() + ">" + nameLink.getName() + "</a>";
	}

	private static boolean isHtmlLinkTag(Element element) {
		return element.getName().equals("a");
	}

}
