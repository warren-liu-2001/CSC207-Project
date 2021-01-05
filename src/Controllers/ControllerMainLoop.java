package Controllers;

import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;
import Gateway.Serializer;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Presenters.DisplayInfo;
import Presenters.ErrorMessages;
import Presenters.InfoPrompts;
import Presenters.Menus;

/**
 * PresenterMain Class
 *
 * Deprecated as of Phase 2
 *
 * @author Warren Liu
 *
 * @version 1.0
 */
public class ControllerMainLoop {

    private ErrorMessages em;
    private InfoPrompts ip;
    private Menus menus;
    private DisplayInfo di;

    public ControllerMainLoop() {
        em = new ErrorMessages();
        ip = new InfoPrompts();
        menus = new Menus();
        di = new DisplayInfo();
    }

    /**
     * Run method
     * @param controllerMainRun helper to controllermain
     * @throws DoesNotExistException if object invalid
     * @throws ObjectInvalidException if object DNE
     */

    public void run(ControllerMenu controllerMainRun) throws DoesNotExistException, ObjectInvalidException {

        Scanner input = new Scanner(System.in);

        // Useful Variables:
        // is active: is the system active?
        // isLoggedIn: is a user logged in?

        // Generate all needed controller first
        Serializer gate = new Serializer();
        UseCaseStorage ucs = gate.Read();

//        UseCaseFactory ucf = new UseCaseFactory();
//        UseCaseStorage ucs = ucf.getUseCaseStorage();
        UserAccountSystem uas = new UserAccountSystem(ucs);

        // Generate the needed use cases
        // Done

        // Store them in Use Case Storage


        // Initialize the Serialized .csv

        // AppGateway ag = new AppGateway(ucs);

        menus.onStartup();

        // System is Now initialized. We must now begin the main Loop

        boolean isActive = true;
        boolean isloggedIn = false;

        while (isActive) {

            boolean notLoggedIn = true;

            menus.printStartupMenu();

            Boolean invalidinput1 = true;
            Scanner scanner_bruh = new Scanner(System.in);
            while (invalidinput1) {
                ip.optionPrompt(0, 1);
                try {
                    int inputed = scanner_bruh.nextInt();
                    if (inputed == 1) {
                        invalidinput1 = false;
                    } else if (inputed == 0) {
                        isActive = false;
                        invalidinput1 = false;
                    } else {
                        em.invalidInput();
                    }
                } catch (InputMismatchException e) {
                    em.invalidInput();
                    scanner_bruh.nextLine(); // clear the scanner
                }
            }


            if (isActive) {

                while (notLoggedIn) {

                    menus.printLoginMenu();

                    boolean chosen = false;
                    boolean loginmode = false;
                    boolean exitcondition = false;

                    while (!chosen) {

                        Scanner input2 = new Scanner(System.in);

                        try {
                            int choice = input2.nextInt();

                            if (choice == 1) {
                                loginmode = true;
                                chosen = true;
                            } else if (choice == 2) {
                                loginmode = false;
                                chosen = true;
                            } else if (choice == 0) {
                                exitcondition = true;
                                chosen = true;
                            } else {
                                em.invalidInput();
                            }
                        } catch (InputMismatchException e) {
                            em.invalidInput();
                        }

                    }


                    // Find a Way for them to exit the login death bubble
                    if (exitcondition) {notLoggedIn = false;}

                    if (loginmode) {
                        Scanner input3 = new Scanner(System.in);

                        menus.onEnterLoginMode();

                        ip.attributePrompt("name");
                        String name1 = input3.nextLine();
                        ip.attributePrompt("email");
                        String email1 = input3.nextLine();
                        ip.attributePrompt("password");
                        String password1 = input3.nextLine();

                        isloggedIn = controllerMainRun.getLogin(uas, name1, email1, password1);


                    } else if (!exitcondition){
                        Scanner input4 = new Scanner(System.in);

                        menus.oneEnterRegistrationMode();

                        ip.attributePrompt("name");
                        String name = input4.nextLine();
                        ip.attributePrompt("email");
                        String email = input4.nextLine();
                        ip.attributePrompt("password");
                        String password = input4.nextLine();


                        boolean typeinput = false;
                        while (!typeinput) {

                            menus.printAccountCreationMenu();

                            int choice = input.nextInt();

                            if (choice == 1) {
                                uas.getUserAccount().registerAttendee(email, name, password);
                                typeinput = true;
                            } else if (choice == 2) {
                                uas.getUserAccount().registerOrganizer(email, name, password);
                                typeinput = true;
                            } else {
                                em.invalidInput();
                            }
                        }
                        di.actionSuccessful("account creation");
                        isloggedIn = controllerMainRun.getLogin(uas, name, email, password);
                    }


                    if (isloggedIn) {
                        notLoggedIn = false;
                        isloggedIn = true;
                    } else {
                        em.loginFailed();
                    }
                }




                while (isloggedIn) {


                    // After logging in, the client will be prompted to enter an integer corresponding to the

                    menus.onLogin();

                    // Other Functions Here

                    // Display a menu based on whether the user is a Speaker, Organizer, or Attendee

                    // For all:
                    // set/get email, password, name, add/remove friends, logout
                    // view entire schedule
                    // message a specific attendee/organizer/speaker, view inbox
                    menus.printUserMenu();

                    if (uas.getUserAccount().isSpeaker()) {
                        // message an attendee, message all attendees at one or multiple of their talks
                        // view the entire schedule, view their talks
                        menus.printSpeakerMenu();

                    } else if (uas.getUserAccount().isOrganizer()) {
                        // create rooms, speakers
                        // add a speaker to a room for an hour
                        // message all speakers, message all attendees
                        //
                        menus.printOrganizerMenu();
                    } else if (uas.getUserAccount().isAttendee()) {
                        // view talks that have capacity
                        // sign up/cancel spot in talks
                        menus.printAttendeeMenu();
                    }

                    // Decide what to do based on the option picked
                    boolean valid  = false;
                    int selection = 0;
                    do {
                        try {
                            ip.optionPrompt(0, 63);
                            selection = input.nextInt();
                        } catch(Exception ex){
                            em.invalidInput();
                            continue;
                        }
                        if (uas.getUserAccount().isSpeaker()) {
                            valid = (1 <= selection && selection < 14) || (30 <= selection && selection <= 32);
                        } else if (uas.getUserAccount().isOrganizer()) {
                            valid = (1 <= selection && selection < 14) || (40 <= selection && selection <= 44);
                        } else if (uas.getUserAccount().isAttendee()) {
                            valid = (1 <= selection && selection < 14) || (60 <= selection && selection <= 63);
                        } else {
                            em.invalidInput();
                        }
                    } while (!valid);

                    // Clear scanner for new input
                    input.nextLine();

                    isloggedIn = controllerMainRun.userMenu(input, ucs, uas, isloggedIn, selection);
                    controllerMainRun.speakerMenu(input, ucs, uas, selection);
                    controllerMainRun.organizerMenu(input, ucs, uas, selection);
                    controllerMainRun.attendeeMenu(input, ucs, uas, selection);


                    // After logging out, the user can close the program.

                    // At this moment in time, as soon as they log in, the user will be logged out.
//                    isloggedIn = false;
                }

                menus.onLogout();
            }

        }

        // Now, to close the system, we need to do a few things.

        // First, we need to recover every piece of data and write it to the .ser file
        gate.Write(ucs);

        // Now we can end the program.
        menus.onExitProgram();
    }
}
