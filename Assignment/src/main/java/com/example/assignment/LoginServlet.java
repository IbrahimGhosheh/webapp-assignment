package com.example.assignment;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "login",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private StudentDAO studentDao;

    public void init() {
        try {
            studentDao = new StudentDAO();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("ID"));
        int password = Integer.parseInt(request.getParameter("password"));
        Student student = new Student(id,password);

        if (studentDao.validate(student)) {
            request.setAttribute("grades", studentDao.getStudentGrades(student));
            request.getRequestDispatcher("grades.jsp").forward(request, response);
        } else {
            response.sendRedirect("LoginFailed.jsp");
        }

    }

}
