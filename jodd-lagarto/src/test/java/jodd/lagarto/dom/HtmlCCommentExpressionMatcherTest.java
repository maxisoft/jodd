// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.lagarto.dom;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HtmlCCommentExpressionMatcherTest {

	@Test
	public void testSingleExpressions() {
		HtmlCCommentExpressionMatcher m = new HtmlCCommentExpressionMatcher();

		assertFalse(m.match(5, "if IE 6"));
		assertTrue(m.match(6, "if IE 6.0"));

		assertTrue(m.match(5, "if !IE 6"));
		assertFalse(m.match(6, "if !IE 6.0"));

		assertTrue(m.match(5, "if lt IE 6"));
		assertFalse(m.match(6, "if lt IE 6.0"));
		assertFalse(m.match(7, "if lt IE 6"));

		assertTrue(m.match(5, "if lte IE 6"));
		assertTrue(m.match(6, "if lte IE 6.0"));
		assertFalse(m.match(7, "if lte IE 6.0"));

		assertFalse(m.match(5, "if gt IE 6.0"));
		assertFalse(m.match(6, "if gt IE 6"));
		assertTrue(m.match(7, "if gt IE 6"));

		assertFalse(m.match(5, "if gte IE 6"));
		assertTrue(m.match(6, "if gte IE 6"));
		assertTrue(m.match(7, "if gte IE 6.0"));

		assertFalse(m.match(5.5f, "if gte IE 6"));

		assertTrue(m.match(5.4f, "if IE 5"));
		assertTrue(m.match(5.6f, "if IE 5"));
		assertFalse(m.match(5.5f, "if IE 5.6"));

		assertFalse(m.match(9.1f, "if !IE 9"));
		assertFalse(m.match(9.1f, "if !IE 9.0"));
		assertTrue(m.match(9.1f, "if !IE 9.2"));

		assertFalse(m.match(9.1f, "if gt IE 9"));
		assertFalse(m.match(9.1f, "if gt IE 9.0"));
		assertTrue(m.match(9.1f, "if gt IE 9.01"));

		assertFalse(m.match(9.1f, "if lt IE 9"));
		assertFalse(m.match(9.1f, "if lt IE 9.0"));
		assertTrue(m.match(9.1f, "if lt IE 9.2"));

		assertTrue(m.match(9.1f, "if gte IE 9"));
		assertTrue(m.match(9.1f, "if gte IE 9.0"));
		assertFalse(m.match(9.1f, "if gte IE 9.2"));

		assertTrue(m.match(9.1f, "if lte IE 9"));
		assertTrue(m.match(9.1f, "if lte IE 9.0"));
		assertFalse(m.match(9.1f, "if lte IE 9.01"));
	}

	@Test
	public void testTwoExpressions() {
		HtmlCCommentExpressionMatcher m = new HtmlCCommentExpressionMatcher();

		assertTrue(m.match(5, "if (lt IE 6)&(lt IE 7)"));
		assertFalse(m.match(6, "if (lt IE 6)&(lt IE 7)"));
		assertFalse(m.match(7, "if (lt IE 6)&(lt IE 7)"));

		assertTrue(m.match(5, "if (lt IE 6)|(lt IE 7)"));
		assertTrue(m.match(6, "if (lt IE 6)|(lt IE 7)"));
		assertFalse(m.match(7, "if (lt IE 6)|(lt IE 7)"));
		assertTrue(m.match(7, "if (lt IE 6)|(lte IE 7)"));

		assertTrue(m.match(6.5f, "if (IE 6)|(IE 7)"));
	}
}