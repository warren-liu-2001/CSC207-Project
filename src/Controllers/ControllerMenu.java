package Controllers;


import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Presenters.DisplayInfo;
import Presenters.ErrorMessages;
import Presenters.InfoPrompts;
import Presenters.ScheduleViewer;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <h1>Presenter Class</h1>
 * The main gateway for the program, this is a command line interface *
 *
 * This class is deprecated as of phase 2.
 *
 * @author  Warren Liu
 * @version 0.1
 */

public class ControllerMenu {

    public ControllerMainLoop pm;
    private ErrorMessages em;
    private InfoPrompts ip;
    private DisplayInfo di;
    private ScheduleViewer sv;

    /**
     *
     * @param pm helper method
     * @param em error messages
     * @param ip infoprompts
     * @param di displayinfo
     */
    public ControllerMenu(ControllerMainLoop pm, ErrorMessages em, InfoPrompts ip, DisplayInfo di){
        this.pm = pm;
        this.em = em;
        this.ip = ip;
        this.di = di;
    }

    /**
     * Input of controller of phase 1
     * @param input input scanner
     * @param ucs use case storage
     * @param uas user account system
     * @param isloggedIn if user is logged in
     * @param selection selection
     * @return if action carried out
     * @throws DoesNotExistException if object DNE
     */
    public boolean userMenu(Scanner input, UseCaseStorage ucs, UserAccountSystem uas, boolean isloggedIn, int selection) throws DoesNotExistException {

        sv = new ScheduleViewer(uas.getUserAccount(), ucs);

        if (selection == 1) {

            // Change email
            ip.attributePrompt("email");
            String newEmail = input.nextLine();

            this.EmailChange(uas, newEmail);

        } else if (selection == 2) {

            // Change name
            ip.attributePrompt("name");
            String newName = input.nextLine();

            this.NameChange(uas, newName);

        } else if (selection == 3) {

            // Change password
            ip.attributePrompt("password");
            String newPass = input.nextLine();

            this.PasswordSet(uas, newPass);

        } else if (selection == 4) {

            // View email
            String email = this.getEmail(uas);
            di.printUserAttribute("email", email);

        } else if (selection == 5) {

            // View name
            String name = this.getName(uas);
            di.printUserAttribute("name", name);

        } else if (selection == 6) {

            // View password
            String password = this.getPassword(uas);
            di.printUserAttribute("password", password);

        } else if (selection == 7) {

            // Add contact
            ip.attributePrompt("friend user id");
            String newContact = input.nextLine();

            this.addFriend(uas, newContact);

        } else if (selection == 8) {

            // Remove contact
            ip.attributePrompt("friend user id");
            String removeID = input.nextLine();

            if (uas.getUserAccount().removeFriend(removeID)) {
                di.actionSuccessful("contact removal");
            } else {
                em.actionFailed();
            }

        } else if (selection == 9) {

            // View entire schedule
            sv.viewEntireSchedule();

        } else if (selection == 10) {

            // Send a private message
            ip.attributePrompt("recipient's id");
            String email = input.nextLine();
            ip.attributePrompt("message subject");
            String subject = input.nextLine();
            ip.attributePrompt("message content");
            String content = input.nextLine();
            if (uas.getUserAccount().sendMessage(subject, content, email, uas.getMessenger())) {
                di.actionSuccessful("message sending");
            } else {
                em.actionFailed();
            }

        } else if (selection == 11) {

            // View messages
            di.printMessages(uas.getUserInfo().viewInbox(uas.getMessenger()));

        } else if (selection == 12) {

            // View contacts
            try {
                di.printUserAttribute("contacts", uas.getUserInfo().viewContacts());
            } catch (DoesNotExistException e) {
                em.actionFailed();
            }

        } else if (selection == 13) {

            // log out
            uas.getUserAccount().logout();
            isloggedIn = false;

        }
        return isloggedIn;
    }

    /**
     * special menu for speakers
     * @param input the input parameter
     * @param ucs use case storage
     * @param uas user acc system
     * @param selection selection
     * @throws DoesNotExistException if obnject DNE
     * @throws ObjectInvalidException if obkject Invalid
     */

    public void speakerMenu(Scanner input, UseCaseStorage ucs, UserAccountSystem uas, int selection) throws DoesNotExistException, ObjectInvalidException {
        if (selection == 30) { // Speaker specific options

            // View talks you are giving
            sv.viewMyEvents(ucs);

        } else if (selection == 31) {

            // Message all attendees of multiple of your talks

            // First get the list of talks to message
            ArrayList<String> talks = new ArrayList<String>();
            Boolean enteringTalks = true;
            String nextTalk;

            while (enteringTalks) {

                ip.multipleAttributePrompt("talk id", "exit");

                nextTalk = input.nextLine();
                if (nextTalk.equals("exit")) {
                    enteringTalks = false;
                } else {
                    talks.add(nextTalk);
                }
            }

            // Next get the actual message
            ip.attributePrompt("message subject");
            String subject = input.nextLine();
            ip.attributePrompt("message content");
            String content = input.nextLine();

            // Send the message
            if (uas.getUserAccount().messageMultipleTalks(talks, subject, content, uas.getMessenger())) {
                di.actionSuccessful("message sending");
            } else {
                em.actionFailed();
            }

        } else if (selection == 32) {

            // Message all attendees of a talk
            ip.attributePrompt("talk id");
            String talkID = input.nextLine();
            ip.attributePrompt("message subject");
            String subject = input.nextLine();
            ip.attributePrompt("message content");
            String content = input.nextLine();

            if (uas.getUserAccount().messageTalk(talkID, subject, content, uas.getMessenger())) {
                di.actionSuccessful("message sending");
            } else {
                em.actionFailed();
            }
        }
    }

    /**
     * Menu for organizers
     * @param input input
     * @param ucs usecasestorage
     * @param uas user acc sustem
     * @param selection the selection to be made
     * @throws DoesNotExistException if obecket dne
     */
    public void organizerMenu(Scanner input, UseCaseStorage ucs, UserAccountSystem uas, int selection) throws DoesNotExistException {
        if (selection == 40) { // Organizer specific options
            // Create a new room
            int openHourDef = 0;
            boolean temp1 = true;
            while (temp1) {
                ip.attributePrompt("room opening hour");
                int openHour = input.nextInt();
                if (9 <= openHour && openHour <= 17) {
                    openHourDef = openHour;
                    temp1 = false;
                }
            }

            int openMinuteDef = 0;
            boolean temp2 = true;
            while (temp2) {
               ip.attributePrompt("room opening minute");
                int openMinute = input.nextInt();
                if (0 <= openMinute && openMinute <= 59) {
                    openMinuteDef = openMinute;
                    temp2 = false;
                }
            }

            int closeHourDef = 0;
            boolean temp3 = true;
            while (temp3) {
                ip.attributePrompt("room closing hour");
                int closeHour = input.nextInt();
                if (9 <= closeHour && closeHour <= 17 && openHourDef < closeHour) {
                    closeHourDef = closeHour;
                    temp3 = false;
                }
            }

            int closeMinuteDef = 0;
            boolean temp4 = true;
            while (temp4) {
                ip.attributePrompt("room closing minute");
                int closeMinute = input.nextInt();
                if (0 <= closeMinute && closeMinute <= 59) {
                    closeMinuteDef = closeMinute;
                    temp4 = false;
                }
            }

            boolean validCapacity = false;
            int capacity = 0;
            ip.attributePrompt("room capacity");
            while (!validCapacity) {
                try {
                    ip.attributePrompt("room capacity");
                    capacity = input.nextInt();
                    validCapacity = true;
                } catch (InputMismatchException e) {
                    em.invalidInput();
                }
            }
            uas.getUserAccount().createRoom(openHourDef, openMinuteDef, closeHourDef, closeMinuteDef, capacity);
            di.actionSuccessful("room creation");

        } else if (selection == 41) {
            // Create a new speaker
            boolean temp = true;
            while (temp) {
                ip.attributePrompt("name");
                String name = input.nextLine();
                ip.attributePrompt("email");
                String email = input.nextLine();
                ip.attributePrompt("password");
                String password = input.nextLine();
                if (uas.getUserAccount().createSpeaker(email, name, password)) {
                    temp = false;
                } else {
                    // speaker already exists
                    em.actionFailed();
                }
            }
            di.actionSuccessful("speaker creation");

        } else if (selection == 42) {
            // Schedule a Talk

            Controllers.TalkOrganizer to = new Controllers.TalkOrganizer(uas.getUserAccount(), ucs);

            // choose which type of event
            ip.eventDescriptionsPrompt();
            ip.whichEventTypePrompt();
            ip.optionPrompt(1, 3);
            int eventType = input.nextInt();

            // make list of organizers (by ID) who will be responsible for this event
            ArrayList<String> organizerIDs = new ArrayList<String>();
            organizerIDs.add(uas.getUserAccount().getUserID());

            boolean enteringOrganizers = true;
            while (enteringOrganizers) {
                ip.multipleAttributePrompt("ID of another organizer for this event", "done");
                String nextOrganizer = input.nextLine();
                if (nextOrganizer.equals("done")) {
                    enteringOrganizers = false;
                } else {
                    organizerIDs.add(nextOrganizer);
                }
            }

            // event title
            ip.attributePrompt("event name");
            String title = input.nextLine();

            // display occupied times for organizer to schedule an event
            di.printOccupiedTimes(ucs.getRoomManager(), ucs.getTalkManager());

            // event room
            ip.attributePrompt("room ID");
            String roomID = input.nextLine();

            // event date
            ip.attributePrompt("event year");
            int year = input.nextInt();
            ip.monthPrompt();
            int month = input.nextInt() - 1;
            ip.attributePrompt("day of event");
            int day = input.nextInt();
            GregorianCalendar date = new GregorianCalendar(year, month, day);

            // event start time
            ip.attributePrompt("hour the event starts");
            int hour = input.nextInt();
            ip.attributePrompt("minute within the hour");
            int minute = input.nextInt();
            LocalTime startTime = LocalTime.of(hour, minute);

            // event duration
            ip.attributePrompt("duration of the event, in minutes");
            int duration = input.nextInt();

            // event capacity
            ip.attributePrompt("capacity of the event");
            int maxLimit = input.nextInt();

            /*
            Someone added this in which denotes a maximum time limit. However, there is no max time limit parameter in
            TalkOrganizer's addTalk/addPanel/addParty method. Commented out if needed for later.
             */
//            boolean validLimit = false;
//            int limit = 0;
//            ip.attributePrompt("Please nter the Maximum time limit, in minutes");
//            while (!validLimit) {
//                try {
//                    limit = input.nextInt();
//                    validLimit = true;
//                } catch (InputMismatchException e) {
//                    em.invalidInput();
//                }
//            }


            // call different constructors for event creation and scheduling
            boolean successfulScheduling = false;
            if (eventType == 1) { // talk event
                ip.attributePrompt("ID of the speaker");
                String speakerID = input.nextLine();

                // check if speaker is available for the talk
                if (to.isSpeakerAvailable(speakerID, date, startTime, duration)) {
                    to.addTalk(title, date, startTime, speakerID, duration, organizerIDs, roomID, maxLimit);
                    successfulScheduling = true;
                } else {
                    ip.speakerUnavailablePrompt(speakerID);
                }

            } else if (eventType == 2) { // panel event
                ArrayList<String> speakerIDs = new ArrayList<String>();

                // check is each speaker is available for the panel
                boolean allSpeakersAvailable = true;
                boolean enteringSpeakers = true;
                while (enteringSpeakers) {
                    ip.multipleAttributePrompt("ID of the speaker", "done");
                    String speakerID = input.nextLine();
                    if (speakerID.equals("done")) {
                        enteringSpeakers = false;
                    } else {
                        if (!(to.isSpeakerAvailable(speakerID, date, startTime, duration))) {
                            ip.speakerUnavailablePrompt(speakerID);
                            enteringSpeakers = false;
                            allSpeakersAvailable = false;
                        } else {
                            speakerIDs.add(speakerID);
                        }
                    }
                }
                if (allSpeakersAvailable) {
                    to.addPanel(title, date, startTime, speakerIDs, duration, organizerIDs, roomID, maxLimit);
                    successfulScheduling = true;
                }

            } else if (eventType == 3) { // party event
                // no speakers, so party should be scheduled
                to.addParty(title, date, startTime, duration, organizerIDs, roomID, maxLimit);
                successfulScheduling = true;
            }
            if (successfulScheduling) {
                di.actionSuccessful("event scheduling");
            }

        } else if (selection == 43) {

            // Message all speakers
            ip.attributePrompt("message subject");
            String subject = input.nextLine();
            ip.attributePrompt("message content");
            String content = input.nextLine();
            if (uas.getUserAccount().messageAllSpeakers(subject, content, uas.getMessenger())) {
                di.actionSuccessful("message sending");
            } else {
                em.actionFailed();
            }

        } else if (selection == 44) {

            // Message all Attendees
            ip.attributePrompt("message subject");
            String subject = input.nextLine();
            ip.attributePrompt("message content");
            String content = input.nextLine();
            if (uas.getUserAccount().messageAllAttendees(subject, content, uas.getMessenger())) {
                di.actionSuccessful("message sending");
            } else {
                em.actionFailed();
            }

        }
    }

    /**
     * menu for attendees
     * @param input input
     * @param ucs ucs
     * @param uas uas
     * @param selection selection
     * @throws DoesNotExistException if object DNE
     * @throws ObjectInvalidException if object invalid
     */
    public void attendeeMenu(Scanner input, UseCaseStorage ucs, UserAccountSystem uas, int selection) throws DoesNotExistException, ObjectInvalidException {
        if (selection == 60) { // Attendee specific actions

            // View available talks (maybe organizers should be able to see this too?)
            sv.viewAvailableEvents();

        } else if (selection == 61) {
            // View Talks already signed up in
            Registrar r = new Registrar(ucs);
            try {
                di.printUserAttribute("registered talks", r.getRegisteredTalks(uas.getUserAccount().getUserID()));
            } catch (ObjectInvalidException e) {
                em.actionFailed();
            }

        } else if (selection == 62) {
            // Sign up for a talk
            Registrar r = new Registrar(ucs);

            ip.attributePrompt("talk id");
            String talkID = input.nextLine();
            if (uas.getUserAccount().register(talkID, r)) {
                di.actionSuccessful("signup");
            } else {
                em.actionFailed();
            }

        } else if (selection == 63) {
            // Cancel spot in a talk
            Registrar r = new Registrar(ucs);

            ip.attributePrompt("talk id");
            String talkID = input.nextLine();
            if (uas.getUserAccount().cancel(talkID, r)) {
                di.actionSuccessful("cancellation");
            } else {
                em.actionFailed();
            }

        }
    }

    /**
     * Add reidnd
     * @param uas uas
     * @param newContact contactID
     */

    public void addFriend(UserAccountSystem uas, String newContact) {
        if (uas.getUserAccount().addFriend(newContact)) {
            di.actionSuccessful("contact addition");
        } else {
            em.actionFailed();
        }
    }

    /**
     * useraccsystem
     * @param uas uas
     * @return password
     */

    public String getPassword(UserAccountSystem uas) {
        String password = uas.getUserInfo().getPassword();
        return password;
    }

    /**
     * get name
     * @param uas user acc sytem
     * @return
     */

    public String getName(UserAccountSystem uas) {
        String name = uas.getUserInfo().getName();
        return name;
    }

    /**
     *
     * @param uas user acc system
     * @return email
     */
    public String getEmail(UserAccountSystem uas) {
        String email = uas.getUserInfo().getEmail();
        return email;
    }

    /**
     * password setter
     * @param uas user acc syterm
     * @param newPass the new password to be set
     */
    public void PasswordSet(UserAccountSystem uas, String newPass) {
        if (uas.getUserAccount().setPassword(newPass)) {
            di.actionSuccessful("password change");
        } else {
            em.actionFailed();
        }
    }

    /**
     *
     * @param uas the user acc system
     * @param newName new name
     * @throws DoesNotExistException if DNE
     */
    public void NameChange(UserAccountSystem uas, String newName) throws DoesNotExistException {
        if (uas.getUserAccount().setName(newName)) {
            di.actionSuccessful("name change");
        } else {
            em.actionFailed();
        }
    }

    /**
     * email set
     * @param uas user acc system
     * @param newEmail the new email to be set
     */
    public void EmailChange(UserAccountSystem uas, String newEmail) {
        if (uas.getUserAccount().setEmail(newEmail)) {
            di.actionSuccessful("email change");
        } else {
            em.actionFailed();
        }
    }

    /**
     * Get a login for the user
     * @param uas the user account system object
     * @param name1 users name
     * @param email1 users email
     * @param password1 users password
     * @return if user logged in successfully
     */
    public Boolean getLogin(UserAccountSystem uas, String name1, String email1, String password1) {
        return uas.getUserAccount().login(email1, name1, password1);
    }
}
