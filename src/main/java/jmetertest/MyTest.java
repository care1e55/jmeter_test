package jmetertest;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
//import org.apache.jmeter.*;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.extractor.gui.RegexExtractorGui;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.util.Locale;

public class MyTest {

	final static Logger logger = Logger.getLogger(Main.class);

	StandardJMeterEngine jMeterEngine;

	TestPlan testPlan;
	LoopController controller;
	ThreadGroup threadGroup;
	HTTPSamplerProxy sampler;
	BackendListener backendListener;
	ConstantTimer constantTimer;
	RegexExtractor extractor;
	ResponseListener responseListener;

	public MyTest() {
		JMeterUtils.setJMeterHome("/storage/Study/myCode/JavaWorkspace/jmeter_test/src/main/resources");
		JMeterUtils.loadJMeterProperties("jmeter.properties");
		JMeterUtils.initLogging();
		JMeterUtils.setLocale(Locale.ENGLISH);

		jMeterEngine = new StandardJMeterEngine();
		testPlan = new TestPlan("test1");
		controller = new LoopController();
		threadGroup = new ThreadGroup();
		sampler = new HTTPSamplerProxy();
		constantTimer = new ConstantTimer();
		backendListener = new BackendListener();
		extractor = new RegexExtractor();
		responseListener = new ResponseListener();

	}

	public void exportJMX(HashTree testTree) throws Exception {
		SaveService.saveTree(testTree, new FileOutputStream("example.jmx"));
	}

	public void setupGUI(TestElement element) throws ClassNotFoundException {
		element.setProperty(element.TEST_CLASS, element.getClass().getName());
		String[] arr = element.getClass().getName().split("\\.");
		String guiname = arr[arr.length-1] + "Gui";
		Class c = Class.forName(guiname);
		element.setProperty(element.GUI_CLASS, element.getClass().getName());
		logger.info(element.getPropertyAsString(element.GUI_CLASS));
	}


	public void configureExtractor() {
		extractor.setRefName("extracted");
		extractor.setRegex("<a href=\"(.*?)\">nginx.com</a>");
		extractor.setTemplate("$1$");
		extractor.setDefaultValue("DEFAULT");
		extractor.setMatchNumber(0);
	}



	public void configureThreadGroup() {
		testPlan.setUserDefinedVariables((Arguments)new ArgumentsPanel().createTestElement());
		threadGroup.setName("kek");
		threadGroup.setRampUp(0);
		threadGroup.setNumThreads(1);
		threadGroup.setSamplerController(controller);
		controller.setLoops(-1);
		controller.setContinueForever(true);
		controller.setFirst(true);
		controller.setContinueForever(true);
		controller.initialize();
	}

	public void configureSampler() {
		sampler.setName("keksampler");
		sampler.setDomain("localhost");
		sampler.setPort(8000);
		sampler.setProtocol("http");
		sampler.setMethod("GET");
		sampler.setFollowRedirects(true);
		sampler.setAutoRedirects(true);
		sampler.setUseKeepAlive(true);
		sampler.setDoMultipartPost(false);
		constantTimer.setDelay("1000");
	}


	public void configureListener() {
		Arguments bckl_args = new Arguments();
		Argument graphiteMetricsSender = new Argument();
		graphiteMetricsSender.setName("graphiteMetricsSender");
		graphiteMetricsSender.setValue("org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender");
		graphiteMetricsSender.setMetaData("=");
		Argument graphiteHost = new Argument();
		graphiteHost.setName("graphiteHost");
		graphiteHost.setValue("localhost");
		graphiteHost.setMetaData("=");
		Argument graphitePort = new Argument();
		graphitePort.setName("graphitePort");
		graphitePort.setValue("2003");
		graphitePort.setMetaData("=");
		Argument rootMetricsPrefix = new Argument();
		rootMetricsPrefix.setName("rootMetricsPrefix");
		rootMetricsPrefix.setValue("jmeter.");
		rootMetricsPrefix.setMetaData("=");
		Argument summaryOnly = new Argument();
		summaryOnly.setName("summaryOnly");
		summaryOnly.setValue("true");
		summaryOnly.setMetaData("=");
		Argument samplersList = new Argument();
		samplersList.setName("samplersList");
		samplersList.setValue("");
		samplersList.setMetaData("=");
		Argument percentiles = new Argument();
		percentiles.setName("percentiles");
		percentiles.setValue("90;95;99");
		percentiles.setMetaData("=");

		bckl_args.addArgument(graphiteMetricsSender);
		bckl_args.addArgument(graphiteHost);
		bckl_args.addArgument(graphitePort);
		bckl_args.addArgument(rootMetricsPrefix);
		bckl_args.addArgument(summaryOnly);
		bckl_args.addArgument(samplersList);
		bckl_args.addArgument(percentiles);

		backendListener.setArguments(bckl_args);
		backendListener.setClassname("org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient");

	}

	public void buildTest() {
		configureThreadGroup();
		configureSampler();
		configureExtractor();
		configureListener();

		jMeterEngine.configure(buildTree());
	}

		public HashTree buildTree() {
			HashTree tptree = new HashTree();
			HashTree tgtree = tptree.add(testPlan, threadGroup);
			HashTree stree = tgtree.add(sampler);
			tgtree.add(backendListener);
			tgtree.add(responseListener);
			tptree.add(testPlan);
			stree.add(constantTimer);
			stree.add(extractor);
			return tptree;
		}

		public void execute() {
			try {
				jMeterEngine.runTest();
			} catch (JMeterEngineException e) {
				e.printStackTrace();
			}
		}

	public void setupGUIs() {
		testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
		testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
		threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
		threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
		sampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
		sampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
		constantTimer.setProperty(ConstantTimer.TEST_CLASS, ConstantTimer.class.getName());
		constantTimer.setProperty(ConstantTimer.GUI_CLASS, ConstantTimerGui.class.getName());
		backendListener.setProperty(BackendListener.TEST_CLASS, BackendListener.class.getName());
		backendListener.setProperty(BackendListener.GUI_CLASS, BackendListenerGui.class.getName());
		extractor.setProperty(RegexExtractor.TEST_CLASS, RegexExtractor.class.getName());
		extractor.setProperty(RegexExtractor.GUI_CLASS, RegexExtractorGui.class.getName());
//		bckl_args.setProperty(Arguments.TEST_CLASS, Arguments.class.getName());
//		bckl_args.setProperty(Arguments.GUI_CLASS, ArgumentsPanel.class.getName());
	}

}




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



