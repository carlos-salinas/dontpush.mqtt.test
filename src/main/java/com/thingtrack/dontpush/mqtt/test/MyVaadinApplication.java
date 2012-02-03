package com.thingtrack.dontpush.mqtt.test;

import com.ibm.mqtt.MqttBrokerUnavailableException;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttNotConnectedException;
import com.ibm.mqtt.MqttPersistenceException;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class MyVaadinApplication extends Application {

	private Window mainWindow;
	private TextField tfMessageField;
	private Button btPublisher;
	private Label lbLogHistory;
	private TextArea taLogHistory;

	private VaadinMqttClient mqttClient;

	@Override
	public void init() {

		mainWindow = buildApplication();
		setMainWindow(mainWindow);
	}

	private Window buildApplication() {

		buildMqttPub();

		Window main = new Window("MQTT Protocol Test");

		HorizontalLayout hlPubLayout = new HorizontalLayout();

		tfMessageField = new TextField();
		tfMessageField.setWidth("500px");
		tfMessageField.setWidth("300px");
		
		hlPubLayout.addComponent(tfMessageField);

		btPublisher = new Button("Publish");
		btPublisher.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {

				if (tfMessageField.getValue() != null) {

					String message = (String) tfMessageField.getValue();
					try {
						mqttClient.publish(MyMqttClient.TOPIC, message.getBytes(), 0, true);
						tfMessageField.setValue("");
					} catch (MqttNotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttPersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		hlPubLayout.addComponent(btPublisher);

		HorizontalLayout hlSubLayout = new HorizontalLayout();
		lbLogHistory = new Label("Log History");
		hlSubLayout.addComponent(lbLogHistory);

		taLogHistory = new TextArea();
		hlSubLayout.addComponent(taLogHistory);

		main.addComponent(hlPubLayout);
		main.addComponent(hlSubLayout);

		return main;
	}

	private void buildMqttPub() {

		try {
			mqttClient = new VaadinMqttClient();
		} catch (MqttException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			mqttClient.connect(MyMqttClient.CLIENT_ID, false, MyMqttClient.KEEP_ALIVE);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttBrokerUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttNotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mqttClient.subscribe(new String[]{ "com/thingtrack/bustrack/mqtt/gps"}, mqttClient.QoS);
		} catch (MqttNotConnectedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public class VaadinMqttClient extends MyMqttClient{

		public VaadinMqttClient() throws MqttException {
			super();
		}
		
		@Override
		public void onMessageArrived(String message) {
		
			if (taLogHistory.getValue() != "") {

				String log = (String) taLogHistory.getValue();
				log += message + "\n";
				taLogHistory.setValue(log);
			} else {
				taLogHistory.setValue(message);
			}
		}
		
		@Override
		public void brokersConnectionLost() {
		
			mainWindow.showNotification("Connection lost with the Message Broker", Notification.TYPE_WARNING_MESSAGE);
		}
		
		
	}

	

}
