import Entities.*;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import UseCases.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TestAttributeChanger {

    public void testFxnality() {
        AttributeChanger ac = new AttributeChanger();
        GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
        LocalTime startTime = LocalTime.of(13, 30);
        ArrayList<String> organizers = new ArrayList<String>();
        organizers.add("Organizer1");
        Event ev = new Party("Birthday", date, startTime, organizers, "Event1", 60, 90);
        UserManager um = new UserManager();
        Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Atten Dee");
        Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
    }



    @Test
    public void testAddContact() throws ObjectInvalidException, DoesNotExistException {
        AttributeChanger ac = new AttributeChanger();
        GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
        LocalTime startTime = LocalTime.of(13, 30);
        ArrayList<String> organizers = new ArrayList<String>();
        organizers.add("Organizer1");
        Event ev = new Party("Birthday", date, startTime, organizers, "Event1", 60, 90);
        UserManager um = new UserManager();
        Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Atten Dee");
        Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
        ac.addContact("Attendee1", "Attendee2", um);
        assertTrue(ac.getContactsID("Attendee1", um).contains("Attendee2"));
    }

    @Test
    public void testRemoveContact() throws ObjectInvalidException, DoesNotExistException {
        AttributeChanger ac = new AttributeChanger();
        GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
        LocalTime startTime = LocalTime.of(13, 30);
        ArrayList<String> organizers = new ArrayList<String>();
        organizers.add("Organizer1");
        Event ev = new Party("Birthday", date, startTime, organizers, "Event1", 60, 90);
        UserManager um = new UserManager();
        Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Atten Dee");
        Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
        ac.addContact("Attendee1", "Attendee2", um);
        ac.removeContact("Attendee1", "Attendee2", um);
        assertTrue(!ac.getContactsID("Attendee1", um).contains("Attendee2"));
    }

    @Test
    public void testRemoveFromEventList() throws ObjectInvalidException, DoesNotExistException {
        AttributeChanger ac = new AttributeChanger();
        GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
        LocalTime startTime = LocalTime.of(13, 30);
        ArrayList<String> organizers = new ArrayList<String>();
        organizers.add("Organizer1");
        Event ev = new Party("Birthday", date, startTime, organizers, "Event1", 60, 90);
        UserManager um = new UserManager();
        Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Atten Dee");
        Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
        ac.addToEventList("Attendee1", "Event1", um);
        ac.removeFromEventList("Attendee1", "Event1", um);
        assertTrue(!a1.getRegisteredEvents().contains("Event1"));
    }

    @Test
    public void testAddToEventList() throws ObjectInvalidException, DoesNotExistException {
        AttributeChanger ac = new AttributeChanger();
        GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
        LocalTime startTime = LocalTime.of(13, 30);
        ArrayList<String> organizers = new ArrayList<String>();
        organizers.add("Organizer1");
        Event ev = new Party("Birthday", date, startTime, organizers, "Event1", 60, 90);
        UserManager um = new UserManager();
        Attendee a1 = new Attendee("a1@gmail.com", "Attendee1", "Atten Dee");
        Attendee a2 = new Attendee("a2@gmail.com", "Attendee2", "Atten Dee");
        ac.addToEventList("Attendee1", "Event1", um);
        assertTrue(a1.getRegisteredEvents().contains("Event1"));
    }

}