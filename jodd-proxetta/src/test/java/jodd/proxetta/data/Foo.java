// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.proxetta.data;

public class Foo {

	public void m1() {
		System.out.println("Foo.m1");
	}

	protected void m2() {
		System.out.println("Foo.m2");
	}

	protected String a1;

	public String a2;

	public String p1(@FooAnn String in) {
		return in;
	}

}
