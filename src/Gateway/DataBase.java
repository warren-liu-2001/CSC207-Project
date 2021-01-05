package Gateway;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Date;

import Entities.*;
import Exceptions.DoesNotExistException;
import UseCases.*;
import UseCases.UserType.*;

public class DataBase<T> implements IGateway, IGatewayAdapter, IGatewayGetter, IGatewaySetter{
    public DataBase() throws SQLException, ClassNotFoundException {
    }
    Class cls = Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
            "jdbc:mysql://rds-sql-csc207g0167.cbw7a6kdya5i.us-east-2.rds.amazonaws.com:3306/mydb","admin","potatosoup");

    private ResultSet connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://rds-sql-csc207g0167.cbw7a6kdya5i.us-east-2.rds.amazonaws.com:3306/mydb","admin","potatosoup");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from userDb");
        return rs;
    }
    public ResultSet getInbox() throws SQLException, ClassNotFoundException {
        return connect();
    }

    /**
     * @param column Attribute to be set
     * @param setMe String to set it to
     * @return True if successful
     * @throws SQLException If the SQL failed
     */
    public boolean setUserAttribute(String column,String setMe) throws SQLException {
        try {PreparedStatement stm = conn.prepareStatement("UPDATE user_table SET ?=? WHERE id=?");
        stm.setString(1, "email");
        stm.setString(2, "adamp33@gmail");
        stm.executeUpdate();
        return true;}
        catch (Exception e) {return false;}
    }

    /**
     * Adds user object to the database
     * @param user User Object to be added into the Data Base
     * @return True if successful
     */
    public boolean addUser(User user){
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO userDB (userid,userType,name,email,contacts,registeredTalks,hostingTalks,password)\n" +
                    "VALUES (?,?,?,?,?,?,?,?)");
            StringBuilder contacts = new StringBuilder();
            stm.setString(1, user.getUserID());
            stm.setString(2, user.getType().toString());
            stm.setString(3, user.getName().toString());
            stm.setString(4, user.getEmail().toString());
            stm.setString(5, toCsv(user.getContacts()));
            stm.setString(6, toCsv(user.getRegisteredEvents()));
            stm.setString(8, user.getPassword().toString());
            ArrayList<UserType> hosts = new ArrayList<UserType>();
            String hostingEventsCsv = "";
            if (user.getType() == UserType.ADMIN || user.getType() == UserType.SPEAKER){
                Speaker asSpeaker = (Speaker) user;
                hostingEventsCsv = toCsv(asSpeaker.getHostingTalks());
            }if(user.getType() == UserType.ORGANIZER){
                Organizer asOrganizer = (Organizer) user;
                hostingEventsCsv = toCsv(asOrganizer.getHostingTalks());
            }
            stm.setString(7, hostingEventsCsv);
            stm.execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * adds event object to the database
     * @param event Event Object to be added to database
     * @return True if successfully pushed to DB
     */
    public boolean addEvent(Event event){
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO talkDB (eventID, eventTime, startTime,AttendeesComing,speaker,title,MaxLimit,duration,organizers)\n" +
                    "VALUES (?,?,?,?,?,?,?,?)");
            StringBuilder contacts = new StringBuilder();
            stm.setString(1, event.getEventID());
            stm.setString(2, event.getDate().toString());
            stm.setString(3, event.getStartTime().toString());
            stm.setString(4, toCsv(event.getAttendees()));
            Talk asTalk = (Talk) event;
            if(asTalk.getSpeakers()!= null){
                stm.setString(5, toCsv(asTalk.getSpeakers()));
            }else{
                stm.setString(5, "");
            }
            stm.setString(6, event.getTitle());
            stm.setInt(7, event.getMAXLimit());
            stm.setInt(8, event.getDuration());
            stm.setString(9, toCsv(event.getOrganizers()));
            stm.execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * adds message object to the database
     * @param message Message Object to be added to database
     * @return True if successfully added
     */
    public boolean addMessage(Message message){
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO messageDb (messageId,senderUserId,recipientUserId,body,time,subject)\n" +
                    "VALUES (?,?,?,?,?)");
            StringBuilder contacts = new StringBuilder();
            stm.setString(1, message.getMessageId());
            stm.setString(2, message.getFrom());
            stm.setString(3, message.getTo());
            stm.setString(4, message.getBody());
            stm.setString(5,message.getTime().toString());
            stm.setString(6,message.getSubject());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * adds room object to the database
     * @param room Room object  to be added to database
     * @return True if successfully added
     */
    public boolean addRoom(Room room){
        try{
            PreparedStatement stm = conn.prepareStatement("INSERT INTO roomDB (roomID,capacity,openTime,cloneTime) \n" +
                    "VALUES (?,?,?,?)");
            StringBuilder contacts = new StringBuilder();
            stm.setString(1, room.getRoomID());
            stm.setInt(2, room.getCapacity());
            stm.setString(3, room.getOpenTime().toString());
            stm.setString(4,room.getClosingTime().toString());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Gets all user information from database
     * @return All user information, each users information is stored in its own array list
     * @throws SQLException
     */
    @Override
    public ArrayList<ArrayList<String>> getAllUserInfo() throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM userDB");
        ResultSet rs = stm.executeQuery();
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        int columnNumber = rs.getMetaData().getColumnCount();
        while(rs.next()){
            output.add(rowToArrayList(rs));
        }
        return output;
    }

    /**
     * gets all event info from database
     * @return Array List containing the information for each event in its own array list object
     * @throws SQLException
     */
    public ArrayList<ArrayList<String>> getEventInfo(String nothing) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM talkDB");
        ResultSet rs = stm.executeQuery();
        rs.next();
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        int columnNumber = rs.getMetaData().getColumnCount();
        while(rs.next()){
            output.add(rowToArrayList(rs));
        }
        return output;
    }


    /**
     * gets all room info from database
     * @return Array List containing each rooms information as an array list
     * @throws SQLException
     */
    @Override
    public ArrayList<ArrayList<String>> getRoomInfo(String roomId) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM roomDB");
        ResultSet rs = stm.executeQuery();
        rs.next();
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        while(rs.next()){
            output.add(rowToArrayList(rs));
        }
        return output;
    }

    /**
     * gets all message information from database
     * @return Array List containing message information as an array list
     * @throws SQLException
     */
    @Override
    public ArrayList<ArrayList<String>> getMessageInfo(String UserOrRoomId) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM messageDb");
        ResultSet rs = stm.executeQuery();
        rs.next();
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        int columnNumber = rs.getMetaData().getColumnCount();
        while(rs.next()){
            output.add(rowToArrayList(rs));
        }
        return output;
    }


    /**
     * Converts an array list of strings to a csv string
     * @param arrayList Array List to be converted to csv
     * @return a string with commas seperating array list elements
     */
    public String toCsv(ArrayList<String> arrayList){
        StringBuilder output = new StringBuilder();
        for (String item : arrayList){
            output.append(item + ",");
        }
        return output.toString();
    }

    /**
     * Converts Result Set object row into an array list
     * @param rs a single row of a result set object
     * @return Array List containing the elements of that result set row
     * @throws SQLException
     */
    public ArrayList<String> rowToArrayList(ResultSet rs) throws SQLException {
        ArrayList<String> output = new ArrayList<>();
        int columnNumber = rs.getMetaData().getColumnCount();
        for(int i = 1; i < columnNumber+1; i++){
            output.add(rs.getString(i));
        }
        return output;
    }

    /**
     * Converts CSV string to ArrayList Object
     * @param csv comma seperated list as a string
     * @return Array List of that comma seperated list
     */
    public ArrayList<String> csvToArrayList(String csv){
        String[] elements = csv.split(",");
        List<String> fixedLengthList = Arrays.asList(elements);
        ArrayList<String> listOfString = new ArrayList<String>(fixedLengthList);
        return listOfString;
    }
    //############# Construct use case classes ####################
    /**
     * DataBase information on Users
     * column number Information stored
     *      0               UserID
     *      3               type
     *      4               name
     *      5               email
     *      6               contacts
     *      7               registeredTalked
     *      8               Hosting talks
     *      9               password
     * Creates User Manager
     * @return UserManager use case class
     * @throws SQLException
     */
    @Override
    public UserManager createUserManager(ArrayList<ArrayList<String>> input) throws SQLException {
        UserManager um = new UserManager();
        ArrayList<ArrayList<String>> userInfo = getAllUserInfo();
        for (ArrayList<String> item: userInfo){
            um.setUserID(item.get(0));
            UserType userType = UserType.valueOf(item.get(3));
            um.setName(item.get(4));
            um.setEmail(item.get(5));
            um.setContacts(csvToArrayList(item.get(6)));
            um.setRegisteredTalks(csvToArrayList(item.get(7)));
            um.setHostingTalks(csvToArrayList(item.get(8)));
            um.setPassword(item.get(9));
            um.createUser(userType);
        }
        return um;
    }

    /**
     * DataBase Information on rooms
     * column number        info
     *      0               RoomId
     *      1               Capacity
     *      2               openTime
     *      3               closeTime
     * Creates Room Manager
     * @param input
     * @return Room manager use case class
     */
    @Override
    public RoomManager createRoomManager(ArrayList<ArrayList<String>> input) {
        RoomManager rm = new RoomManager();
        for (ArrayList<String> item : input){
            String RoomId = item.get(0);
            int capacity = Integer.parseInt(item.get(1));
            LocalTime openTime = LocalTime.parse(item.get(2));
            LocalTime closeTime = LocalTime.parse(item.get(3));
            rm.addRoomByID(openTime.getHour(),
                    openTime.getMinute(),
                    closeTime.getHour(),
                    closeTime.getMinute(),
                    capacity,RoomId);

        }
        return rm;
    }

    /**
     *      Column Number       Information
     *      0                   EventId
     *      1                   EventDate
     *      2                   Start Time
     *      3                   Attendees coming
     *      4                   Speaker(s)
     *      5                   title
     *      6                   MaxLimit
     *      7                   Duration
     *      8                   Organizers
     *
     * Creates the Talk Manager use case Class
     * @Param Array List containing event information as an array list. ex getEventInfo()
     * returns TalkManager Use Case Class
     **/
    @Override
    public TalkManager createTalkManager(ArrayList<ArrayList<String>> input) {
        TalkManager tm = new TalkManager();
        for (ArrayList<String>item : input){
            String id = item.get(0);
            LocalTime eventTime = LocalTime.parse(item.get(2));
            LocalDate preGregory = LocalDate.parse(item.get(1));
            GregorianCalendar date = new GregorianCalendar(preGregory.getDayOfMonth(),  preGregory.getMonthValue(), preGregory.getYear());
            ArrayList<String> attendees = csvToArrayList(item.get(3));
            ArrayList<String> speakers = csvToArrayList(item.get(4));
            String title = item.get(5);
            int MaxLimit = Integer.parseInt(item.get(6));
            String EventId = item.get(0);
            int duration = Integer.parseInt(item.get(7));
            ArrayList<String> organizers = csvToArrayList(item.get(8));
        }
        return tm;
    }

    /**
     *      column number       Information
     *          0                   ID
     *          1                   Sender ID
     *          2                   Recipient Id
     *          3                   Body
     *          4                   Time
     *
     *
     * Creates the inboxManager Use Case Class
     * @param input Array List containing inbox information as an array list
     */
    @Override
    public InboxManager createInboxManager(ArrayList<ArrayList<String>> input) {
        InboxManager im = new InboxManager();
        for (ArrayList<String> item:input){
            String id = item.get(0);
            String sender = item.get(1);
            String recipient= item.get(2);
            String body = item.get(3);
            String subject = item.get(5);
            LocalTime time = LocalTime.parse(item.get(4));
            im.createMessage(id,sender,recipient,subject,body,time);
        }
        return im;
    }

    /**
     * Takes the User Manager for your use case class and sets it as the user manager on the database
     * @param um UserManager use case class
     * @throws SQLException SQL issue
     * @throws DoesNotExistException A user on the list does not exist
     */
    @Override
    public void setUserManager(UserManager um) throws SQLException, DoesNotExistException {
        for (String id: um.getUserIDList()){
            try{
                addUser(um.findUser(id));
            }catch(Exception e){
                System.out.println("User was not able to be added to database: " +id);
                e.printStackTrace();
            }
        }
    }

    /**
     * Takes the room manager for your use case class and sets it as the room manager on the database
     * @param rm Room Manager Use Case Class
     * @throws DoesNotExistException room does not exist that you are trying to set
     */
    @Override
    public void setRoomManager(RoomManager rm) throws DoesNotExistException {
        for (String id: rm.getAllRooms()){
            addRoom(rm.findRoom(id));
        }
    }

    /**
     * Takes the talk manager for your use case class and sets it as the talk manager on the database
     * @param tm Talk Manager use case class
     * @throws DoesNotExistException the event does not exist
     */
    @Override
    public void setTalkManager(TalkManager tm) throws DoesNotExistException {
        for (String id: tm.getAllTalks()){
            addEvent(tm.findTalk(id));
        }
    }

    /**
     * Takes the inbox manager in your session and sets it as the inbox manager on the database
     * @param im Inbox manager use case class
     * @param um user manager use case class
     */
    @Override
    public void setInboxManager(InboxManager im,UserManager um) {
        for (String id: um.getUserIDList()){
            for(Message message: im.getMessages(id)){
                addMessage(message);
            }
        }
    }
}
