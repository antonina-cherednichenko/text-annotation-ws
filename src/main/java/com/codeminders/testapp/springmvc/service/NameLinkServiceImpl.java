package com.codeminders.testapp.springmvc.service;

import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Service;

import com.codeminders.testapp.springmvc.model.NameLink;

@Service("nameLinkService")
public class NameLinkServiceImpl implements NameLinkService {

	//private Map<String, NameLink> nameLinks = new HashMap<String, NameLink>();
	//private ConcurrentNavigableMap<String, NameLink> nameLinks = DBMaker.tempTreeMap();
	private HTreeMap<String, NameLink> nameLinks = DBMaker.tempHashMap();

	public NameLink findByName(String name) {
		return nameLinks.get(name);
	}

	public void createUpdateNameLink(String name, NameLink nameLink) {
		nameLinks.put(name, nameLink);
	}

	public void deleteAllNameLinks() {
		nameLinks.clear();
	}

	public String annotateHtmlCode(String htmlBody) {
		return AnnotateHtmlUtils.annotateHtmlForAllNameLinks(htmlBody, nameLinks);
	}
}
