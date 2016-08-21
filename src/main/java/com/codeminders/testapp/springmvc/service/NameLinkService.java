package com.codeminders.testapp.springmvc.service;

import com.codeminders.testapp.springmvc.model.NameLink;

public interface NameLinkService {

	public NameLink findByName(String name);

	public void createUpdateNameLink(String name, NameLink nameLink);

	public void deleteAllNameLinks();

	public String annotateHtmlCode(String htmlBody);

}
