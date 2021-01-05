package tests;
import Entities.*;
import UseCases.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.*;

import java.util.Set;
public class TestEventSearch {


    //Check if getEventsByTitle returns the correct list of events
    @Test
    public void testGetEventsByTitle() {
        EventSearch es = new EventSearch();
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        tm.getTalks().add(talk);
        speaker1.getHostingTalks().add("Talk 1");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        ArrayList<Event> arr = new ArrayList<>();
        arr.add(talk);
        Set<Event> testSet = new HashSet<>(arr);
        Set<Event> resultSet = new HashSet<>(es.getEventsByTitle("Chelsea's Talk", tm, rm));
        assertEquals(testSet, resultSet);
    }

    //Check if getEventsBySpeaker returns the correct list of events
    @Test
    public void testGetEventsBySpeaker() {
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Chelsea's Second Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(7, 0), new ArrayList<>(), "Talk 2", 60, "Speaker 1", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker1.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        events.add("Talk 2");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        ArrayList<Event> arr = new ArrayList<>();
        arr.add(talk);
        arr.add(talk2);
        EventSearch es = new EventSearch();
        Set<Event> testSet = new HashSet<>(arr);
        Set<Event> resultSet = new HashSet<>(es.getEventsBySpeaker("Speaker 1", tm, rm));
        assertEquals(testSet, resultSet);
    }

    //Check if GetEventsByTime returns the correct list
    @Test
    public void testGetEventsByTime(){
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        Room room1 = new Room(5, 0, 23, 0, "room2", 5, "room2name");
        rm.getRooms().add(room1);
        ArrayList<String> events = new ArrayList<>();
        ArrayList<String> events2 = new ArrayList<>();
        events.add("Talk 1");
        events2.add("Talk 2");
        GregorianCalendar date = new GregorianCalendar(2020, Calendar.JANUARY, 5);
        room.getSchedule().put(date, events);
        room1.getSchedule().put(date, events2);
        ArrayList<Event> arr = new ArrayList<>();
        arr.add(talk);
        arr.add(talk2);
        EventSearch es = new EventSearch();
        Set<Event> testSet = new HashSet<>(arr);
        Set<Event> resultSet = new HashSet<>(es.getEventsByTime(date, LocalTime.of(5, 30), tm, rm));
        assertEquals(testSet, resultSet);
    }

    @Test
    public void testGetEmptyEvents() {
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        Room room1 = new Room(5, 0, 23, 0, "room2", 5, "room2name");
        rm.getRooms().add(room1);
        ArrayList<String> events = new ArrayList<>();
        ArrayList<String> events2 = new ArrayList<>();
        events.add("Talk 1");
        events2.add("Talk 2");
        GregorianCalendar date = new GregorianCalendar(2020, Calendar.JANUARY, 5);
        room.getSchedule().put(date, events);
        room1.getSchedule().put(date, events2);
        ArrayList<Event> arr = new ArrayList<>();
        arr.add(talk);
        arr.add(talk2);
        EventSearch es = new EventSearch();
        Set<String> testSet = new HashSet<>();
        for (Event event: arr){
            testSet.add(event.getTitle());
        }
        Set<String> resultSet = new HashSet<>(es.getEmptyEvents(tm, rm));
        assertEquals(testSet, resultSet);
    }

    @Test
    public void testGetAllEvents() {
        UserManager um = new UserManager();
        TalkManager tm = new TalkManager();
        RoomManager rm = new RoomManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        um.getUserList().add(speaker1);
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker2);
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Entities.Talk talk2 = new Entities.Talk("Manny's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 2", 60, "Speaker 2", 10);
        tm.getTalks().add(talk);
        tm.getTalks().add(talk2);
        speaker1.getHostingTalks().add("Talk 1");
        speaker2.getHostingTalks().add("Talk 2");
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        Room room1 = new Room(5, 0, 23, 0, "room2", 5, "room2name");
        rm.getRooms().add(room1);
        ArrayList<String> events = new ArrayList<>();
        ArrayList<String> events2 = new ArrayList<>();
        events.add("Talk 1");
        events2.add("Talk 2");
        GregorianCalendar date = new GregorianCalendar(2020, Calendar.JANUARY, 5);
        room.getSchedule().put(date, events);
        room1.getSchedule().put(date, events2);
        ArrayList<Event> arr = new ArrayList<>();
        arr.add(talk);
        arr.add(talk2);
        EventSearch es = new EventSearch();
        Set<Event> testSet = new HashSet<>(arr);
        Set<Event> resultSet = new HashSet<>(es.getAllEvents(tm, rm));
        assertEquals(testSet, resultSet);
    }

}