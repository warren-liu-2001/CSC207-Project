package Gateway;

import Entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IGateway {

    public ArrayList<ArrayList<String>> getAllUserInfo() throws SQLException;

    public ArrayList<ArrayList<String>> getEventInfo(String eventId) throws SQLException;

    public ArrayList<ArrayList<String>> getRoomInfo(String roomID) throws SQLException;

    public ArrayList<ArrayList<String>> getMessageInfo(String id) throws SQLException;

    public String toCsv (ArrayList<String> arrayList);

    public ArrayList<String> rowToArrayList(ResultSet rs) throws SQLException;

    public ArrayList<String> csvToArrayList(String csv);

}
