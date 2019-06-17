package jmetertest;

import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerFactory;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

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
	}

	public static HTTPSamplerProxy createSampler1(String name) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		configureDefaults(mysampler);
		mysampler.setName(name);
		return mysampler;
	}

	public static HTTPSamplerProxy
	createSamplerPost(String name, String node1, String node2) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		configureDefaults(mysampler);
		mysampler.setName(name);
		mysampler.setMethod("POST");

		HTTPArgument body = new HTTPArgument();
		String sBody = 	"<lol>myBody: " + node1
						+ " " + node2 +
						"</lol>";
		body.setValue(sBody);
		return mysampler;
	}

	public static HTTPSamplerProxy createSampler3(String name) {
		HTTPSamplerProxy mysampler = new HTTPSamplerProxy();
		configureDefaults(mysampler);
		mysampler.setName(name);
		return mysampler;
	}

}
