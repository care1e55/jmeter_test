package jmetertest;

import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;

public class MyNonSampler {

	TestPlan testPlan;
	LoopController controller;
	ThreadGroup threadGroup;
	BackendListener backendListener;
	ResponseListener responseListener;


	public MyNonSampler() {
		configureTestPlan();
		configureThreadGroup();
		configureListener();
//		responseListener = new ResponseListener();
	}


	public TestPlan configureTestPlan() {
		testPlan = new TestPlan("test1");
		testPlan.setUserDefinedVariables((Arguments)new ArgumentsPanel().createTestElement());
		testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
		testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
		return testPlan;
	}


	public ThreadGroup configureThreadGroup() {
		threadGroup = new ThreadGroup();
		controller = new LoopController();
		controller.setLoops(-1);
		controller.setContinueForever(true);
		controller.setFirst(true);
		controller.initialize();
		threadGroup.setName("kek");
		threadGroup.setRampUp(100);
		threadGroup.setNumThreads(100);
		threadGroup.setSamplerController(controller);
		threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
		threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
		return threadGroup;
	}

	public BackendListener configureListener() {
		backendListener = new BackendListener();
		Arguments bckl_args = new Arguments();
		Argument graphiteMetricsSender = new Argument();
		Argument graphiteHost = new Argument();
		Argument graphitePort = new Argument();
		Argument rootMetricsPrefix = new Argument();
		Argument summaryOnly = new Argument();
		Argument samplersList = new Argument();
		Argument percentiles = new Argument();
		graphiteMetricsSender.setName("graphiteMetricsSender");
		graphiteMetricsSender.setValue("org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender");
		graphiteMetricsSender.setMetaData("=");
		graphiteHost.setName("graphiteHost");
		graphiteHost.setValue("localhost");
		graphiteHost.setMetaData("=");
		graphitePort.setName("graphitePort");
		graphitePort.setValue("2003");
		graphitePort.setMetaData("=");
		rootMetricsPrefix.setName("rootMetricsPrefix");
		rootMetricsPrefix.setValue("jmeter.");
		rootMetricsPrefix.setMetaData("=");
		summaryOnly.setName("summaryOnly");
		summaryOnly.setValue("true");
		summaryOnly.setMetaData("=");
		samplersList.setName("samplersList");
		samplersList.setValue("");
		samplersList.setMetaData("=");
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
		backendListener.setProperty(BackendListener.TEST_CLASS, BackendListener.class.getName());
		backendListener.setProperty(BackendListener.GUI_CLASS, BackendListenerGui.class.getName());

		return backendListener;
	}

}
