/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.model.email;

import java.util.LinkedList;
import java.util.Queue;

import es.ficonlan.web.backend.entities.Email;

public class EmailFIFO {

	public static Queue<Email>	emails	= new LinkedList<Email>();

	public static void startEmailQueueThread() {
		Thread mailFIFO = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while (true) {
						//System.out.println("DENTRO EMAIL THREAD");
						Email email = emails.poll();
						if (email != null) {
							try {
								email.sendMail();
								//System.out.println("Email Enviado " + email.getAsunto());
							}
							catch (Exception e1) {
								if(e1.getCause() instanceof com.sun.mail.smtp.SMTPAddressFailedException) {
									// The recipient address <adminMail> is not a valid RFC-5321 address
									System.err.println("Could not send email. Dirección incorrecta.");
									System.err.println(email.toShortString());
								} else {
									emails.add(email);
									//System.out.println("Error al enviar el Email " + email.getAsunto());
									System.err.println("Could not send email.");
									e1.printStackTrace();
									email.onError();
									Thread.sleep(15*60*1000);
								}
							}
							Thread.sleep(10*1000);
						}
						else {
							//System.out.println("No hay Emails");
							Thread.sleep(40*1000);
						}
					}
				}
				catch (Exception e2) {
					System.out.println("Error en el sistema de Envío de Email");
					e2.printStackTrace();
					throw new RuntimeException("EmailFIFO thread error: " + e2.getMessage());
				}
			}
		};
		System.out.println("INFO: starting email thread");
		mailFIFO.start();
	}

	public static int mailQueueSize() {
		return emails.size();
	}
	
	public static void addEmailToQueue(Email email) {
		emails.add(email);
	}
}
