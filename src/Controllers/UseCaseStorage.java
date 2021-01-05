package Controllers;

import Entities.Talk;
import UseCases.*;

/**
 * Class that stores all use cases and allows for serialization of the use cases needed to maintain data.
 *
 * @version 2.0
 */

public class UseCaseStorage implements java.io.Serializable {
    private TalkManager tm;
    private RoomManager rm;
    private InboxManager im;
    private UserManager um;
    private AttributeChanger ac;

    /**
     * Stores the provided use case classes
     *
     * @param tm The TalkManager to store
     * @param rm The RoomManager to store
     * @param im The InboxManager to store
     * @param um The UserManager to store
     * @param ac The AttributeChanger to store
     */
    public UseCaseStorage(TalkManager tm, RoomManager rm, InboxManager im, UserManager um, AttributeChanger ac){
        if (tm == null) {
            tm = new TalkManager();
        }
        if (rm == null) {
            rm = new RoomManager();
        }
        if (im == null) {
            im = new InboxManager();
        }
        if (um == null) {
            um = new UserManager();
        }
        if (ac == null) {
            ac = new AttributeChanger();
        }
        this.tm = tm;
        this.rm = rm;
        this.im = im;
        this.um = um;
        this.ac = ac;
    }


    /**
     *
     * @return the stored talk manager
     */
    public TalkManager getTalkManager(){
        return tm;
    }

    /**
     *
     * @return the stored inboxmanager
     */
    public InboxManager getInboxManager(){
        return im;
    }


    /**
     *
     * @return the stored roommanager use case
     */
    public RoomManager getRoomManager(){
        return rm;
    }

    /**
     *
     * @return the stored usermanager
     */
    public UserManager getUserManager() {
        return um;
    }

    /**
     *
     * @return the stored AttributeChanger
     */

    public AttributeChanger getAttributeChanger() {
        return ac;
    }
}
