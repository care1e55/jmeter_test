package jmetertest;

import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.log4j.Logger;

public class ResponseListener
		extends AbstractListenerElement
		implements SampleListener {

	final static Logger logger = Logger.getLogger(ResponseListener.class);

	JMeterContext context = null;
	JMeterVariables vars = null;

//	ResponseListener() {
//
//	}

	@Override
	public void sampleOccurred(SampleEvent sampleEvent) {
		logger.info("Sample Occurred");
		SampleResult result = sampleEvent.getResult();
		String responsedata = new String(result.getResponseData());
		logger.info(responsedata);
		context = getThreadContext();
		vars = context.getVariables();
		logger.info(vars.get("extracted"));
	}

	@Override
	public void sampleStarted(SampleEvent sampleEvent) {
	}

	@Override
	public void sampleStopped(SampleEvent sampleEvent) {
	}

}
