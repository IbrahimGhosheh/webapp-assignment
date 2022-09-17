package com.example.assignment;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "course",urlPatterns = "/course")
public class CourseServlet extends HttpServlet {

    private StudentDAO studentDao;

    public void init() {
        try {
            studentDao = new StudentDAO();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String course= request.getParameter("courseName");
        ArrayList<Integer> info = studentDao.getCourseInfo(course);
        getServletContext().setAttribute("course",course);
        getServletContext().setAttribute("avg",info.get(0));
        getServletContext().setAttribute("max",info.get(1));
        getServletContext().setAttribute("min",info.get(2));
        request.getRequestDispatcher("CourseInfo.jsp").forward(request, response);
    }
}
