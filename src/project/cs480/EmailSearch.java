package cs480;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

/**
 * The purpose of this class is to use JavaMail to search our
 * group email for clubs. Then it will scan through the email and 
 * get relevant information from the email and store it. 
 * Relevant Gmail Information:
   	String host = "imap.gmail.com";
    String username = "ProlificProjectPurveyors";
    String password = "groupemail";  
 * 
 * @author
 *
 */
public class EmailSearch {
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param keyword
	 */
	public void searchEmail(String port, final String keyword) {
        Properties properties = new Properties();
        
       // Relevant Gmail Information:
        String host = "imap.gmail.com";
        String userName = "ProlificProjectPurveyors";
        String password = "groupemail";
        
        
        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
 
        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));
 
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
