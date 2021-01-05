import Controllers.Messenger;
import Controllers.UseCaseStorage;
import Entities.*;
import Exceptions.*;
import UseCases.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TestMessenger {

    InboxManager im = new InboxManager();
    TalkManager tm = new TalkManager();
    RoomManager rm = new RoomManager();
    UserManager um = new UserManager();
    AttributeChanger ac = new AttributeChanger();
    UseCaseStorage ucs = new UseCaseStorage(tm, rm, im , um, ac);
    Messenger mg = new Messenger(ucs);

    @Before
    public void setup() {
        im = new InboxManager();
        tm = new TalkManager();
        rm = new RoomManager();
        um = new UserManager();
        ac = new AttributeChanger();
        ucs = new UseCaseStorage(tm, rm, im , um, ac);
        mg = new Messenger(ucs);
    }

    @Test
    public void testSendPrivateMessage() throws DoesNotExistException {
        String content = "Hello. Sarah divorced Caleb and is depressed out of her mind so she will not be attending.";
        String senderID = "Attendee1";
        String recipientID = "Attendee2";

        Attendee attendee1 = new Attendee("attendee1@gmail.com", "Attendee1", "Attendee One");
        Attendee attendee2 = new Attendee("attendee2@gmail.com", "Attendee2", "Attendee Two");

        ArrayList<String> contacts1 = new ArrayList<>();
        ArrayList<String> contacts2 = new ArrayList<>();
        contacts1.add("Attendee2");
        contacts2.add("Attendee1");

        attendee1.setContacts(contacts1);
        attendee2.setContacts(contacts2);

        try {
            ac.addContact(attendee1, attendee2);
        } catch (ObjectInvalidException e) {
            e.printStackTrace();
        }

        String subject = "Important News";
        mg.sendPrivateMessage(content, senderID, recipientID, subject);
        assertTrue(mg.viewInbox(recipientID).contains(new Message(senderID, recipientID, subject, content)));
    }
}
