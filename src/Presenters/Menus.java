package Presenters;

import java.util.ArrayList;
import java.util.Arrays;

// menus + menu navigation messages

/**
 * The Phase 1 code for presenters menus.
 *
 * @version 1.0
 */
public class Menus {

    /**
     * Ah damn, here we go again.
     */

    // string list of actions in the menu
    // menu = anywhere you have to pick a number out of a certain number of choices
    String[] userOptions;
    String[] speakerOptions;
    String[] organizerOptions;
    String[] attendeeOptions;
    String[] loginOptions;
    String[] startupOptions;
    String[] accountCreationOptions;
    String[] adminOptions;

    // number assigned to the first option in that menu (each option's number after is previous + 1)
    int userStartCount;
    int speakerStartCount;
    int organizerStartCount;
    int attendeeStartCount;
    int loginStartCount;
    int startupStartCount;
    int accountCreationStartCount;

    /**
     * The Menu initiation
     */
    public Menus() {
        this.userStartCount = 1;
        this.userOptions = new String[]{"Change your email",
                "Change your name",
                "Change your password",
                "Go to Scheduler",
                "Go to Inbox",
                "Leave a review"};

        this.speakerStartCount = 30;
        this.speakerOptions = new String[] {//"View Talks and Panels you are giving",
                //"Message all attendees of multiple of your events",
                //"Message all attendees of a event"
                };


        this.organizerStartCount = 40;
        this.organizerOptions = new String[] {"Create a User/Event/Room",
                //"Create a new Room\n",
                //"Create a new Speaker",
                //"Schedule an Event",
                //"Message all speakers",
                //"Message all Attendees",
                //"Cancel an Event",
                //"Create a new Organizer",
                //"Create a new Admin",
                //"Create a new Attendee",
                //"Change Event capacity",
                //"Add a speaker to a panel",
                //"Remove a speaker from a panel",
                //"Change the speaker at a talk"
                };

        this.attendeeStartCount = 60;
        this.attendeeOptions = new String[] {
//                "View Events available to sign up",
//                "View Events already signed up in",
//                "Sign Up for an Event",
//                "Cancel spot in an Event"
        };

        this.adminOptions = new String[] {//"Delete message",
                //"Delete Event"
                "Delete a Message"};

        this.loginStartCount = 0;
        this.loginOptions = new String[] {"Cancel login/registration", // System.out.println("Alternatively, hit 0, then 0 again to abort login");
                "Login",
                "Register a new account"};

        this.startupStartCount = 0;
        this.startupOptions = new String[] {"Quit and save data",
                "Log in"};

        this.accountCreationStartCount = 1;
        this.accountCreationOptions = new String[] {"Create a new Attendee account",
                "Create a new Organizer account"};

    }

    /**
     *
     * @return userMenu ArrList
     */
    // for now continues to print stuff, but will likely remove the printing later
    public ArrayList<String> printUserMenu() {
        //this.printMenu(userOptions, userStartCount);
        return new ArrayList<>(Arrays.asList(this.userOptions));
    }

    /**
     *
     * @return SpeakerMenu Arrlist
     */
    public ArrayList<String> printSpeakerMenu() {
        //this.printMenu(speakerOptions, speakerStartCount);
        return new ArrayList<>(Arrays.asList(this.speakerOptions));
    }

    /**
     *
     * @return OrganizerMenu Arrlist
     */
    public ArrayList<String> printOrganizerMenu() {
       // this.printMenu(organizerOptions, organizerStartCount);
        return new ArrayList<>(Arrays.asList(this.organizerOptions));
    }

    /**
     *
     * @return Attendee options Arrlist
     */
    public ArrayList<String> printAttendeeMenu() {
       // this.printMenu(attendeeOptions, attendeeStartCount);
        return new ArrayList<>(Arrays.asList(this.attendeeOptions));
    }

    /**
     *
     * @return AdminOptions ArrList
     */
    public ArrayList<String> printAdminMenu() {
       // this.printMenu(adminOptions, accountCreationStartCount);
        return new ArrayList<>(Arrays.asList(this.adminOptions));
    }

    /**
     *
     * @return the login Menu Arrlist
     */
    public ArrayList<String> printLoginMenu() {
       // this.printMenu(loginOptions, loginStartCount);
        return new ArrayList<>(Arrays.asList(this.loginOptions));
    }

    /**
     *
     *
     * @return the startup menu arrlist
     */
    public ArrayList<String> printStartupMenu() {
      //  this.printMenu(startupOptions, startupStartCount);
        return new ArrayList<>(Arrays.asList(this.startupOptions));
    }

    /**
     *
     * @return AccountCreationMenu arrlist
     */
    public ArrayList<String> printAccountCreationMenu() {
        //this.printMenu(accountCreationOptions, accountCreationStartCount);
        return new ArrayList<>(Arrays.asList(this.accountCreationOptions));
    }

    // helper method, where the actual menu printing happens
    // the other methods just specify which menu is going to be printed

    /**
     *
     * @param menu the menu needed to be printed
     * @param startCount the starting count
     */
    private void printMenu(String[] menu, int startCount) {
        int optionNum = startCount;
        for (String option : menu) {
            // Format: # - Action
            System.out.println(optionNum + " - " + option + "\n");
            optionNum += 1;
        }
    }

    // general messages

    public void onStartup() {
        System.out.println("Initializing Assets and Objects");
        System.out.println("All objects initialized, data has been loaded.");
        System.out.println("Welcome to the TextChat Command Line Interface!");
    }

    public void onEnterLoginMode() {
        System.out.println("You are in login mode");
    }

    public void oneEnterRegistrationMode() {
        System.out.println("You are in account creation mode");
    }

    public void onLogin() {
        System.out.println("Login successful");
        System.out.println("Please See a list of below options to operate:");
        System.out.println("Input a number to select an option\n");
    }

    public void onLogout() {
        System.out.println("Logout successful");
    }

    public void onExitProgram() {
        System.out.println("All Data has been stored successfully");
        System.out.println("Thank you for using TextChat!");
    }
}
