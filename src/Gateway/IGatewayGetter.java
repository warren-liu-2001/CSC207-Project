package Gateway;

import UseCases.InboxManager;
import UseCases.RoomManager;
import UseCases.TalkManager;
import UseCases.UserManager;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGatewayGetter {
    public UserManager createUserManager(ArrayList<ArrayList<String>> input) throws SQLException;

    public RoomManager createRoomManager(ArrayList<ArrayList<String>> input);

    public TalkManager createTalkManager(ArrayList<ArrayList<String>> input);

    public InboxManager createInboxManager(ArrayList<ArrayList<String>> input);
}
