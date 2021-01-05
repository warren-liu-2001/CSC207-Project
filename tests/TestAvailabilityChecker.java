package tests;

import Entities.Attendee;
import Entities.Room;
import Exceptions.DoesNotExistException;

import UseCases.AvailabilityChecker;
import UseCases.RoomManager;
import UseCases.TalkManager;
import UseCases.UserManager;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.GregorianCalendar;

public class TestAvailabilityChecker {

    //Checks that test will return false if speaker is busy with another talk
    @Test
    public void testIsSpeakerAvailable() throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertFalse(ac.isSpeakerAvailable(talk, speaker2, tm));
    }

    //Checks that method returns false if speaker is attending another event
    @Test
    public void testIsSpeakerAvailableEvent() throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk2);
        speaker1.getRegisteredEvents().add("Talk 2");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertFalse(ac.isSpeakerAvailable(talk2, speaker1, tm));
    }

    //Checks that method returns true when speaker isn't busy
    @Test
    public void testIsSpeakerAvailableTrue() throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        tm.getTalks().add(talk);
        speaker1.getHostingTalks().add("Talk 1");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertTrue(ac.isSpeakerAvailable(talk, speaker2, tm));
    }

    //Checks that isUserAvailable returns false when user is busy
    @Test
    public void testIsUserAvailable() throws DoesNotExistException{
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        Entities.Attendee attendee = new Entities.Attendee("attendee@gmail.com", "Attendee 1", "Noah");
        attendee.getRegisteredEvents().add("Talk 1");
        assertFalse(ac.isUserAvailable(talk2, attendee, tm));
    }

    //Checks that isUserAvailable returns true when user is free
    @Test
    public void testIsUserAvailableTrue() throws DoesNotExistException{
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(7, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        Entities.Attendee attendee = new Entities.Attendee("attendee@gmail.com", "Attendee 1", "Noah");
        attendee.getRegisteredEvents().add("Talk 1");
        assertTrue(ac.isUserAvailable(talk2, attendee, tm));
    }

    //Checks isRoomAvailable returns false when room is busy
    @Test
    public void testIsRoomAvailable() throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertFalse(ac.isRoomAvailable(talk2, room, tm));
    }

    //Checks isRoomAvailable returns true when room is free
    @Test
    public void testIsRoomAvailableTrue() throws DoesNotExistException {
        AvailabilityChecker ac = new AvailabilityChecker();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(9, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertTrue(ac.isRoomAvailable(talk2, room, tm));
    }
}

