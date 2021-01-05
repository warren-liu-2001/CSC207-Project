package Controllers;

import UseCases.*;


/**
 * Generates a use case storage facade class for the use of the presenter in terms of access.
 *
 * @version 2.0
 */

public class UseCaseFactory implements java.io.Serializable{
    private UseCaseStorage ucs;

    public UseCaseFactory(){
        RoomManager rm = new RoomManager();
        InboxManager im = new InboxManager();
        TalkManager tm = new TalkManager();
        UserManager um = new UserManager();
        AttributeChanger ac = new AttributeChanger();
        ucs = new UseCaseStorage(tm, rm, im, um, ac);
    }

    /**
     *
     * @return the generated UseCaseStorage
     */
    public UseCaseStorage getUseCaseStorage() {
        return ucs;
    }
}
