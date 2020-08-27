package by.java.controller;


import by.java.entity.User;
import by.java.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/banks")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 124578466L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ServiceFactory factory = ServiceFactory.getInstance();
        String command = request.getParameter("command");
        List<User> richestUsers;
        Integer sum;
        try {
            if (command.equals("richest")) {
                richestUsers = factory.getUserService().findRichestUser().get();
                request.setAttribute("richestUsers", richestUsers);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/richestUser.jsp");
                dispatcher.forward(request, response);
            } else if (command.equals("sum")) {
                sum = factory.getAccountService().findSumOfAllAccounts();
                request.setAttribute("sumOfAccounts", sum);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/accountsSum.jsp");
                dispatcher.forward(request, response);
            } else {
                throw new Exception("There is no such command");
            }

        } catch (Exception e) {
            response.sendRedirect("index.jsp");
        }
    }
}
