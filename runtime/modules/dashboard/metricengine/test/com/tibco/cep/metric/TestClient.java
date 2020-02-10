package com.tibco.cep.metric;

import java.util.Collection;

import com.tibco.cep.metric.api.Notification;
import com.tibco.cep.metric.api.NotificationType;
import com.tibco.cep.metric.api.PubSubManager;
import com.tibco.cep.metric.api.Subscription;
import com.tibco.cep.metric.runtime.PubSubManagerImpl;
import com.tibco.cep.runtime.model.element.Concept;

/*
 * Author: Anil Jeswani / Date: Mar 29, 2010 / Time: 3:06:05 PM
 */
public class TestClient {

	public static void main(String[] args) {
		Notification d = new Notification() {
			public void notify(Concept metric,
					Collection<String> subscriptionNames,
					NotificationType notificationType) {
				System.out.println("do something");
			}
		};

		Subscription s = new Subscription("Client", "OrderByCustomer",
				"customerid between 1 and 2");
		PubSubManager p = new PubSubManagerImpl(null, null);
		try {
			p.initialize("TestClient", d);
			p.subscribe(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.unSubscribe(s.getName());
	}
}
