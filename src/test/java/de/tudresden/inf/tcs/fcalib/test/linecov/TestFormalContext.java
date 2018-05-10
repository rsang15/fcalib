/**
 * Test implementation for testing attribute exploration in partial contexts.
 * @author Baris Sertkaya
 */
package de.tudresden.inf.tcs.fcalib.test.linecov;

import junit.framework.TestCase;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import de.tudresden.inf.tcs.fcaapi.obsolete.ExpertAction;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalAttributeException;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.FormalContext;
import de.tudresden.inf.tcs.fcalib.FullObject;
import de.tudresden.inf.tcs.fcalib.FullObjectDescription;
import de.tudresden.inf.tcs.fcalib.Implication;
import de.tudresden.inf.tcs.fcalib.ImplicationSet;
import de.tudresden.inf.tcs.fcalib.PartialObject;
import de.tudresden.inf.tcs.fcalib.action.ChangeAttributeOrderAction;
import de.tudresden.inf.tcs.fcalib.action.CounterExampleProvidedAction;
import de.tudresden.inf.tcs.fcalib.action.QuestionConfirmedAction;
import de.tudresden.inf.tcs.fcalib.action.QuestionRejectedAction;
import de.tudresden.inf.tcs.fcalib.action.ResetExplorationAction;
import de.tudresden.inf.tcs.fcalib.action.StartExplorationAction;
import de.tudresden.inf.tcs.fcalib.action.StopExplorationAction;

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

public class TestFormalContext<O> extends TestCase {

	public TestFormalContext() {
	}

	public void testFormalContext() throws IllegalObjectException, CloneNotSupportedException, InstantiationException, IllegalAccessException {
		/**
		 * START OF ISP TESTING
		 */
		BasicConfigurator.configure();
		
		//initialize constructors
		FormalContext<String, String> context = new FormalContext<>();
		NoExpertFull<String> expert = new NoExpertFull<>(context);
		FullObject<String, String> o = new FullObject<>("object");
		
		//assert you can add attributes 
		//assertTrue added for mutation testing
		assertTrue(context.addAttribute("a"));
		context.addAttribute("b");
		context.addAttribute("c");
		
		//test you can't remove an object that is not in the context
		try{
			context.removeObject(o);
		} catch (Exception e){
			assertTrue(e.getClass().equals(IllegalObjectException.class));
		}
		
		//test you can't add an object twice
		context.addObject(o);
		try{
			context.addObject(o);
		} catch (Exception e){
			assertTrue(e.getClass().equals(IllegalObjectException.class));
		}
		
		//cover when object does not have attribute
		context.objectHasAttribute(o, "a");
		context.getObjectAtIndex(0);
		System.out.println("Attributes: " + context.getAttributes());
		//test getters for line coverage
		context.getAttributeAtIndex(1);
		context.getAttributeCount();
		context.getExpert();
		context.getConceptLattice();
		context.getObject(o.getIdentifier());
		context.getObject("a");
		//assert you can remove and readd an object
		assertTrue(context.removeObject(o));
		assertTrue(context.addObject(o));
		//successfully add attribute to object
		assertTrue(context.addAttributeToObject("a", o.getIdentifier()));
		
		//fail when trying to add same attribute again 
		try{
			context.addAttributeToObject("a", o.getIdentifier());
		} catch(Exception e){
			assertTrue(e.getClass().equals(IllegalAttributeException.class));
		}
		//fail when add attribute to object that does not exist
		try{
			context.addAttributeToObject("a", "object2");
		} catch(Exception e){
			assertTrue(e.getClass().equals(IllegalObjectException.class));
		}
		//cover when object does have attribute
		assertTrue(context.objectHasAttribute(o, "a"));
		//successfully remove attribute from an object
		assertTrue(context.removeAttributeFromObject("a", o.getIdentifier()));
		try{//fail when try to remove it again
			context.removeAttributeFromObject("a", o.getIdentifier());
		}catch(Exception e){
			assertTrue(e.getClass().equals(IllegalAttributeException.class));
		}
		try{//not a legal object
			context.removeAttributeFromObject("a", "object2");
		}catch(Exception e){
			assertTrue(e.getClass().equals(IllegalObjectException.class));
		}
		try{//not a legal attribute
			context.removeAttributeFromObject("z", "object");
		}catch(Exception e){
			assertTrue(e.getClass().equals(IllegalAttributeException.class));
		}
		try{//not in the object
			context.removeAttributeFromObject("e", "object");
		}catch(Exception e){
			assertTrue(e.getClass().equals(IllegalAttributeException.class));
		}
		//not a legal attribute
		try{
			context.addAttributeToObject("asdkfm3", "object");
		}catch(Exception e){
			assertTrue(e.getClass().equals(IllegalAttributeException.class));
		}
		//assert you can remove object
		assertTrue(context.removeObject("object"));
		context.clearObjects();
		//assert that you can't remove objects after clearing
		try{
			context.removeObject("a");
		}catch(Exception e){
			assertTrue(e.getClass().equals(NullPointerException.class));
		}
		/**
		 * START OF LINE COVERAGE
		 */
		//cover all getters, setters and booleans
		context.getExtents();
		context.getDuquenneGuiguesBase();
		context.getImplications();
		context.getObjects();
		context.getStemBase();
		context.allClosures();
		context.getIntents();
		Set<String> a = new HashSet<String>();
		context.closure(a);
		a.add("string");
		context.isClosed(a);
		context.getAttributes();
		context.getCurrentQuestion();
		context.getConcepts();
		context.getObjectCount();
		context.getExtents();
		
		
		StartExplorationAction<String, String, FullObject<String, String>> action = new StartExplorationAction<>();
		action.setContext(context);

		//context.addObjects((new Set<FullObject<String, String>>() set) );
		try{
			expert.fireExpertAction(action);
		}catch(Exception e){
			assertTrue(e.getClass().equals(NullPointerException.class));
		}
		
		context.setExpert(expert);
		
		expert.addExpertActionListener(context);
		expert.fireExpertAction(action);
		//NOTE: HAD TO SET THIS TO PUBLIC FOR TEST
		//ADD THIS TO NOT POSSIBLE TO COVER
		ResetExplorationAction<String, String, FullObject<String, String>> action2 = new ResetExplorationAction<String, String, FullObject<String, String>>(context);
		action2.setContext(context);
		
		StopExplorationAction<String, String, FullObject<String, String>> action3 = new StopExplorationAction<String, String, FullObject<String, String>>();
		action3.setContext(context);
		action3.getContext();
		assertTrue(action3.getContext() != null);
		expert.fireExpertAction(action3);
		expert.fireExpertAction(action2);
		assertTrue(context.getAttributeCount() == 0);
		assertTrue(context.getObjectCount() ==0);
		
		QuestionConfirmedAction<String, String, FullObject<String, String>> action4 = new QuestionConfirmedAction<String, String, FullObject<String, String>>();
		action4.setContext(context);
		//changed visibility for this as well
		action4.getQuestion();
		action4.setQuestion(context.getCurrentQuestion());
		action4.setEnabled(true);
		assertTrue(action4.getContext()!= null);
		assertTrue(action4.isEnabled());
		
		assertTrue(action4.getQuestion() != null);
		action4.getKeys();
		action4.setContext(context);
		assertTrue(action4.getContext()!= null);
		expert.fireExpertAction(action4);
		ChangeAttributeOrderAction<String, String, FullObject<String, String>> action5 = new ChangeAttributeOrderAction<String, String, FullObject<String, String>>();
		action5.setContext(context);
		action5.setContext(context);
		assertTrue(action5.getContext()!=null);
		
		action5.setEnabled(true);
		assertTrue(action5.isEnabled());
		try{
			expert.fireExpertAction(action5);
		} catch (Exception e){
			assertTrue(e.getClass().equals(IndexOutOfBoundsException.class));
		}
		try{
			expert.fireExpertAction(action5);
		} catch (Exception e){
			assertTrue(e.getClass().equals(IndexOutOfBoundsException.class));
		}
		Set<String> list = new HashSet<String>();
		list.add("m");
		list.add("d");
		list.add("object");
		Set<FullObject<String,String>> fullObjectList = new HashSet<FullObject<String,String>>();
		fullObjectList.add(o);
		
		/**
		 * START mutation assert functions for context
		 */
		assertTrue(context.getAttributes() != null);
		context.setExpert(expert);
		assertTrue(context.getExpert() != null);
		assertTrue(context.getImplications() != null);
		assertTrue(context.getCurrentQuestion() != null);
		context.addObject(o);
		assertTrue(context.getObjectCount() != 0);
		assertTrue(context.getObject("object") != null);
		assertTrue(context.getObjectAtIndex(0) != null);
		context.removeObject(o);
		assertTrue(context.addAttributes(list));
		assertTrue(context.addObjects(fullObjectList));
		
		/**
		 * Begin Implication tests - ISP 
		 */
		Implication<String> imp = new Implication<String>(list,list);
		context.setCurrentQuestion(imp); //made this public to test it
		context.initializeExploration();
		ImplicationSet<String> set = (ImplicationSet<String>) context.getImplications();
		set.getContext();
		
		set.allClosures();
		assertTrue(set.isClosed(list)); //MUTATION
		Implication<String> imp2 = new Implication<String>();
		assertTrue(set.add(imp2)); //MUTATION
		set.closure(list);
		set.add(imp);
		set.closure(list);
		assertFalse(context.refutes(imp2)); //MUTATION
		context.clearObjects();
		assertTrue(context.getObjectCount()==0); //MUTATION
		assertFalse(context.followsFromBackgroundKnowledge(imp)); //MUTATION

		Set<String> attrs = new HashSet<String>();
		attrs.add("e");
		attrs.add("f");
		assertTrue(context.addAttributes(attrs));
		Set<String> attrs2 = new HashSet<String>();
		attrs2.add("g");
		
		assertTrue(set.getContext() != null); //MUTATION
		o.getDescription().clone();
		context.refutes(imp);
		context.refutes(imp2);
		
		/**
		 * Begin Expert Action coverage - ISP AND LINE COVERAGE
		 */
		//test expert action contructors and getters 
		Object x = new Object();
		ExpertAction<String, String> ex = new ExpertAction<String, String>(x, 2); //MADE CLASS PUBLIC
		ExpertAction<String, String> ex2 = new ExpertAction<String, String>(x, 2, imp); //MADE CLASS PUBLIC
		ExpertAction<String, String> ex3 = new ExpertAction<String, String>(x, 2, imp, "test"); //MADE CLASS PUBLIC
		ex3.getType();
		ex3.getQuestion();
		ex3.getCounterExample();
		assertFalse(imp.equals(imp2));
		Implication<String> imp3 = imp2;
		assertTrue(imp2.equals(imp3));
		
		//test counter example provided action
		CounterExampleProvidedAction<String,String,FullObject<String,String>> provided = new CounterExampleProvidedAction<String,String,FullObject<String,String>>(context,imp,o);
		CounterExampleProvidedAction<String,String,FullObject<String,String>> provided2 = new CounterExampleProvidedAction<String,String,FullObject<String,String>>(null,null,null);
		assertTrue(provided.getCounterExample()!= null);
		provided2.getCounterExample();
		
		//test question rejected action
		QuestionRejectedAction<String,String,FullObject<String,String>> action6 =
				 new QuestionRejectedAction<>();
		action6.setContext(context);
		action6.setQuestion(context.getCurrentQuestion());
		action6.getContext();
		action6.getQuestion();
		
		
		/**
		 * START OF MUTATION TEST ADDITIONS FOR ACTIONS
		 * AND VARIOUS UNCOVERED GETTER AND SETTERS FOR RETURNVAL
		 */
		
		//stack overflow
		//expert.fireExpertAction(action6);
		
		assertTrue(provided.getCounterExample() != null);
		o.toString();
		assertTrue(o.toString() != null);
		assertTrue(o.getDescription() != null);
		assertTrue(o.getName() != null);
		assertTrue(o.getIdentifier() != null);
		assertTrue(context.isExpertSet());
		assertFalse(context.isClosed(a));
		assertTrue(action6.getQuestion() != null);
		assertTrue(action6.getContext() != null);
		assertTrue(ex3.getCounterExample()!= null);
		assertTrue(ex3.getQuestion() != null);
		assertTrue(ex3.getSource() != null);
		assertTrue(ex3.getType() != 0);
		assertTrue(o.getDescription() != null);
		assertTrue(o.getIdentifier() != null);
		assertTrue(o.getName() != null);
		assertTrue(o.getDescription().addAttributes(attrs));
		assertTrue(set.getContext() != null);
		try{
			set.allClosures();
		}catch(Exception e){
			assertTrue(e.getClass().equals(NullPointerException.class));
		}
		assertTrue(set.isClosed(list));
		
	}

}

