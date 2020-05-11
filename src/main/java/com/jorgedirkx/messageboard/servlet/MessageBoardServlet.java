package com.jorgedirkx.messageboard.servlet;

import com.jorgedirkx.messageboard.dao.MessageDao;
import com.jorgedirkx.messageboard.dao.MessageDaoImpl;
import com.jorgedirkx.messageboard.model.Message;
import javafx.beans.binding.ListExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

@WebServlet("/")
public class MessageBoardServlet extends HttpServlet {

    private MessageDao messageDao;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        messageDao = new MessageDaoImpl("jdbc:mariadb://noelvaes.eu/StudentDB",
                "student","student123" );

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try(PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>guestbook servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Messages</h1>");
            List<Message> messages = messageDao.getAllMessages();
            for (Message message:
                    messages) {
                out.println("<p>"+ message.getName() +"</p>");
                out.println("<p>"+ message.getMessage() +"</p>");
                out.println("<p>"+ message.getDate() +"</p>");
            }
            out.println("<form method='post' action=''>");
            out.println("<p>please enter name:</p>\n" +
                    "name: <input type=\"text\" name=\"name\">\n" +
                    "message: <input type=\"textarea\" name=\"message\">\n" +
                    "    <input type=\"submit\" value=\"okidoki!\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(IOException ex){
            System.out.println("exception during writing procedure");
            throw ex;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        String name = req.getParameter("name");
        String message = req.getParameter("message");
        messageDao.createMessage(new Message(dateTime,name,message));
        resp.sendRedirect("");
    }

    @Override
    public void destroy() {
        ((MessageDaoImpl)messageDao).closeConnection();
    }
}