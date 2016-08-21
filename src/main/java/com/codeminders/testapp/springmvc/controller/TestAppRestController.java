package com.codeminders.testapp.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codeminders.testapp.springmvc.model.NameLink;
import com.codeminders.testapp.springmvc.service.NameLinkService;

@RestController
public class TestAppRestController {

	@Autowired
	private NameLinkService nameLinkService;

	@RequestMapping(value = "/names/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NameLink> getUser(@PathVariable("name") String name) {
		NameLink nameLink = nameLinkService.findByName(name);
		if (nameLink == null) {
			return new ResponseEntity<NameLink>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<NameLink>(nameLink, HttpStatus.OK);
	}

	@RequestMapping(value = "/names/{name}", method = RequestMethod.PUT)
	public ResponseEntity<NameLink> createOrUpdateNameLink(
			@PathVariable("name") String name, @RequestBody NameLink nameLink) {
		nameLink.setName(name);
		nameLinkService.createUpdateNameLink(name, nameLink);
		return new ResponseEntity<NameLink>(nameLink, HttpStatus.OK);
	}

	@RequestMapping(value = "/names", method = RequestMethod.DELETE)
	public ResponseEntity<NameLink> deleteAllNameLinks() {
		nameLinkService.deleteAllNameLinks();
		return new ResponseEntity<NameLink>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/annotate", method = RequestMethod.POST, consumes = "text/plain", produces = "text/plain")
	public String annotateHtmlSnippet(@RequestBody String htmlForAnnotation) {
		return nameLinkService.annotateHtmlCode(htmlForAnnotation);
	}

}
