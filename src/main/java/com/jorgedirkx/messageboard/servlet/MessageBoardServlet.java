package com.jorgedirkx.messageboard.servlet;

import com.jorgedirkx.messageboard.dao.MessageDaoImpl;
import javafx.beans.binding.ListExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;

@WebServlet("/messageboard")
public class MessageBoardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer =resp.getWriter();
        writer.println("<html>" +
                "<head><title>Message Board</title></head>" +
                "<body>" +
                "<form method='post'>"+
                "<label for=\"name\">Name</label>"+
                "<input id=\"name\" name=\"name\" type=\"text\" placeholder=\"enter your name\" required>"+
                "<label for=\"message\">Message</label>"+
                "<input id=\"message\" name=\"message\" type=\"text\" placeholder=\"enter your message\"required>"+
                //when the submit button is pressed, 'post' form will activate doPost method
                //and will return response to client
                "<button type=\"submit\">post your message</button>"+
                "</form>"+
                "</body>"+
                "</hmtl>");
        writer.close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer =resp.getWriter();
        writer.println("<html>" +
                "<head><title>Message Posted</title></head>" +
                "<body><h1>" +
                "Message posted by: " +
                req.getParameter("name") +
                "</h1></body>" +
                "</hmtl>");
        writer.close();
    }

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            MessageDaoImpl messageDao = new MessageDaoImpl(
                    "jdbc:mariadb://noelvaes.eu/StudentDB",
                    "student",
                    "student123"
            );

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        MessageDaoImpl messageDao = null;
        messageDao.closeConnection();
    }
}

