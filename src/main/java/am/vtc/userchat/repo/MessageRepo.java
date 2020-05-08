package am.vtc.userchat.repo;

import am.vtc.userchat.model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageRepo {

    Message insert(Message message) throws SQLException;

    List<Message> fetchAll(int senderId, int receiverId) throws SQLException;

}
