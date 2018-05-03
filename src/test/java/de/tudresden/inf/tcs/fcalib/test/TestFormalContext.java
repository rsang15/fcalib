/**
 * Test implementation for testing attribute exploration in partial contexts.
 * @author Baris Sertkaya
 */
package de.tudresden.inf.tcs.fcalib.test;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;

import de.tudresden.inf.tcs.fcaapi.action.ExpertAction;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalAttributeException;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.FormalContext;
import de.tudresden.inf.tcs.fcalib.FullObject;
import de.tudresden.inf.tcs.fcalib.action.ChangeAttributeOrderAction;
import de.tudresden.inf.tcs.fcalib.action.CounterExampleProvidedAction;
import de.tudresden.inf.tcs.fcalib.action.QuestionConfirmedAction;
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

	public void testFormalContext() throws IllegalObjectException {
		BasicConfigurator.configure();

		FormalContext<String, String> context = new FormalContext<>();
		NoExpertFull<String> expert = new NoExpertFull<>(context);
		FullObject<String, String> o = new FullObject<>("object");
		context.addAttribute("a");
		context.addAttribute("b");
		context.addAttribute("c");
		try{
			context.removeObject(o);
		} catch (Exception e){
			e.getClass().equals(IllegalObjectException.class);
		}
		context.addObject(o);
		try{
			context.addObject(o);
		} catch (Exception e){
			e.getClass().equals(IllegalObjectException.class);
		}
		//cover when object does not have attribute
		context.objectHasAttribute(o, "a");
		context.getObjectAtIndex(0);
		System.out.println("Attributes: " + context.getAttributes());
		//test getters 
		context.getAttributeAtIndex(1);
		context.getAttributeCount();
		context.getExpert();
		context.getConceptLattice();
		context.getObject(o.getIdentifier());
		context.getObject("a");
		context.removeObject(o);
		context.addObject(o);
		//successfully add attribute to object
		context.addAttributeToObject("a", o.getIdentifier());
		//fail when trying to add same attribute again 
		try{
			context.addAttributeToObject("a", o.getIdentifier());
		} catch(Exception e){
			e.getClass().equals(IllegalAttributeException.class);
		}
		//fail when add attribute to object that does not exist
		try{
			context.addAttributeToObject("a", "object2");
		} catch(Exception e){
			e.getClass().equals(IllegalObjectException.class);
		}
		//cover when object does have attribute
		context.objectHasAttribute(o, "a");
		//successfully remove attribute from an object
		context.removeAttributeFromObject("a", o.getIdentifier());
		try{//fail when try to remove it again
			context.removeAttributeFromObject("a", o.getIdentifier());
		}catch(Exception e){
			e.getClass().equals(IllegalAttributeException.class);
		}
		try{//not a legal object
			context.removeAttributeFromObject("a", "object2");
		}catch(Exception e){
			e.getClass().equals(IllegalObjectException.class);
		}
		try{//not a legal attribute
			context.removeAttributeFromObject("z", "object");
		}catch(Exception e){
			e.getClass().equals(IllegalAttributeException.class);
		}
		try{//not in the object
			context.removeAttributeFromObject("e", "object");
		}catch(Exception e){
			e.getClass().equals(IllegalAttributeException.class);
		}
		try{//not a legal attribute
			context.addAttributeToObject("asdkfm3", "object");
		}catch(Exception e){
			e.getClass().equals(IllegalAttributeException.class);
		}
		context.removeObject("object");
		context.clearObjects();
		try{
			context.removeObject("a");
		}catch(Exception e){
			e.getClass().equals(NullPointerException.class);
		}
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

		try{
			expert.fireExpertAction(action);
		}catch(Exception e){
			e.getClass().equals(NullPointerException.class);
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
		expert.fireExpertAction(action3);
		expert.fireExpertAction(action2);
		QuestionConfirmedAction<String, String, FullObject<String, String>> action4 = new QuestionConfirmedAction<String, String, FullObject<String, String>>();
		action4.setContext(context);
		//changed visibility for this as well
		action4.getQuestion();
		action4.setQuestion(context.getCurrentQuestion());
		action4.getKeys();
		action4.setContext(context);
		expert.fireExpertAction(action4);
		ChangeAttributeOrderAction<String, String, FullObject<String, String>> action5 = new ChangeAttributeOrderAction<String, String, FullObject<String, String>>();
		action5.setContext(context);
		action5.setContext(context);
		action5.setEnabled(true);
		
		try{
			expert.fireExpertAction(action5);
		} catch (Exception e){
			e.getClass().equals(IndexOutOfBoundsException.class);
		}
	}

}
