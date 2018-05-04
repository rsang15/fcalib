package de.tudresden.inf.tcs.fcalib.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.utils.ListSet;

/*
 * FCAlib: An open-source extensible library for Formal Concept Analysis 
 *         tool developers
 * Copyright (C) 2009  Baris Sertkaya
 *
 * This file is part of FCAlib.
 * FCAlib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FCAlib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FCAlib.  If not, see <http://www.gnu.org/licenses/>.
 */

public class TestListSet extends TestCase {
	 
	List<String> list4 = Arrays.asList("Lars", "Simon");
	public TestListSet() {
	}

	private void assertEq(ListSet<String> ls, List<String> list) {
		assertTrue(ls.equals(list));
		assertEquals(ls.size(), list.size());
		assertTrue(ls.containsAll(list));
		assertTrue(list.containsAll(ls));
		for (int i = 0; i < ls.size(); i++) {
            assertEquals(ls.getElementAt(i), list.get(i));
		}
	}

	public void testListSet() {
		List<String> list4 = Arrays.asList("Lars", "Simon");
		List<String> list5 = Arrays.asList("Lars", "Simon", "Bob");
		ListSet<String> listset = new ListSet<>(list4);
		listset.addAll(list4);
		listset.removeAll(list4);
		listset.clear();
		listset.addAll(list4);
		listset.retainAll(list4);
		listset.toArray();
		listset.getIndexOf("a");
		listset.getIndexOf("00030");
		listset.remove("329840");
		listset.containsAll(list5);
		listset.contains(list5);
        listset.contains(list4);	
        
		ListSet<String> ls = new ListSet<>();
		ls.add("a");
		ls.add("b");
		ls.add("c");
		ls.add("d");

		List<String> list0 = new ArrayList<>();
		list0.add("a");
		list0.add("b");
		list0.add("c");
		list0.add("d");

		List<String> list1 = new ArrayList<>();
		list1.add("b");
		list1.add("c");
		list1.add("d");
		list1.add("a");

		List<String> list2 = new ArrayList<>();
		list2.add("c");
		list2.add("d");
		list2.add("a");
		list2.add("b");

		List<String> list3 = new ArrayList<>();
		list3.add("d");
		list3.add("a");
		list3.add("b");
		list3.add("c");

		assertEq(ls, list0);
		ls.changeOrder();
		assertEq(ls, list1);
		ls.changeOrder();
		assertEq(ls, list2);
		ls.changeOrder();
		assertEq(ls, list3);
		ls.changeOrder();
		assertEq(ls, list0);
		ls.changeOrder();
		assertEq(ls, list1);
		
		String[] x = {"ds", "dsf"};
		list5.toArray(x);
		
		ListSet<String> ls1 = new ListSet<>();
        try{
        	ls1.add(null);
		}catch(Exception e){
			e.getClass().equals(NullPointerException.class);
		}
        assertTrue(listset.getElementAt(0) != null);
        assertTrue(listset.getIndexOf("lars") != 0);
        assertFalse(listset.getIndexOf("lars") != -1);
	}

}
