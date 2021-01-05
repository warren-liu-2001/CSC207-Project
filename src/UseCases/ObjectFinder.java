package UseCases;

import Entities.Event;
import Entities.Room;
import Entities.Talk;
import Entities.User;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;


import java.util.ArrayList;

/**
 * This class has no use and is deprecated.
 *
 * @version 1.0
 */

public class ObjectFinder {
    private static volatile ObjectFinder instance = null;
    private ObjectFinder() {
    }

    public static ObjectFinder getInstance() {
        if (instance == null) {instance = new ObjectFinder();}
        return instance;
    }

    public Object findTheObject(String objectID, UserManager um, RoomManager rm, TalkManager tm) throws ObjectInvalidException, DoesNotExistException {
        String[] information = objectID.split(" ");
        String objectType = information[0];
        String id = information[1];
        Object Objected = null;

        try {
            String classType;
            ArrayList<String> User_types = new ArrayList<>();
            User_types.add("Attendee");
            User_types.add("Organizer");
            User_types.add("Speaker");
            User_types.add("Admin");

            if (objectType.equals("Room")) {
                Objected = findRoom(id, rm);
                return Objected;
            }
            else if (objectType.equals("Talk")) {
                Objected = findTalk(id, tm);
                return Objected;
            }
            else if (User_types.contains(objectType)){
                Objected = findUser(id, um);
                return Objected;
            }
            else {throw new ObjectInvalidException("This Object Does Not Exist");}

        }

        catch (DoesNotExistException e) {
            System.out.println("Object Does not exist within the class");
            e.printStackTrace();
            throw new DoesNotExistException("Invalid");
        }

    }

    public User findUser(String userId, UserManager um) throws ObjectInvalidException, DoesNotExistException {
        for (User user : um.getUserList()) {
            if (user.getUserID().equals(userId)) {
                return user;
            }
        }
        throw new DoesNotExistException("This user ID does not exist!");
    }

    public Event findTalk(String ID, TalkManager tm) throws DoesNotExistException {
        for (Event event : tm.getTalks()) {
            if (event.getEventID().equals(ID)) {
                return event;
            }
        }
        throw new DoesNotExistException("This Talk with ID does not exist");
    }

    public Room findRoom(String ID, RoomManager rm) throws DoesNotExistException {
        for (Room room : rm.getRooms()) {
            if (room.getRoomID().equals(ID)) {
                return room;
            }
        }
        throw new DoesNotExistException("This ID does not exist");
    }
}
