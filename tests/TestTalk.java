import Entities.Organizer;
import Entities.Speaker;
import Entities.Talk;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import UseCases.TalkManager;
import UseCases.UserManager;
import UseCases.UserType;
import org.junit.*;
import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TestTalk {
    // parameters needed for tests

    // organizers
    String organizerEmail1 = "oragnizer1@gmail.com";
    String organizerID1 = "Organizer " + (new UUID(10, 10)).toString();
    String organizerName1 = "Jane Doe";
    Organizer organizer1 = new Organizer(organizerEmail1, organizerID1, organizerName1);

    String organizerEmail2 = "oragnizer2@gmail.com";
    String organizerID2 = "Organizer " + (new UUID(10, 10)).toString();
    String organizerName2 = "John Doe";
    Organizer organizer2 = new Organizer(organizerEmail2, organizerID2, organizerName2);

    // speakers (a talk can only have 1)
    String speakerEmail1 = "speaker1@gmail.com";
    String speakerID1 = "Speaker " + (new UUID(10, 10)).toString();
    String speakerName1 = "Maria Smith";
    Speaker speaker1 = new Speaker(speakerEmail1, speakerID1, speakerName1);

    String speakerEmail2 = "speaker2@gmail.com";
    String speakerID2 = "Speaker " + (new UUID(10, 10)).toString();
    String speakerName2 = "Chris Fan";
    Speaker speaker2 = new Speaker(speakerEmail2, speakerID2, speakerName2);

    // talk info
    String title = "Tech Companies";
    GregorianCalendar date = new GregorianCalendar(2020, 12, 1);
    LocalTime time = LocalTime.of(13, 30);
    int duration = 60;
    int maxLimit = 100;


    @Test
    public void testEventCreation() {
        TalkManager tm = new TalkManager();

        ArrayList<String> organizerIDs = new ArrayList<String>();
        organizerIDs.add(organizerID1);
        organizerIDs.add(organizerID2);

        tm.createTalk(title, date, time, organizerIDs, duration, speakerID1, maxLimit);

        String talkID1 = tm.getAllTalks().get(0);
        assertTrue("The talk has not been created\n", tm.isEvent(talkID1));

    }

    @Test
    public void testSwitchSpeakers() {
        TalkManager tm = new TalkManager();
        UserManager um = new UserManager();

        um.setEmail(organizerEmail1);
        um.setUserID(organizerID1);
        um.setName(organizerName1);
        um.createUser(UserType.ORGANIZER);

        um.setEmail(organizerEmail2);
        um.setUserID(organizerID2);
        um.setName(organizerName2);
        um.createUser(UserType.ORGANIZER);

        um.setEmail(speakerEmail1);
        um.setUserID(speakerID1);
        um.setName(speakerName1);
        um.createUser(UserType.SPEAKER);

        um.setEmail(speakerEmail2);
        um.setUserID(speakerID2);
        um.setName(speakerName2);
        um.createUser(UserType.SPEAKER);

        ArrayList<String> organizerIDs = new ArrayList<String>();
        organizerIDs.add(organizerID1);
        organizerIDs.add(organizerID2);

        tm.createTalk(title, date, time, organizerIDs, duration, speakerID1, maxLimit);
        String talkID2 = tm.getAllTalks().get(0);

        try {
            tm.replaceSpeaker((Talk) tm.findTalk(talkID2), speakerID2, um);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }

        try {
            assertEquals("incorrect speaker\n", tm.getTalkSpeaker(talkID2).get(0), speakerID2);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        }
    }
}

