package jmetertest;

import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.util.Locale;

public class MyTest {

	final static Logger logger = Logger.getLogger(Main.class);

	StandardJMeterEngine jMeterEngine;
	HashTree testTree;

	public MyTest() {
		JMeterUtils.setJMeterHome("/storage/Study/myCode/JavaWorkspace/jmeter_test/src/main/resources");
		JMeterUtils.loadJMeterProperties("jmeter.properties");
		JMeterUtils.initLogging();
		JMeterUtils.setLocale(Locale.ENGLISH);

		jMeterEngine = new StandardJMeterEngine();

	}

	public void exportJMX() throws Exception {
		SaveService.saveTree(testTree, new FileOutputStream("example.jmx"));
	}

	public void buildTest() {
		jMeterEngine.configure(buildTree());
	}

	public HashTree buildTree() {
		MyNonSampler nontest= new MyNonSampler();
		testTree = new HashTree(nontest.testPlan);
		HashTree tgtree = testTree.add(nontest.testPlan, nontest.threadGroup);
		tgtree.add(mySampler.createSampler1("kek1"));
		tgtree.add(mySampler.createSampler1("kek2"));
		tgtree.add(mySampler.createSampler1("kek2"));
		tgtree.add(nontest.backendListener);

		return testTree;
	}

	public void execute() {
		try {
			jMeterEngine.runTest();
		} catch (JMeterEngineException e) {
			e.printStackTrace();
		}
	}

}



//	public void setupGUI(TestElement element) throws ClassNotFoundException {
//		element.setProperty(element.TEST_CLASS, element.getClass().getName());
//		String[] arr = element.getClass().getName().split("\\.");
//		String guiname = arr[arr.length-1] + "Gui";
//		Class c = Class.forName(guiname);
//		element.setProperty(element.GUI_CLASS, element.getClass().getName());
//		logger.info(element.getPropertyAsString(element.GUI_CLASS));
//	}
//	Summariser summer = null;
//	String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
//		if(summariserName.length()>0)
//	{
//		summer = new Summariser(summariserName);
//	}
//
//	SampleSaveConfiguration ssc = new SampleSaveConfiguration();
//		ssc.setResponseData(true);
//		ssc.setResponseHeaders(true);
//
//	ResultCollector logger = new ResultCollector(summer);
//		logger.setFilename("logFile.jtl");
//		logger.setSaveConfig(ssc);
//
//	ResultSaver rs = new ResultSaver();


//		setupGUI(testPlan);
//		setupGUI(controller);
//		setupGUI(threadGroup);
//		setupGUI(sampler);
//		setupGUI(backendListener);
//		setupGUI(constantTimer);
//		setupGUI(extractor);
//		setupGUI(responseListener);
//		logger.info(guicommand);
//		logger.info("element.setProperty(element.GUI_CLASS, " + arr[arr.length-1] + "Gui.class.getName()");
//		element.setProperty(element.GUI_CLASS, element.getClass().getName());
//		logger.info(element.TEST_CLASS + " " + TestPlan.class.getName());
//		logger.info(BackendListener.GUI_CLASS + " " + BackendListenerGui.class.getName());
//		logger.info(element.GUI_CLASS + " " + TestPlanGui.class.getName());



