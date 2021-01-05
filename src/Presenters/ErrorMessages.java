package Presenters;

import Exceptions.DoesNotExistException;

import java.io.FileNotFoundException;

/**
 * Phase 1 Command Line inteface class. This is now unused
 */

public class ErrorMessages {

    public ErrorMessages(){}


    public void invalidInput() {
        System.out.println("Invalid input. Please try again.");

    }

    // could expand to be more specific based on action
    // ex change info failed, couldn't find entity (room/user/etc.)
    public void actionFailed() {
        System.out.println("Failed to perform action. Please try again.");
    }

    public void loginFailed() {
        System.out.println("Your login information was incorrect. Please try again.");
    }

    public void fileDNE(FileNotFoundException e) {
        System.out.println("Error occured. Please Check configuration files and filepath");
        e.printStackTrace();
    }

    public void loadFailed(DoesNotExistException e) {
        System.out.println("Error occured. Error in code. Please Debug Does Not exist");
        e.printStackTrace();
    }

    // just prints the stack trace, idk maybe we want to change how it's printed
    public void printError(Exception e) {
        e.printStackTrace();
    }
}