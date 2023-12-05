package secretSanta;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class principal {
	final static String from = "x@gmail.com";
	final static String pass = "XXXXXXXXX";
	
	public static void main(String[] args) {
		
		ArrayList<Participant> participants = new ArrayList<principal.Participant>();
		ArrayList<Participant> asignedTargets = new ArrayList<principal.Participant>();
		asingPlayers(participants);

		//Lottery
		while(!sorteo(participants, asignedTargets)) {
			participants = new ArrayList<principal.Participant>();
			asignedTargets = new ArrayList<principal.Participant>();
			
			asingPlayers(participants);
		};
		
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);
            
        for (Participant participante : participants) {
        	//System.out.println("El objetivo de " + participante.getName()+ " es: " + participante.getObjetivo().getName());
        	  try {
                  // Create a default MimeMessage object.
                  MimeMessage message = new MimeMessage(session);

                  // Set From: header field of the header.
                  message.setFrom(new InternetAddress(from));

                  // Set To: header field of the header.
                  message.addRecipient(Message.RecipientType.TO, new InternetAddress(participante.getMail()));

                  // Set Subject: header field
                  message.setSubject("Amigo invisible");

                  // Now set the actual message
                  message.setText(String.format("Tienes que comprar un regalo a %s ", participante.getObjetivo().getName()));

                  System.out.println("sending...");
                  // Send message
                  Transport.send(message);
                  System.out.println("Sent message successfully....");
              } catch (MessagingException mex) {
                  mex.printStackTrace();
              }
        }
          
		
		
	}
	private static void asingPlayers(ArrayList<Participant> participants) {
		participants.add( new Participant( "Pepe", 		"x@gmail.com", 		"Ver�nica",	null));
		participants.add( new Participant( "Ver�nica", 	"x@gmail.com", 		"Pepe", 	null));
		participants.add( new Participant( "Irene", 	"x@gmail.com", 		"Alejandro",null));
		participants.add( new Participant( "Alejandro",	"x@gmail.com", 		"Irene", 	null));
		participants.add( new Participant( "Juan", 		"x@gmail.com", 		"Johana", 	null));
		participants.add( new Participant( "Johana", 	"x@gmail.com", 		"Juan", 	null));
	}
	
	private static boolean sorteo(ArrayList<Participant> participants, ArrayList<Participant> asignedTargets) {
		System.out.println("Starting the process...");
		for (Participant participant : participants) {
			boolean asigned= false;
			int loop = 0;
			while(!asigned) {

				Participant objetive = participants.get((int)(Math.random()*6));
				participant.setObjetivo( objetive );
				// not himself, objetive is not asigned and is not his forbidden
				if(!participant.equals(objetive) && !asignedTargets.contains(objetive) && !participant.getInvalid().equals(objetive.getName()) ) {
					asignedTargets.add(objetive);
					asigned = true;
				}
				//System.out.println(loop + " trying: "+ participante.getName() + " > " + objetive.getName() + " " + asigned);
				loop++;
				if(loop > 100){
					return false;
				}

			}
		}
		System.out.println("Process ending...");
		return true;
	}
	
	static class Participant {
		private String name;
		private String mail;
		private String invalid;
		private Participant target;
		public Participant(String name, String mail, String invalid, Participant target) {
			super();
			this.name = name;
			this.mail = mail;
			this.invalid = invalid;
			this.target = target;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getInvalid() {
			return invalid;
		}
		public void setInvalid(String invalid) {
			this.invalid = invalid;
		}
		public Participant getObjetivo() {
			return target;
		}
		public void setObjetivo(Participant target) {
			this.target = target;
		}
		
	}
}
