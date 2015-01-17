package es.ficonlan.web.backend.model.email;

import java.util.LinkedList;
import java.util.Queue;

public class EmailFIFO {

	public static Queue<Email>	emails	= new LinkedList<Email>();

	public static Thread emailThread() {
		Thread mailFIFO = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while (true) {
						Email email = emails.poll();
						try { email.sendMail(); }
						catch (Exception e1) {
							emails.add(email);
							Thread.sleep(300000);
						}
					}
				}
				catch (Exception e2) {
					throw new RuntimeException("EmailFIFO thread error: " + e2.getMessage());
				}
			}
		};
		return mailFIFO;
	}

}
