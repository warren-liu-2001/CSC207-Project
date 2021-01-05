package Gateway;

import Exceptions.DoesNotExistException;
import UseCases.InboxManager;
import UseCases.RoomManager;
import UseCases.TalkManager;
import UseCases.UserManager;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGatewaySetter {
    public void setUserManager(UserManager um) throws SQLException, DoesNotExistException;

    public void setRoomManager(RoomManager rm) throws DoesNotExistException;

    public void setTalkManager(TalkManager tm) throws DoesNotExistException;

    public void setInboxManager(InboxManager im,UserManager um);
}