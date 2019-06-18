package jmetertest;

import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;

public class Main {

	final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

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

}
