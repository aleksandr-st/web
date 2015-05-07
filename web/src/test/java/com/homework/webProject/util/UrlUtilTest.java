package com.homework.webProject.util;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

public class UrlUtilTest {
	private static String path = "/path";
	private static final String TRUE_CHARACTER_ENCODING = "UTF-8";
	private static final String FALSE_CHARACTER_ENCODING = "FFF";
	private static HttpServletRequest httpServletRequest;
	
	@Before
	public void setUp() throws Exception {
		httpServletRequest = new MockHttpServletRequest();
	}

	@Test
	public void testEncodeUrlPathSegment() {
		try {
			httpServletRequest.setCharacterEncoding(TRUE_CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String newPath = UrlUtil.encodeUrlPathSegment(path, httpServletRequest);
		try {
			path = UriUtils.encodePathSegment(path, TRUE_CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		assertNotNull(newPath);
		assertEquals(path,newPath);
	}

	@Test
	public void testEncodeUrlPathSegmentWithoutEncSet() {
		String newPath = UrlUtil.encodeUrlPathSegment(path, httpServletRequest);
		try {
			path = UriUtils.encodePathSegment(path, WebUtils.DEFAULT_CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		assertNotNull(newPath);
		assertEquals(path,newPath);
	}

	@Test//(expected = java.io.UnsupportedEncodingException)
	public void testEncodeUrlPathSegmentFalse() {
		try {
			httpServletRequest.setCharacterEncoding(FALSE_CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		String newPath = UrlUtil.encodeUrlPathSegment(path, httpServletRequest);
		assertNotNull(newPath);
		assertEquals(path,newPath);
	}
}
