package am.vtc.userchat.service.impl;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.Message;
import am.vtc.userchat.repo.MessageRepo;
import am.vtc.userchat.repo.impl.MessageRepoSql;
import am.vtc.userchat.service.MessageService;

import java.sql.SQLException;
import java.util.List;

public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

    public MessageServiceImpl() {
        messageRepo = new MessageRepoSql();
    }

    @Override
    public Message add(Message message) throws DatabaseException {
        try {
            return messageRepo.insert(message);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }

    @Override
    public List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException {
        try {
            return messageRepo.fetchAll(senderId, receiverId);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }
}
