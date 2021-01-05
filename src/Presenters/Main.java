package Presenters;

import Controllers.ControllerMenu;
import Controllers.ControllerMainLoop;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;

import java.io.FileNotFoundException;

/**
 * The pass access point for the program in Phase 1. Run this to initialize the program and load the objects
 *
 * This access point is unused for phase 2 except for special debugging purposes.
 *
 * @version 1.0
 *
 */
public class Main {
    // TimeStamp, Thurs Nov 26 @14:18 EST: Code still compiles =)
    public static void main(String[] args) throws DoesNotExistException, ObjectInvalidException {
        ControllerMainLoop pm = new ControllerMainLoop();
        ErrorMessages em = new ErrorMessages();
        InfoPrompts ip = new InfoPrompts();
        DisplayInfo di = new DisplayInfo();
        ControllerMenu cm = new ControllerMenu(pm, em, ip, di);
        pm.run(cm);
    }
}
