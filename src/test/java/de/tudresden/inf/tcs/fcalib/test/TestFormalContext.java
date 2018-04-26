/**
 * Test implementation for testing attribute exploration in partial contexts.
 * @author Baris Sertkaya
 */
package de.tudresden.inf.tcs.fcalib.test;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
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

	public void testFormalContext() {
		BasicConfigurator.configure();

		FormalContext<String, String> context = new FormalContext<>();
		NoExpertFull<String> expert = new NoExpertFull<>(context);
		
		context.addAttribute("a");
		context.addAttribute("b");
		context.addAttribute("c");
		// context.addAttribute("d");
		// context.addAttribute("e");
		// context.addAttribute("f");
		// context.addAttribute("g");
		System.out.println("Attributes: " + context.getAttributes());
		//test getters 
		context.getAttributeAtIndex(1);
		context.getAttributeCount();
		context.getExpert();
		context.getConceptLattice();
		context.getExtents();
		context.getDuquenneGuiguesBase();
		context.getImplications();
		context.getObjects();
		context.getStemBase();
	
		context.getAttributes();
		context.getCurrentQuestion();
		context.getConcepts();
		context.getObjectCount();
		context.getExtents();
		
		expert.addExpertActionListener(context);
		context.setExpert(expert);
		
		StartExplorationAction<String, String, FullObject<String, String>> action = new StartExplorationAction<>();
		action.setContext(context);

		//context.addObjects((new Set<FullObject<String, String>>() set) );
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
		action4.getKeys();
		ChangeAttributeOrderAction<String, String, FullObject<String, String>> action5 = new ChangeAttributeOrderAction<String, String, FullObject<String, String>>();
		action5.setContext(context);
	}

}
