package com.thingtrack.dontpush.mqtt.test;

import com.ibm.mqtt.MqttAdvancedCallback;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttSimpleCallback;

public class MyMqttClient extends MqttClient {

	public static final String CLIENT_ID = "MyMqttClient";
	
	public static final String HOST = "tcp://localhost:1883";
	public static final String TOPIC = "com/thingtrack/mqtt/test";
	public static final Short KEEP_ALIVE = new Short("30"); 
	public String[] TOPICS = { TOPIC };
	public int[] QoS = { 1 };
	
	private MYMqttCallback clientHandler;


	
	public MyMqttClient() throws MqttException {
	
		super(HOST);
		clientHandler = new MYMqttCallback();
		this.registerSimpleHandler(clientHandler);
	}
	
	public void brokersConnectionLost(){
		
		//TODO: Do nothing. It should be overriden by the derived class.
	}
	
	public void onMessageArrived(String message){
		
		//TODO: Do nothing. It should be overriden by the derived class.
	}
	
	
	public class MYMqttCallback implements MqttSimpleCallback{

		public void connectionLost() throws Exception {
			
			brokersConnectionLost();
		}

		public void publishArrived(String thisTopicName, byte[] thisPayload,
				int QoS, boolean retained) throws Exception {

			String messageBody = new String(thisPayload);
			onMessageArrived(messageBody);

		}

	}
}
