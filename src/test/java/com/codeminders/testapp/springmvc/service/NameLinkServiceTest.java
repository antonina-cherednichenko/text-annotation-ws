package com.codeminders.testapp.springmvc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.codeminders.testapp.springmvc.model.NameLink;

public class NameLinkServiceTest {
	
	private NameLinkService service = new NameLinkServiceImpl();
	
	@Test
	public void testCreateFetchUpdate() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.org"));
		NameLink res = service.findByName("alex");
		assertEquals("http://alex.org", res.getUrl());
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex2.org"));
		NameLink res1 = service.findByName("alex");
		assertEquals("http://alex2.org", res1.getUrl());
	}
	
	@Test
	public void testDeleteAll() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		NameLink resBefore = service.findByName("alex");
		assertNotNull(resBefore);
		service.deleteAllNameLinks();
		NameLink resAfter = service.findByName("alex");
		assertNull(resAfter);
	}
	
	@Test 
	public void testSimpleAnnotation() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		String res1 = service.annotateHtmlCode("my name is alex");
		assertEquals(res1, "my name is <a href=http://alex.com>alex</a>");
		String res2 = service.annotateHtmlCode("my name is todd");
		assertEquals(res2, "my name is todd");
	}
	
	@Test
	public void testAnnotationMultipleNames() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		service.createUpdateNameLink("bo", new NameLink("bo", "http://bo.com"));
		service.createUpdateNameLink("casey", new NameLink("casey", "http://casey.com"));
		String res1 = service.annotateHtmlCode("alex, bo, and casey went to the park.");
		String res2 = service.annotateHtmlCode("alex alexander alexandria alexbocasey");
		assertEquals(res1, "<a href=http://alex.com>alex</a>, <a href=http://bo.com>bo</a>, and <a href=http://casey.com>casey</a> went to the park.");
		assertEquals(res2, "<a href=http://alex.com>alex</a> alexander alexandria alexbocasey");
	}
	
	@Test
	public void testHtmlCorrectness() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		String res1 = service.annotateHtmlCode("<div data-alex=alex>alex</div>");
		String res2 = service.annotateHtmlCode("<a href=http://foo.com>alex is already linked</a> but alex is not");
		String res3 = service.annotateHtmlCode("<div><p>this is paragraph 1 about alex.</p><p>alex's paragraph number 2.</p><p>and some closing remarks about alex</p></div>");
		assertEquals(res1, "<div data-alex=alex><a href=http://alex.com>alex</a></div>");
		assertEquals(res2, "<a href=http://foo.com>alex is already linked</a> but <a href=http://alex.com>alex</a> is not");
		assertEquals(res3, "<div><p>this is paragraph 1 about <a href=http://alex.com>alex</a>.</p><p><a href=http://alex.com>alex</a>'s paragraph number 2.</p><p>and some closing remarks about <a href=http://alex.com>alex</a></p></div>");
	}
	
	@Test
	public void testAdditionalAnnotations() {
		service.createUpdateNameLink("alex", new NameLink("alex", "http://alex.com"));
		service.createUpdateNameLink("bo", new NameLink("bo", "http://bo.com"));
		service.createUpdateNameLink("casey", new NameLink("casey", "http://casey.com"));
		String res1 = service.annotateHtmlCode("<div data-alex=alex>alex</div>");
		assertEquals(res1, "<div data-alex=alex><a href=http://alex.com>alex</a></div>");
		String res2 = service.annotateHtmlCode("<div><p>this is paragraph 1 about alex.</p><p>alex's paragraph number 2.</p><p>and some closing remarks about alex</p></div>");
		assertEquals(res2, "<div><p>this is paragraph 1 about <a href=http://alex.com>alex</a>.</p><p><a href=http://alex.com>alex</a>'s paragraph number 2.</p><p>and some closing remarks about <a href=http://alex.com>alex</a></p></div>");
	}
	
	@Test
	public void trickyCaseTest() {
		service.createUpdateNameLink("name", new NameLink("name", "https://name.com"));
		String res1 = service.annotateHtmlCode("<div class='<div class=\"name\">name</a>'>name</div>");
		assertEquals(res1, "<div class='<div class=\"name\">name</a>'><a href=https://name.com>name</a></div>");
		
	}

	
}