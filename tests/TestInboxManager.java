import Entities.*;
import Exceptions.DoesNotExistException;
import UseCases.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TestInboxManager {

    InboxManager im = new InboxManager();
    Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Ahtaenne Di");
    Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
    User u1 = new User("user1@gmail.com", "User1", "Yew Zer");
    UserManager um = new UserManager();
    Message messageToBeAdded = new Message("Attendee1", "Attendee2", "Birthday Wishes", "HBD, loser.");


    //@Test
    //public void testAddMessage() {
    //    im.addMessage(messageToBeAdded, "Attendee2");
    //}

    @Test
    public void testSendMessage() throws DoesNotExistException {
        im.sendMessage("Attendee1", "Attendee2" , messageToBeAdded, um);
        // assertTrue();
    }

    @Test
    public void testSendMessage(String senderID, String recipientID, String Content, String Subject, UserManager um) {};

    @Test
    public void deleteMessage(User user, Message messageToBeDeleted) {};



}