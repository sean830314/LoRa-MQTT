package mqtt.Subscriber.pack;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class SimpleMqttClientSubscriber implements MqttCallback {

  MqttClient myClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://140.120.14.56:1883";
	static final String M2MIO_DOMAIN = "<Mosquito>";
	static final String M2MIO_STUFF = "data";
	static final String M2MIO_THING = "<device_A>";
	static final String M2MIO_USERNAME = "ekko771";
	static final String M2MIO_PASSWORD_MD5 = "ekko771";

	// the following two flags control whether this example is a publisher, a subscriber or both
	static final Boolean subscriber = true;
	static final Boolean publisher = true;

	/**
	 * 
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
		// code to reconnect to the broker would go here if desired
	}

	/**
	 * 
	 * deliveryComplete
	 * This callback is invoked when a message published by this client
	 * is successfully received by the broker.
	 * 
	 */
	/*
	@Override
	public void deliveryComplete(MqttDeliveryToken token) {
		//System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
	}
	*/
	/**
	 * 
	 * messageArrived
	 * This callback is invoked when a message is received on a subscribed topic.
	 * 
	 */
	/*
	@Override
	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic.getName());
		System.out.println("| Message: " + new String(message.getPayload()));
		System.out.println("-------------------------------------------------");
	}
	 */
	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {
		SimpleMqttClientSubscriber smc = new SimpleMqttClientSubscriber();
		smc.runClient();
	}
	
	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient() {
		// setup MQTT Client
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		connOpt.setUserName(M2MIO_USERNAME);
		connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Connected to " + BROKER_URL);

		// setup topic
		// topics on m2m.io are in the form <domain>/<stuff>/<thing>
		String myTopic = M2MIO_DOMAIN + "/" + M2MIO_STUFF + "/" + M2MIO_THING;
		myTopic="ttt";
		System.out.println("Topic : "+myTopic);
		MqttTopic topic = myClient.getTopic(myTopic);
		// subscribe to topic if subscriber
		while(true) {
			if(!myClient.isConnected()) {	//ensure isConnected
				System.out.println("DisConnect");
				try {
					myClient.connect(connOpt);
					System.out.println("Connected to " + BROKER_URL);
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (subscriber) {
				try {
					int subQoS = 0;
					myClient.subscribe(myTopic, subQoS);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			/*
			// disconnect
			try {
				// wait to ensure subscribed messages are delivered
				if (subscriber) {
					Thread.sleep(5000);
				}
				myClient.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		//myClient.disconnect();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Pub complete" + new String(arg0.getMessage().getPayload()));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-------------------------------------------------");
		System.out.println("| Subscriber:");
		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " + new String(message.getPayload(), "utf-8" ));
		System.out.println("-------------------------------------------------");
	}
}