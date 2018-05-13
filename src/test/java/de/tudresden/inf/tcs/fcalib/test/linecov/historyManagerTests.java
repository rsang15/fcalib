package de.tudresden.inf.tcs.fcalib.test.linecov;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import de.tudresden.inf.tcs.fcaapi.Context;
import de.tudresden.inf.tcs.fcaapi.FCAImplication;
import de.tudresden.inf.tcs.fcaapi.FCAObject;
import de.tudresden.inf.tcs.fcaapi.change.ContextChange;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.FormalContext;
import de.tudresden.inf.tcs.fcalib.FullObject;
import de.tudresden.inf.tcs.fcalib.Implication;
import de.tudresden.inf.tcs.fcalib.PartialContext;
import de.tudresden.inf.tcs.fcalib.PartialObject;
import de.tudresden.inf.tcs.fcalib.change.NewImplicationChange;
import de.tudresden.inf.tcs.fcalib.change.NewObjectChange;
import de.tudresden.inf.tcs.fcalib.change.ObjectHasAttributeChange;
import de.tudresden.inf.tcs.fcalib.test.NoExpertFull;
import de.tudresden.inf.tcs.fcalib.change.HistoryManager;
import java.util.EventListener;

public class historyManagerTests<B> implements EventListener{
	
	
	@Test
	public void begin() throws IllegalObjectException{
		/**
		 * Begin functional testing and input space Partitioning
		 */
		Set<String> set1 = new HashSet<>();
		set1.add("A");
		set1.add("B");
		set1.add("");
		Set<String> set2 = new HashSet<>();
		set2.add("A");
		set2.add("B");
		FCAImplication<String> implication = new Implication<>();
		Implication<String> imp = new Implication<String>(set1,set2);
		
		Context<String,?,FCAObject<String,?>> context = null;
		
		ContextChange<String> cchange = null;
		HistoryManager<String, ContextChange<String>> histmanager;
		
		BasicConfigurator.configure();
		
		FormalContext<String, String> context3 = new FormalContext<>();
		PartialObject<String, String> partialo = new PartialObject<>("object");
		//FormalContext<String, String, FCAObject<String, String>> context4 = new FormalContext<>();
		NoExpertFull<String> expert = new NoExpertFull<>(context3);
		FullObject<String, String> o = new FullObject<>("object");
		/**
		 * Begin line coverage Additions
		 */
		context3.setExpert(expert);
		assertTrue(context3.addAttribute("s"));
		assertTrue(context3.addObject(o));
		context3.setCurrentQuestion(imp);
		
		NewImplicationChange<String> impchange = new NewImplicationChange<>(context3, imp);
		context3.initializeExploration();
		impchange.undo();
	
		histmanager = new HistoryManager<String, ContextChange<String>>();
		
		NewObjectChange<String> objchange = new NewObjectChange<String>(context, o);
		ObjectHasAttributeChange<String> attchange = new ObjectHasAttributeChange<>(o, "a");
		assertTrue(histmanager.add(attchange));
		assertTrue(context3.addAttributeToObject("s", "object"));
		
		/**
		 * BUG 2: Does not actually undo, since we can keep undoing
		 * successfully via histmanager
		 */
		
		attchange.undo();
		histmanager.undo(attchange);
		histmanager.undoAll();
		//test getters
		/**
		 * Note: Add Assert Values for to get mutation coverage
		 * BEGIN MUTATION COVERAGE
		 */
		impchange.getImplication();
		impchange.getType();
		assertTrue(impchange.getType() == ContextChange.NEW_IMPLICATION_MODIFICATION);
		assertTrue(objchange.getObject() == o);
		assertTrue(objchange.getType() == ContextChange.NEW_OBJECT_MODIFICATION);
		assertTrue(attchange.getAttribute() != null);
		objchange.getType();
		assertTrue(attchange.getObject() == o);
		assertTrue(attchange.getType() == ContextChange.OBJECT_HAS_ATTRIBUTE_MODIFICATION);
		attchange.getType();
		attchange.getAttribute();
		
		impchange.getImplication();
		impchange.getType();
		
		assertTrue(impchange.getType() == ContextChange.NEW_IMPLICATION_MODIFICATION);
		assertFalse(impchange.getType() == ContextChange.AUTOMATICALLY_ACCEPTED_IMPLICATION);
		assertFalse(impchange.getImplication().equals(implication));
		impchange.getType();
		
		histmanager.push(cchange);
	}
}
