package UseCases;

import Entities.*;
import Exceptions.DoesNotExistException;
import Exceptions.ObjectInvalidException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * UserManager class, which manages users storage
 *
 * @version 2.0
 */
public class UserManager implements java.io.Serializable{
    private ArrayList<User> userList;
    private ArrayList<String> listIDs;
    private String name= "";
    private String email = "";
    private String password = "";
    private String userID = ""; //"Attendee " + (new UUID(10,10)).toString();
    private ArrayList<String> contacts = new ArrayList<String>();
    private ArrayList<String> registeredTalks = new ArrayList<String>();
    private ArrayList<String> hostingTalks;

    /**
     * Constructor for UserManager
     */
    public UserManager() {
        this.userList = new ArrayList<User>();
        this.listIDs = new ArrayList<String>();
    }

    /**
     *
     * @param email the email of the user
     * @return a user in the system
     * @throws ObjectInvalidException if Object format provided is invalid
     * @throws DoesNotExistException if the Object Does Not Exist
     */
    public User findUserWithEmail(String email) throws ObjectInvalidException, DoesNotExistException {
        for (User user: this.userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new DoesNotExistException("This user email does not exist!");
    }

    /**
     * Gets a list of all users
     * @return returns Array of all user objects
     */
    public ArrayList<User> getUserList(){
        return this.userList;
    }
    /**
     * Gets a list of all users of UserType type
     * @param type A specific User Type as specified as the Enums in UserType
     * @return an array list of all users of that type
     */
    public ArrayList<User> getUserList(UserType type){
        ArrayList<User> outputArray = new ArrayList<User>();
        for (User user : this.userList){
            if (user.getType() == type){
                outputArray.add(user);
            };
        }
        return outputArray;
    }
    /**
     * Gets a list of all user names
     * @return returns Array of all user names
     */
    public ArrayList<String> getUserNames(){
        ArrayList<String> ls = new ArrayList<>();
        for(User u: this.getUserList()){
            ls.add(u.getName());
        }
        return ls;
    }
    /**
     * Gets a list of all user emails
     * @return returns Array of all user emails
     */
    public ArrayList<String> getUserEmails(){
        ArrayList<String> ls = new ArrayList<>();
        for(User u: this.getUserList()){
            ls.add(u.getEmail());
        }
        return ls;
    }

    /**
     * Gets a list of all user ID's
     * @return an ArrayList of all user ID's
     */
    public ArrayList<String> getUserIDList(){
        ArrayList<String> idList = new ArrayList<>();
        for (User user : getUserList()) {
            idList.add(user.getUserID());
        }
        return idList;
    }

    /**
     * Gets a list of all user names of UserType type
     * @param type type of users to get
     * @return returns Array of all user names of specified type
     */
    public ArrayList<String> getUserNames(UserType type){
        ArrayList<String> ls = new ArrayList<>();
        for(User u: this.getUserList(type)){
            ls.add(u.getName());
        }
        return ls;
    }
    /**
     * Gets a list of all user emails of UserType type
     * @param type type of users to get
     * @return returns Array of all user emails
     */
    public ArrayList<String> getUserEmails(UserType type){
        ArrayList<String> ls = new ArrayList<>();
        for(User u: this.getUserList(type)){
            ls.add(u.getEmail());
        }
        return ls;
    }

    /**
     * Gets a list of all user ID's of UserType type
     * @param type type of users to get
     * @return an ArrayList of all user ID's of specified type
     */
    public ArrayList<String> getUserIDList(UserType type){
        ArrayList<String> idList = new ArrayList<>();
        for (User user : this.getUserList(type)) {
            idList.add(user.getUserID());
        }
        return idList;
    }

    /**
     *
     * @param userList userlist
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    /**
     *
     * @param listIDs set the list of IDs
     */

    public void setListIDs(ArrayList<String> listIDs) {
        this.listIDs = listIDs;
    }

    /**
     *
     * @param name name of the user to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param email email of the user to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param password of the user to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *
     * @param userID the ID of the user to be set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @param contacts set the contacts of the user
     */
    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @param registeredTalks set the registered talks if the user
     */
    public void setRegisteredTalks(ArrayList<String> registeredTalks) {
        this.registeredTalks = registeredTalks;
    }

    /**
     *
     * @param hostingTalks set the hosting talks of the user
     */
    public void setHostingTalks(ArrayList<String> hostingTalks) {
        this.hostingTalks = hostingTalks;
    }

    /**
     * @param type the type of User
     * @return the specified user from UserType
     */
    public User createUser(UserType type){
        if(userID.equals("")) {
            switch (type) {
                case ATTENDEE:
                    userID = "Attendee" + UUID.randomUUID();
                    break;
                case SPEAKER:
                    userID = "Speaker" + UUID.randomUUID();
                    break;
                case ORGANIZER:
                    userID = "Organizer" + UUID.randomUUID();
                    break;
                case ADMIN:
                    userID = "Admin" + UUID.randomUUID();
                    break;
            }
        }
        if (type == UserType.ORGANIZER){
            Organizer c = new Organizer(email, userID, name);
            c.setPassword(password);
            c.setContacts(contacts);
            c.setHostingTalks(hostingTalks);
            this.resetAttributes();
            userList.add(c);
            return c;
        }
        else if (type == UserType.SPEAKER){
            Speaker d = new Speaker(email, userID, name);
            d.setPassword(password);
            d.setContacts(contacts);
            d.setHostingTalks(hostingTalks);
            this.resetAttributes();
            userList.add(d);
            return d;
        }
        else if (type == UserType.ADMIN) {
            Admin e = new Admin(email, userID, name);
            e.setPassword(password);
            e.setContacts(contacts);
            e.setHostingTalks(hostingTalks);
            this.resetAttributes();
            userList.add(e);
            return e;
        } else { // type = ATTENDEE
            Attendee b = new Attendee(email, userID, name);
            b.setPassword(password);
            b.setContacts(contacts);
            b.setRegisteredTalks(registeredTalks);
            this.resetAttributes();
            userList.add(b);
            return b;
        }

    }

    /**
     * Resets instance attributes after creating any User
     */
    private void resetAttributes(){
        this.name= "";
        this.email = "";
        this.password = "";
        this.userID = ""; //"Attendee " + (new UUID(10,10)).toString();
        this.contacts = new ArrayList<String>();
        this.registeredTalks = new ArrayList<String>();
        this.hostingTalks = new ArrayList<String>();
    }

    /**
     * Find the User with associated ID or email
     * @param userID ID or email of the User to find
     * @return The User found, if any
     * @throws DoesNotExistException If no User was found
     */
    public User findUser(String userID) throws DoesNotExistException {
        for (User user: this.userList) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }

        for (User user: this.userList) {
            if (user.getEmail().equals(userID)) {
                return user;
            }
        }

        throw new DoesNotExistException("This user ID does not exist!");
    }

    /**
     * Find the Speaker with associated ID or email
     * @param userID ID or email of the User to find
     * @return The Speaker found, if any
     * @throws DoesNotExistException If no Speaker was found
     */
    public Speaker findSpeaker(String userID) throws DoesNotExistException {
        User user = this.findUser(userID);

        // check if speaker, and if it is, return it
        if (user.isSpeaker()) {
            return (Speaker) user;
        }
        throw new DoesNotExistException("This user ID does not belong to a speaker!");
    }

    /**
     *
     * @return a string representation of the users within the system, returning a list of all users.
     */
    @Override
    public String toString() {
        String userList = "User List:\n";
        for (User user : this.getUserList()) {
            userList += user.toString() + "\n";
        }
        return userList;
    }
}

