/*******************************************************************************
 * Copyright (c) 2018, 2021 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Christian Dietrich - Initial contribution and API
 */
public class JavaVersionTest {
	@Test
	public void test_fromBree() {
		assertEquals(JavaVersion.JAVA15, JavaVersion.fromBree("JavaSE-15"));
		assertEquals(JavaVersion.JAVA14, JavaVersion.fromBree("JavaSE-14"));
		assertEquals(JavaVersion.JAVA13, JavaVersion.fromBree("JavaSE-13"));
		assertEquals(JavaVersion.JAVA12, JavaVersion.fromBree("JavaSE-12"));
		assertEquals(JavaVersion.JAVA11, JavaVersion.fromBree("JavaSE-11"));
		assertEquals(JavaVersion.JAVA10, JavaVersion.fromBree("JavaSE-10"));
		assertEquals(JavaVersion.JAVA9, JavaVersion.fromBree("JavaSE-9"));
		assertEquals(JavaVersion.JAVA8, JavaVersion.fromBree("JavaSE-1.8"));
		assertNull(JavaVersion.fromBree("JavaSE-1.11"));
		assertNull(JavaVersion.fromBree("JavaSE-1.10"));
		assertNull(JavaVersion.fromBree(null));
	}
	
	@Test
	public void testFromQualifier() {
		assertEquals(JavaVersion.JAVA15, JavaVersion.fromQualifier("15"));
		assertEquals(JavaVersion.JAVA15, JavaVersion.fromQualifier("1.15"));
		assertEquals(JavaVersion.JAVA14, JavaVersion.fromQualifier("14"));
		assertEquals(JavaVersion.JAVA14, JavaVersion.fromQualifier("1.14"));
		assertEquals(JavaVersion.JAVA13, JavaVersion.fromQualifier("13"));
		assertEquals(JavaVersion.JAVA13, JavaVersion.fromQualifier("1.13"));
		assertEquals(JavaVersion.JAVA12, JavaVersion.fromQualifier("12"));
		assertEquals(JavaVersion.JAVA12, JavaVersion.fromQualifier("1.12"));
		assertEquals(JavaVersion.JAVA11, JavaVersion.fromQualifier("11"));
		assertEquals(JavaVersion.JAVA11, JavaVersion.fromQualifier("1.11"));
		assertEquals(JavaVersion.JAVA10, JavaVersion.fromQualifier("10"));
		assertEquals(JavaVersion.JAVA10, JavaVersion.fromQualifier("1.10"));
		assertEquals(JavaVersion.JAVA9, JavaVersion.fromQualifier("9"));
		assertEquals(JavaVersion.JAVA9, JavaVersion.fromQualifier("1.9"));
		assertEquals(JavaVersion.JAVA8, JavaVersion.fromQualifier("8"));
		assertEquals(JavaVersion.JAVA8, JavaVersion.fromQualifier("1.8"));
		assertEquals(JavaVersion.JAVA7, JavaVersion.fromQualifier("7"));
		assertEquals(JavaVersion.JAVA7, JavaVersion.fromQualifier("1.7"));
		assertEquals(JavaVersion.JAVA6, JavaVersion.fromQualifier("6"));
		assertEquals(JavaVersion.JAVA6, JavaVersion.fromQualifier("1.6"));
		assertEquals(JavaVersion.JAVA5, JavaVersion.fromQualifier("1.5"));
		assertNull(JavaVersion.fromQualifier("1.4"));
		assertNull(JavaVersion.fromQualifier("5"));
		assertNull(JavaVersion.fromQualifier(null));
	}
	
	@Test
	public void test_getQualifier() {
		assertEquals("15", JavaVersion.JAVA15.getQualifier());
		assertEquals("14", JavaVersion.JAVA14.getQualifier());
		assertEquals("13", JavaVersion.JAVA13.getQualifier());
		assertEquals("12", JavaVersion.JAVA12.getQualifier());
		assertEquals("11", JavaVersion.JAVA11.getQualifier());
		assertEquals("10", JavaVersion.JAVA10.getQualifier());
		assertEquals("9", JavaVersion.JAVA9.getQualifier());
		assertEquals("1.8", JavaVersion.JAVA8.getQualifier());
	}
	

	@Test
	public void test_isAtLeast() {
		assertTrue(JavaVersion.JAVA9.isAtLeast(JavaVersion.JAVA9));
		assertTrue(JavaVersion.JAVA9.isAtLeast(JavaVersion.JAVA8));
		assertFalse(JavaVersion.JAVA9.isAtLeast(JavaVersion.JAVA10));
	}

	@Test
	public void test_toJdtClassFileConstant () {
		assertEquals(3866624, JavaVersion.JAVA15.toJdtClassFileConstant());
		assertEquals(3801088, JavaVersion.JAVA14.toJdtClassFileConstant());
		assertEquals(3735552, JavaVersion.JAVA13.toJdtClassFileConstant());
		assertEquals(3670016, JavaVersion.JAVA12.toJdtClassFileConstant());
		assertEquals(3604480, JavaVersion.JAVA11.toJdtClassFileConstant());
		assertEquals(3538944, JavaVersion.JAVA10.toJdtClassFileConstant());
		assertEquals(3473408, JavaVersion.JAVA9.toJdtClassFileConstant());
		assertEquals(3407872, JavaVersion.JAVA8.toJdtClassFileConstant());
	}

}
