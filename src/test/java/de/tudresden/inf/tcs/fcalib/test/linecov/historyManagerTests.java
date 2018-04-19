package de.tudresden.inf.tcs.fcalib.test.linecov;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import de.tudresden.inf.tcs.fcaapi.Context;
import de.tudresden.inf.tcs.fcaapi.FCAImplication;
import de.tudresden.inf.tcs.fcaapi.FCAObject;
import de.tudresden.inf.tcs.fcaapi.change.ContextChange;
import de.tudresden.inf.tcs.fcaapi.exception.IllegalObjectException;
import de.tudresden.inf.tcs.fcalib.Implication;
import de.tudresden.inf.tcs.fcalib.change.NewImplicationChange;
import de.tudresden.inf.tcs.fcalib.change.NewObjectChange;
import de.tudresden.inf.tcs.fcalib.change.ObjectHasAttributeChange;
import de.tudresden.inf.tcs.fcaapi.change.ContextChange;
import de.tudresden.inf.tcs.fcalib.change.HistoryManager;

public class historyManagerTests<B> {
	
	public FCAImplication<Integer> implication = new Implication<Integer>();
	public FCAObject<Integer, Integer> object;
	public Context<Integer,Integer,FCAObject<Integer,Integer>> context;
	public Context<Integer,?,FCAObject<Integer,?>> context2;
	public NewImplicationChange<Integer> histman1 = new NewImplicationChange<Integer>(context, implication);
	public NewObjectChange<Integer> objchange = new NewObjectChange<Integer>(context2, object);
	public ObjectHasAttributeChange<Integer> attchange = new ObjectHasAttributeChange<Integer>(object, 0);
	public ContextChange<Integer> cchange;
	public HistoryManager<Integer, ContextChange<Integer>> histmanager;
	@Before
	public void begin(){
		
	}
	
	@Test
	public void testnewimplicationchange1() {
		//histman1.undo();
		//NewImplicationChange<int 12,> histman1 = new NewImplicationChange<int 12,>(context, implication);
		assertTrue(histman1.getType() == ContextChange.NEW_IMPLICATION_MODIFICATION);
		assertFalse(histman1.getType() == ContextChange.AUTOMATICALLY_ACCEPTED_IMPLICATION);
		histman1.getType();
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void testnewimplicationchange2() {
		histman1.undo();
		histman1.getImplication();
		histman1.getType();
		assertFalse(histman1.getImplication().equals(implication));
	}
	@Test
	public void objectchangedtest(){
		//objchange.getObject();
		//objchange.getType();
		
		//objchange.undo();
	}
	@Test
	public void attchangetests(){
		attchange.getAttribute();
		attchange.getType();
		attchange.getObject();
	}
	@Test(expected = java.lang.NullPointerException.class)
	public void histmantests(){
		histmanager.push(cchange);
		histmanager.undo(cchange);
		
	}
}
