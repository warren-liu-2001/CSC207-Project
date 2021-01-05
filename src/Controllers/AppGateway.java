package Controllers;

import Presenters.ErrorMessages;
import UseCases.*;
import Exceptions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * This stub class loads and stores data via csvs.
 * This class has been abandoned for phase 1 as we switched from .csv data storage to .ser via serialization.
 *
 * @version 0.1.1
 */

/*
public class AppGateway {

    /**
     * Creates a Java class for the uploading of stored data at program initiation DEPRECATED
     *

    private static final String users = "Users.csv";
    private static final String events = "Events.csv";
    private static final String messages = "Messages.csv";
    private static final String bruh = "Messages.csv";
    private ErrorMessages em;

    private UseCaseStorage ucs;

    public AppGateway(UseCaseStorage ucs) throws FileNotFoundException {
        this.ucs = ucs;
        this.em = new ErrorMessages();

        File usersfile = new File(users);
        File eventsfile = new File(events);
        File messagesfile = new File(messages);

        try {
            Scanner usersScanner = new Scanner(usersfile);

            while (usersScanner.hasNextLine()) {
                String[] user = usersScanner.nextLine().split(",");

                if (user[0].equals("Attendee")) {

                    // Populate the attendee manager with this user
                    ArrayList<String> Friends = new ArrayList<>(Arrays.asList(user).subList(5, user.length));
                    //??
                    AttendeeManager am = this.ucs.getAttendeeManager();
                    am.createAttendee(user[3], user[1], user[2]);
                    am.setAttendeePassword(user[1], user[4]);
                    // From the 6th index onwards, the line in the .csv reads a list of friends ID's
                    for (String friend: Friends) {
                        am.setContactViaID(user[1], friend);
                    }

                }

                else if (user[0].equals("Organizer")) {
                    ArrayList<String> Friends = new ArrayList<>(Arrays.asList(user).subList(5, user.length));
                    AttendeeManager am = this.ucs.getAttendeeManager();
                    am.createOrganizer(user[2], user[1], user[3]);
                    am.setAttendeePassword(user[1], user[4]);
                    // From the 6th index onwards, the line in the .csv reads a list of friends
                    for (String friend: Friends) {
                        am.setContactViaID(user[1], friend);}
                }

                else {
                    ArrayList<String> Friends = new ArrayList<>(Arrays.asList(user).subList(4, user.length));
                    SpeakerManager sm = this.ucs.getSpeakerManager();
                    sm.createSpeaker(user[2], user[3]);
                    sm.setSpeakerID(user[2], user[1]);
                    sm.setSpeakerPassword(user[1], user[4]);
                }
            }

        }
        catch (FileNotFoundException e) {
            em.fileDNE(e);

        }
        catch (DoesNotExistException e2) {
            em.loadFailed(e2);
        }

    }
    */
    /*

    public void saveUsers(AttendeeManager am) {
        try {

            // get list of attendees
            ArrayList<Attendee> users = am.getAttendeeList();
            // create a writer
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.valueOf(users)));

            // write header record
            writer.write("ID,Name,Email,Password");
            writer.newLine();
            // write all records
            for (Attendee user : users) {
                writer.write(String.join(",", user.getUserID(), user.getName(), user.getEmail(), user.getPassword()));
                writer.newLine();
            }

            //close the writer
            writer.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}
*/
