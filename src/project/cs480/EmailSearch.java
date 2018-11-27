package cs480;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

/**
 * The purpose of this class is to use JavaMail to search our group email for
 * clubs. Then it will scan through the email and get relevant information from
 * the email and store it.
 * 
 * Look for the format. Accept the entire line following as a string. Date: xxxx
 * Time: xxxxx Location: xxxx
 * 
 * @author
 *
 */
public class EmailSearch {

	/**
	 * 
	 * @param pop3Host  creates a temporary inbox. use: pop.gmail.com
	 * @param storeType use: pop3
	 * @param user      our group email
	 * @param password  our group email password
	 * @param file      the file we print the information we get to.
	 */
	public static void fetch(String pop3Host, String storeType, String user, String password, File file)
			throws IOException {

		// needed to append the info we find.
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter buffer = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(buffer);

		try {
			// create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", pop3Host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(pop3Host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// retrieve the messages from the folder in an array and write to file
			Message[] messages = emailFolder.getMessages();

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				// keep a space between lines
				printWriter.println();
				writePart(message, file);
				String line = reader.readLine();
				if ("YES".equals(line)) {
					message.writeTo(System.out);
				} else if ("QUIT".equals(line)) {
					break;
				}
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();
			printWriter.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writePart(Part p, File file) throws Exception {
		if (p instanceof Message) {
			// Call method writeEnvelope
			writeEnvelope((Message) p, file);
		}

		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter buffer = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(buffer);
	}

	public static void writeEnvelope(Message m, File file) throws Exception {
		Address[] a;
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter buffer = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(buffer);

		// Get who the email is from
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++)
				printWriter.println("FROM: " + a[j].toString());
		}

		// Get what the email is about.
		if (m.getSubject() != null) {
			printWriter.println("SUBJECT: " + m.getSubject());
		}
	}

	/**
	 * This method looks for a keyword in the subject of an email.
	 * 
	 * @param port
	 * @param keyword
	 */
	public void checkEmail(String port, final String keyword) {
		Properties properties = new Properties();

		// Relevant Gmail Information:
		String host = "imap.gmail.com";
		String userName = "ProlificProjectPurveyors";
		String password = "groupemail";

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port", String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);

			// creates a search criterion
			SearchTerm searchCondition = new SearchTerm() {
				@Override
				public boolean match(Message message) {
					try {
						if (message.getSubject().contains(keyword)) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
					return false;
				}
			};

			// performs search through the folder
			Message[] foundMessages = folderInbox.search(searchCondition);

			for (int i = 0; i < foundMessages.length; i++) {
				Message message = foundMessages[i];
				String subject = message.getSubject();
				System.out.println("Found message #" + i + ": " + subject);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
	}

}
