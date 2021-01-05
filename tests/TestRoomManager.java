import Entities.Attendee;
import Entities.Room;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import UseCases.RoomManager;
import UseCases.TalkManager;
import UseCases.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
public class TestRoomManager {

        //Checks if addAttendee will return false when attendee is busy
        @Test
        void testAddAttendee() throws Exception {
            // Setup
            UserManager um = new UserManager();
            TalkManager tm = new TalkManager();
            RoomManager rm = new RoomManager();
            Entities.Attendee attendee = new Attendee("chelsea", "Attendee 123", "cm@gmail.com");
            um.getUserList().add(attendee);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            um.getUserList().add(new Entities.Speaker("a", "Speaker 2", "MannyS"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            Entities.Event talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
            tm.getTalks().add(talk);
            tm.getTalks().add(talk2);
            attendee.getRegisteredEvents().add(talk.getEventID());
            Room room = new Room(9, 0, 23, 0, "roomID", 5, "name");
            rm.getRooms().add(room);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 1");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertFalse(rm.addAttendee("Manny's Talk", "Attendee 123", um, tm));
        }

        //Checks if addAttendee will return false when event is at maxCapacity
        @Test
        void testAddAttendeeMaxCapacity() throws Exception {
            // Setup
            UserManager um = new UserManager();
            TalkManager tm = new TalkManager();
            RoomManager rm = new RoomManager();
            Entities.Attendee attendee = new Attendee("chelsea", "Attendee 123", "cm@gmail.com");
            um.getUserList().add(attendee);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 0);
            tm.getTalks().add(talk);
            Room room = new Room(9, 0, 23, 0, "roomID", 5, "name");
            rm.getRooms().add(room);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 1");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertFalse(rm.addAttendee("Chelsea's Talk", "Attendee 123", um, tm));
        }

        //Checks that an attendee can be added successfully
        @Test
        void testAddAttendeeTrue() throws Exception {
            // Setup
            UserManager um = new UserManager();
            TalkManager tm = new TalkManager();
            RoomManager rm = new RoomManager();
            Entities.Attendee attendee = new Attendee("chelsea", "Attendee 123", "cm@gmail.com");
            um.getUserList().add(attendee);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 0);
            tm.getTalks().add(talk);
            Room room = new Room(9, 0, 23, 0, "roomID", 5, "name");
            rm.getRooms().add(room);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 1");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertTrue(rm.addAttendee("Chelsea's Talk", "Attendee 123", um, tm));
        }

        //Checks that attendee won't be added if room is at capacity
        @Test
        void testAddAttendeeRoomCapacity() throws Exception {
            // Setup
            UserManager um = new UserManager();
            TalkManager tm = new TalkManager();
            RoomManager rm = new RoomManager();
            Entities.Attendee attendee = new Attendee("chelsea", "Attendee 123", "cm@gmail.com");
            um.getUserList().add(attendee);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 0);
            tm.getTalks().add(talk);
            Room room = new Room(9, 0, 23, 0, "roomID", 0, "name");
            rm.getRooms().add(room);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 1");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertFalse(rm.addAttendee("Chelsea's Talk", "Attendee 123", um, tm));
        }

        //Checks that we can't add an event to schedule if it's not within room's hours
        @Test
        void testAddEventToSchedule() throws Exception {
            // Setup
            RoomManager rm = new RoomManager();
            Room room = new Room(9, 0, 13, 0, "roomID", 0, "name");
            TalkManager tm = new TalkManager();
            UserManager um = new UserManager();
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            tm.getTalks().add(talk);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));

            assertFalse(rm.addEventToSchedule(room, "Talk 1", tm, um));
        }

        @Test
        //Checks that we can't add an event to the schedule if room is occupied
        void testAddEventToScheduleOccupied() throws Exception {
            // Setup
            RoomManager rm = new RoomManager();
            Room room = new Room(5, 0, 23, 0, "roomID", 0, "name");
            rm.getRooms().add(room);
            TalkManager tm = new TalkManager();
            UserManager um = new UserManager();
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            um.getUserList().add(new Entities.Speaker("a", "Speaker 2", "MannyS"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            tm.getTalks().add(talk);
            Entities.Event talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(6, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
            tm.getTalks().add(talk2);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 2");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertFalse(rm.addEventToSchedule(room, "Talk 1", tm, um));
        }

        //Checks that we can add an event to the schedule successfully
        @Test
        void testAddEventToScheduleTrue() throws Exception {
            // Setup
            RoomManager rm = new RoomManager();
            Room room = new Room(5, 0, 13, 0, "roomID", 0, "name");
            TalkManager tm = new TalkManager();
            UserManager um = new UserManager();
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            tm.getTalks().add(talk);
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            assertTrue(rm.addEventToSchedule(room, "Talk 1", tm, um));
        }


        //Checks that we can remove an event from a schedule if it's scheduled
        @Test
        void testRemoveEventFromSchedule() throws Exception {
            // Setup
            TalkManager tm = new TalkManager();
            UserManager um = new UserManager();
            RoomManager rm = new RoomManager();
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            tm.getTalks().add(talk);
            Room room = new Room(9, 0, 13, 0, "roomID", 0, "name");
            rm.getRooms().add(room);
            ArrayList<String> events = new ArrayList<>();
            events.add("Talk 1");
            room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
            assertTrue(rm.removeEventFromSchedule("Talk 1", tm, um));
        }

        //Checks that function returns false if talk is not scheduled
        @Test
        void testRemoveEventFromScheduleFalse() throws Exception {
            TalkManager tm = new TalkManager();
            UserManager um = new UserManager();
            RoomManager rm = new RoomManager();
            um.getUserList().add(new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM"));
            Entities.Event talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
            tm.getTalks().add(talk);
            Room room = new Room(9, 0, 13, 0, "roomID", 0, "name");
            rm.getRooms().add(room);
            assertFalse(rm.removeEventFromSchedule("Talk 1", tm, um));
        }

}
