import Entities.Feedback;
import UseCases.FeedbackManager;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;


public class TestFeedbackManager {

    /**
     * Using the standard way of sleeping in Java to sleep for a few milliseconds.
     * @param ms milliseconds delayed
     */

    public static void wait(int ms)
    {
        try {Thread.sleep(ms);}
        catch(InterruptedException ex)
        {Thread.currentThread().interrupt();}
    }

    /**
     * This test waits 5 seconds and creates 2 different feedbacks.
     */
    @Test
    public void testFeedback() {
        FeedbackManager fb = new FeedbackManager();

        fb.createFeedback(1, "Your App is Shit");

        wait(5000);

        fb.createFeedback(5, "This app is Great!");

        ArrayList<Feedback> sorted = fb.SortedFeedbacks();

        ArrayList<Feedback> copy = new ArrayList<>(sorted);

        ArrayList<Feedback> copy2 = new ArrayList<>(sorted);

        Collections.sort(copy);

        Collections.sort(copy2, Collections.reverseOrder());

        assert(!sorted.equals(copy));

        assert(sorted.equals(copy2));





    }

}
