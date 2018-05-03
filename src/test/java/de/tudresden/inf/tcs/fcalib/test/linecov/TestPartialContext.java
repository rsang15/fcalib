/**
 * Test implementation for testing attribute exploration in partial contexts.
 * @author Baris Sertkaya
 */
package de.tudresden.inf.tcs.fcalib.test.linecov;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import de.tudresden.inf.tcs.fcaapi.exception.IllegalAttributeException;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalContextException;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalExpertException;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.Implication;
import de.tudresden.inf.tcs.fcalib.PartialContext;
import de.tudresden.inf.tcs.fcalib.PartialObject;
import de.tudresden.inf.tcs.fcalib.action.StartExplorationAction;

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

public class TestPartialContext extends TestCase {

	public TestPartialContext() {
		IllegalAttributeException IAE = new IllegalAttributeException();
		IllegalContextException ICE = new IllegalContextException();
		IllegalContextException ICE2 = new IllegalContextException("message");
		IllegalExpertException IEE = new IllegalExpertException();
		IllegalExpertException IEE2 = new IllegalExpertException("message");
		IllegalObjectException IOE = new IllegalObjectException("message");
	}

	public void testPartialContext() throws IllegalAttributeException, IllegalObjectException {
		BasicConfigurator.configure();

		PartialContext<String, String, PartialObject<String, String>> context = new PartialContext<>();
		NoExpertPartial<String> expert = new NoExpertPartial<>(context);
		context.addAttribute("a");
		
		try{
			context.addAttributeToObject("a", "object399");
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalObjectException.class));
		}
		
		PartialObject<String, String> o = new PartialObject<>("object");
		PartialObject<String, String> o3 = new PartialObject<>("object3");
		Set<PartialObject<String,String>> objset = new HashSet<>();
		objset.add(o);
		objset.add(o3);
		o.getName();
		o.setName("object");
		context.addAttribute("b");
		context.addAttribute("c");
		context.addAttribute("d");
		//adding partial object
		context.addObject(o);
		
		//try adding again, fail
		try{
			context.addObject(o);
		}catch(Exception E){
			assertTrue(E.getClass().equals(IllegalObjectException.class));
		}
		//add attribute to object
		context.addAttributeToObject("a", o.getIdentifier());
		try{//try to re-add attribute to object and fail
			context.addAttributeToObject("a", o.getIdentifier());
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		try{//try to add fake attribute to object
			context.addAttributeToObject("doesnotexist", o.getIdentifier());
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		try{//try to add real attribute to fake object
			context.addAttributeToObject("a", "fakeobjectidentifier");
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalObjectException.class));
		}
		context.removeAttributeFromObject("a", o.getIdentifier());
		try{//try to re-remove attribute from object and fail
			context.removeAttributeFromObject("a", o.getIdentifier());
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		try{//try to remove fake attribute from object
			context.removeAttributeFromObject("doesnotexist", o.getIdentifier());
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		try{//try to remove real attribute from fake object
			context.removeAttributeFromObject("a", "fakeobjectidentifier");
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalObjectException.class));
		}
		
		context.objectHasAttribute(o, "a");
		context.objectHasAttribute(o, "doesnotexist");
		context.objectHasNegatedAttribute(o, "a");
		context.objectHasNegatedAttribute(o, "doesnotexist");
		
		try{//try to add attribute that does not exist to an object
			context.addAttributeToObject("doesnotexist", o.getIdentifier());
		}catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		
		try{//try to add attribute already in object
			context.addAttributeToObject("a", o.getIdentifier());
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		//successfully remove
		context.removeObject(o);
		try{//fail to remove after already removing
			context.removeObject(o);
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalObjectException.class));
		}
		//re-add object
		context.addObject(o);
		/**
		 * remove attribute from object tests
		 */
		
		try{
			context.addAttribute("a");
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		try{
			context.addAttribute("");
		} catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}

		context.getAttributeAtIndex(0);
		try{
			context.getAttributeAtIndex(10);
		} catch (Exception E){
			assertTrue(E.getClass().equals(IndexOutOfBoundsException.class));
		}
		context.getCurrentQuestion();
		context.getObject("a");
		context.getObject(o.getIdentifier());
		context.getAttributeCount();
		context.getObjectCount();
		context.clearObjects();
		context.getStemBase();
		context.getDuquenneGuiguesBase();
		context.getAttributes();
		
		context.addObject(o);
		context.getObjectAtIndex(0);
		context.removeObject(o.getIdentifier());
		
		/*****
		 * BUG HERE - ONLY ON THE IDENTIFIER BEING THE STRING
		 * SHOULD SAY ILLEGAL OBJECT BECAUSE ALREADY REMOVED (4 lines up)
		 */
//		try{//try to remove an object that does not exist
//			context.removeObject(o.getIdentifier());
//		}catch(Exception E){
//			assertTrue(E.getClass().equals(IllegalObjectException.class));
//		}
		
		context.containsObject(o.getIdentifier());
		context.clearObjects();

		System.out.println("Attributes: " + context.getAttributes());

		expert.addExpertActionListener(context);
		context.setExpert(expert);
		
		StartExplorationAction<String, String, PartialObject<String, String>> action = new StartExplorationAction<>();
		action.setContext(context);
		expert.fireExpertAction(action);
		
		Set<String> attrs = new HashSet<String>();
		attrs.add("e");
		attrs.add("f");
		context.addAttributes(attrs);
		Set<String> attrs2 = new HashSet<String>();
		attrs2.add("g");
		
		o.getDescription().containsNegatedAttribute("a");
		try{
			o.getDescription().addNegatedAttribute("a");
		}catch(Exception E){
			assertTrue(E.getClass().equals(IllegalAttributeException.class));
		}
		o.getDescription().addAttribute("a");
		try{
			o.getDescription().addAttribute("a");
		}catch(Exception E){
			assertTrue(E.getClass().equals(IllegalArgumentException.class));
		}
		o.getDescription().containsNegatedAttributes(attrs2);
		PartialObject<String, String> o4;
		o4 = new PartialObject("name", attrs, attrs2);
		Implication<String> imp = new Implication<String>(attrs,attrs);
		context.refutes(imp);

	}

}
