package Gateway;

import Controllers.*;
import Presenters.FeedbackPresenter;
import UseCases.*;

import java.io.*;

public class FeedbackSerializer implements java.io.Serializable{

    /**
     * Read a Feedback Serial
     * @return the feedbackcontroller object involved
     */

    public FeedbackController Read() {
        try{

            FileInputStream fileIn = new FileInputStream("phase2/src/Storage/Feedback.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            FeedbackController fbc = (FeedbackController) in.readObject();
            in.close();
            fileIn.close();
            return fbc;
        } catch (FileNotFoundException e) {
            System.out.println("Serializable file wasn't found, check the path.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Object was not reconstructable, initialized a fresh system: ");
            System.out.println(e);
            FeedbackControllerFactory fbcf = new FeedbackControllerFactory();
            FeedbackController fbc = fbcf.getFeedbackController();
            return fbc;
        } catch (Exception e){
            System.err.println(e);
            System.out.println("System failed to reboot from Memory.");
        }
        // make a new one instead? not too sure serializing works
        return new FeedbackController(new FeedbackManager(), new FeedbackPresenter());
    }

    /**
     *
     * @param fbc the feedback controller to be written to
     * @return if file was successfully written
     */
    public Boolean Write(FeedbackController fbc){
        try{
//            UseCaseStorage ucs = new UseCaseStorage(tm,rm,sm,am,im);
            FileOutputStream fileOut = new FileOutputStream("phase2/src/Storage/Feedback.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(fbc);
            out.close();
            fileOut.close();
            System.out.println("Serialized current state to Feedback.ser");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



}
