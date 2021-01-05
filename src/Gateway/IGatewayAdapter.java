package Gateway;

import UseCases.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IGatewayAdapter {
    public String toCsv (ArrayList<String> arrayList);

    public ArrayList<String> rowToArrayList(ResultSet rs) throws SQLException;

    public ArrayList<String> csvToArrayList(String csv);


}
