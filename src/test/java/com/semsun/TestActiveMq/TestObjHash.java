package com.semsun.TestActiveMq;

import org.junit.Test;

import com.semsun.util.Md5Util;

public class TestObjHash {
	
	class TmpObj {
		private String name;
		private int value;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
	}
	
	@Test
	public void TestObjectHash() {
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		
		TmpObj obj1 = new TmpObj();
		
		System.out.println(String.format("A Hash:%d", a.hashCode()));
		System.out.println(String.format("B Hash:%d", b.hashCode()));
		System.out.println(String.format("A MD5:%s", Md5Util.string2MD5("123")));
		System.out.println(String.format("B MD5:%s", Md5Util.string2MD5("124")));
	}
	
	@Test
	public void stringHash() {
		String a = "aaa";
		String b = "bbb";
		String c = "ccc";
		String d = "ddd";
		
		System.out.println(String.format("A Hash:%d %d", a.hashCode(), a.hashCode() % 4));
		System.out.println(String.format("B Hash:%d %d", b.hashCode(), b.hashCode() % 4));
		System.out.println(String.format("C Hash:%d %d", c.hashCode(), c.hashCode() % 4));
		System.out.println(String.format("D Hash:%d %d", d.hashCode(), d.hashCode() % 4));
	}

}
