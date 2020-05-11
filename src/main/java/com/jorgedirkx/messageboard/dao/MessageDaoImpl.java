package com.jorgedirkx.messageboard.dao;

import com.jorgedirkx.messageboard.model.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private String url;
    private String user;
    private String password;
    private Connection connection;
    private final String createStatement = "INSERT INTO GuestBook(Date,Name,Message) VALUES (?,?,?)";

    public MessageDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        connection = getConnection();
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM GuestBook")) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //read out result
                LocalDateTime dateTime = result.getTimestamp("date").toLocalDateTime();
                String name = result.getString("name");
                String message = result.getString("message");
                Message messageObject = new Message(dateTime, name, message);
                messages.add(messageObject);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return messages;
    }

    @Override
    public void createMessage(Message message) {
        try (PreparedStatement statement = connection.prepareStatement(createStatement)) {
            statement.setTimestamp(1, Timestamp.valueOf(message.getDate()));
            statement.setString(2, message.getName());
            statement.setString(3, message.getMessage());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Er is iets foutgegaan tijdens het aanmaken van een connection");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
