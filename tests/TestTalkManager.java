package tests;

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
import Entities.Room;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class TestTalkManager {

    //Checks that replace speaker can return true
    @Test
    public void testReplaceSpeaker() throws DoesNotExistException {
        UserManager um = new UserManager();
        RoomManager rm = new RoomManager();
        TalkManager tm = new TalkManager();
        Entities.Speaker speaker1 = new Entities.Speaker("speaker@gmail.com", "Speaker 1", "ChelseaM");
        Entities.Speaker speaker2 = new Entities.Speaker("a", "Speaker 2", "MannyS");
        um.getUserList().add(speaker1);
        um.getUserList().add(speaker2);
        speaker1.getHostingTalks().add("Talk 1");
        Entities.Talk talk = new Entities.Talk("Chelsea's Talk", new GregorianCalendar(2020, Calendar.JANUARY, 5), LocalTime.of(5, 30), new ArrayList<>(), "Talk 1", 60, "Speaker 1", 10);
        Room room = new Room(5, 0, 23, 0, "roomID", 5, "name");
        rm.getRooms().add(room);
        ArrayList<String> events = new ArrayList<>();
        events.add("Talk 1");
        room.getSchedule().put(new GregorianCalendar(2020, Calendar.JANUARY, 5), events);
        assertTrue(tm.replaceSpeaker(talk, "Speaker 2", um));
    }

    //Checks that

}
