package Presenters;

/**
 * InfoPrompts class
 *
 * Mostly phase 1 code, with some phase 2 usage
 *
 * @version 1.1
 */

public class InfoPrompts {

    /**
     * Well...
     */

    public InfoPrompts(){}

    /**
     *
     * @param attribute enter the attribute prompted?
     */
    // split this up to more specific prompts? esp for talk info prompts
    public void attributePrompt(String attribute) {
        System.out.println("Enter the " + attribute + ":");
    }

    /**
     * Prompt the option
     * @param start starting index
     * @param end ending index
     */
    public void optionPrompt(int start, int end) {
        System.out.println("Enter your desired option (" + start + "-" + end + "): ");
    }


    /**
     * A description of an event
     */
    // next few methods used specifically for scheduling talks (menu option 42)
    public void eventDescriptionsPrompt() {
        System.out.println("A talk event has one speaker, a panel event has more than one speaker, and a party " +
                "event has no speakers");
    }

    /**
     * Which event type do u want?
     */
    public void whichEventTypePrompt() {
        System.out.println("What type of event is being scheduled? Talk being 1, Panel being 2, and Party being 3.");
    }

    /**
     * Month of prompt
     */
    public void monthPrompt() {
        System.out.println("Enter the month of the event, with January being 1, February being 2, and so on.");
    }

    /**
     *
     * @param speakerID speaker ID
     */
    public void speakerUnavailablePrompt(String speakerID) {
        System.out.println("The speaker with ID " + speakerID + " is unavailable for this event!");
    }




    /**
     * Prompts the user for an unknown amount of an attribute.
     * @param attribute The attribute the User is prompted for.
     * @param exitString The string that the User should enter to stop repeating this prompt.
     */
    public void multipleAttributePrompt(String attribute, String exitString) {
        System.out.println("Enter the " + attribute + "(\"" + exitString + "\" to stop): ");
    }
}
