package com.jorgedirkx.messageboard.dao;

import com.jorgedirkx.messageboard.model.Message;

import java.sql.Connection;
import java.util.List;

public interface MessageDao {

    List<Message> getAllMessages();

    void createMessage(Message message);

}
