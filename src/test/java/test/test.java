package test;

import jmetertest.MyTest;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.util.keystore.JmeterKeyStore;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class test {

	final static Logger logger = Logger.getLogger(test.class);

	@Before
	public void initTest() throws Exception {
	}

	@After
	public void closeTest() {

	}

	@Test
	public void debugrun() throws Exception {
		MyTest myTest = new MyTest();
	}

	@Test
	public void treeValidation() throws Exception {
		MyTest myTest = new MyTest();
		StandardJMeterEngine jMeterEngine = new StandardJMeterEngine();
		HashTree treeeee = myTest.buildTree();
		jMeterEngine.configure(treeeee);
		myTest.exportJMX();

		try {
			jMeterEngine.runTest();
		} catch (JMeterEngineException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gui() throws Exception {
		MyTest myTest = new MyTest();
	}

	@Test
	public void testStartGame() throws Exception {
	}

	@Test
	public void testStartReplay() throws Exception {
	}

	@Test
	public void test_3_Replays() throws Exception {
	}

	@Test
	public void testMovingFiles() throws Exception {
	}

	@Test
	public void testRemovingFiles() throws Exception {
	}

	@Test
	public void testMovingAndRemovingFiles() throws Exception {
	}

	@Test
	public void testMovingAndRemovingRandomFiles() throws Exception {
	}

	@Test
	public void testReplay() throws Exception {
	}



}
