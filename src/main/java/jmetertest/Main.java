package jmetertest;

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
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.ResultSaver;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;


public class Main {

	final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		logger.info("Hello, world!");
		JMeterUtils.setJMeterHome("/storage/Study/myCode/JavaWorkspace/jmeter_test/src/main/resources");
		JMeterUtils.loadJMeterProperties("jmeter.properties");
		JMeterUtils.initLogging();
		JMeterUtils.setLocale(Locale.ENGLISH);

		TestPlan testPlan = new TestPlan("test1");
		testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
		testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
		testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());


		LoopController controller = new LoopController();
		controller.setLoops(-1);
		controller.setContinueForever(true);
		controller.setFirst(true);
		controller.setContinueForever(true);
		controller.initialize();

		ThreadGroup tg = new ThreadGroup();
		tg.setName("kek");
		tg.setRampUp(0);
		tg.setNumThreads(1);
		tg.setSamplerController(controller);
		tg.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
		tg.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

		HTTPSamplerProxy sampler = new HTTPSamplerProxy();
		sampler.setName("keksampler");
		sampler.setDomain("localhost");
		sampler.setPort(8000);
		sampler.setProtocol("http");
		sampler.setMethod("GET");
		sampler.setFollowRedirects(true);
		sampler.setAutoRedirects(true);
		sampler.setUseKeepAlive(true);
		sampler.setDoMultipartPost(false);
		sampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
		sampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

		ConstantTimer ct = new ConstantTimer();
		ct.setDelay("1000");
		ct.setProperty(ConstantTimer.TEST_CLASS, ConstantTimer.class.getName());
		ct.setProperty(ConstantTimer.GUI_CLASS, ConstantTimerGui.class.getName());

		BackendListener bckl = new BackendListener();
		bckl.setProperty(BackendListener.TEST_CLASS, BackendListener.class.getName());
		bckl.setProperty(BackendListener.GUI_CLASS, BackendListenerGui.class.getName());
		bckl.setClassname("org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient");

		Arguments bckl_args = new Arguments();
		bckl_args.setProperty(Arguments.TEST_CLASS, Arguments.class.getName());
		bckl_args.setProperty(Arguments.GUI_CLASS, ArgumentsPanel.class.getName());

		Argument graphiteMetricsSender = new Argument();
		graphiteMetricsSender.setName("graphiteMetricsSender");
		graphiteMetricsSender.setValue("org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender");
		graphiteMetricsSender.setMetaData("=");


//		<elementProp name="graphiteMetricsSender" elementType="Argument">
//		<stringProp name="Argument.name">graphiteMetricsSender</stringProp>
//		<stringProp name="Argument.value">org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender</stringProp>


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

		bckl.setArguments(bckl_args);
//		Summariser sum = new Summariser();
//		SampleSaveConfiguration sc = new SampleSaveConfiguration(true);

//		ResultCollector rc = new ResultCollector(sum);
//		rc.setFilename("report.jtl");
//		rc.setSuccessOnlyLogging(true);
//		rc.setSaveConfig(sc);

		RegexExtractor extractor = new RegexExtractor();
		extractor.setProperty(RegexExtractor.TEST_CLASS, RegexExtractor.class.getName());
		extractor.setProperty(RegexExtractor.GUI_CLASS, RegexExtractorGui.class.getName());

		extractor.setRefName("extracted");
		extractor.setRegex("<a href=\"(.*?)\">nginx.com</a>");
		extractor.setTemplate("$1$");
		extractor.setDefaultValue("DEFAULT");
		extractor.setMatchNumber(0);

		Summariser summer = null;
		String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
		if (summariserName.length() > 0) {
			summer = new Summariser(summariserName);
		}

		SampleSaveConfiguration ssc = new SampleSaveConfiguration();
		ssc.setResponseData(true);
		ssc.setResponseHeaders(true);

		ResultCollector logger = new ResultCollector(summer);
//		ResultCollector logger = new ResultCollector();
		logger.setFilename("logFile.jtl");
		logger.setSaveConfig(ssc);

		ResultSaver rs = new ResultSaver();
		ResponseListener respl = new ResponseListener();

		HashTree tptree = new HashTree();

		tptree.add(testPlan);
		HashTree tgtree = tptree.add(testPlan, tg);
		tgtree.add(bckl);
		tgtree.add(respl);
//		tgtree.add(logger);
		HashTree stree = tgtree.add(sampler);
		stree.add(ct);
		stree.add(extractor);

//		logger.info("");
//
//		HashTree stree = tgtree.add(threadGroup, sampler);
//		stree.add(constantTimer);


//		testTree.add(tgtree, sampler);

		StandardJMeterEngine jMeterEngine = new StandardJMeterEngine();
		jMeterEngine.configure(tptree);

		SaveService.saveTree(tptree, new FileOutputStream("example.jmx"));

		try {
			jMeterEngine.runTest();
		} catch (JMeterEngineException e) {
			e.printStackTrace();
		}

	}

}
