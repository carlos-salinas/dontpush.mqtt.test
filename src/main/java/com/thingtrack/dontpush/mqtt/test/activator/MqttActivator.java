package com.thingtrack.dontpush.mqtt.test.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.thingtrack.dontpush.mqtt.test.MyMqttClient;

public class MqttActivator implements BundleActivator {

	private MyMqttClient mqttClient;
	public void start(BundleContext context) throws Exception {
		
//		mqttClient = new MyMqttClient();
//		
//		String messageBody = "Message from activator";
//		mqttClient.publish(MyMqttClient.TOPIC, messageBody.getBytes(), 1, true);
//		mqttClient.subscribe(mqttClient.TOPICS, mqttClient.QoS);
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
