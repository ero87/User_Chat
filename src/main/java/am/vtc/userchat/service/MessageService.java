package am.vtc.userchat.service;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.Message;

import java.util.List;

public interface MessageService {

    Message add(Message message) throws DatabaseException;

    List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException;
}
