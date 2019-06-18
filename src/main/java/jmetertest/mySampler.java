package jmetertest;

import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.extractor.gui.RegexExtractorGui;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;
import org.apache.jorphan.collections.HashTree;

public class mySampler {

	private static void configureDefaults(HTTPSamplerProxy mysampler) {
		mysampler.setName("default");
		mysampler.setDomain("localhost");
		mysampler.setPort(8000);
		mysampler.setProtocol("http");
		mysampler.setMethod("GET");
		mysampler.setFollowRedirects(true);
		mysampler.setAutoRedirects(true);
		mysampler.setUseKeepAlive(true);
		mysampler.setDoMultipartPost(false);
		mysampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
		mysampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

	}

	public static HashTree createSampler1(String name) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		ConstantTimer constantTimer = new ConstantTimer();
		RegexExtractor extractor = new RegexExtractor();
		HashTree myTree = new HashTree();
		configureDefaults(mysampler);
		mysampler.setName(name);

		extractor.setRefName("extracted");
		extractor.setRegex("<a href=\"(.*?)\">nginx.com</a>");
		extractor.setTemplate("$1$");
		extractor.setDefaultValue("DEFAULT");
		extractor.setMatchNumber(0);
		constantTimer.setDelay("1000");
		constantTimer.setProperty(ConstantTimer.TEST_CLASS, ConstantTimer.class.getName());
		constantTimer.setProperty(ConstantTimer.GUI_CLASS, ConstantTimerGui.class.getName());
		extractor.setProperty(RegexExtractor.TEST_CLASS, RegexExtractor.class.getName());
		extractor.setProperty(RegexExtractor.GUI_CLASS, RegexExtractorGui.class.getName());

		myTree.add(mysampler);
		myTree.add(mysampler, constantTimer);
		myTree.add(mysampler, extractor);

		return myTree;
	}

	public static HashTree createSamplerPost(String name, String node1, String node2) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		HashTree myTree = new HashTree();
		configureDefaults(mysampler);
		mysampler.setName(name);
		mysampler.setMethod("POST");

		HTTPArgument body = new HTTPArgument();
		String sBody = 	"<lol>myBody: " + node1
						+ " " + node2 +
						"</lol>";
		body.setValue(sBody);
		myTree.add(mysampler);
		return myTree;
	}

	public static HashTree createSampler3(String name) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		HashTree myTree = new HashTree();
		configureDefaults(mysampler);
		mysampler.setName(name);
		myTree.add(mysampler);
		return myTree;
	}


}
