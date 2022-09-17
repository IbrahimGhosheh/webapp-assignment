package com.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class LoginController {
	
	StudentDAO dao= new StudentDAO();
	
	@RequestMapping("/login")
	public ModelAndView loginUser(HttpServletRequest request,HttpServletResponse response) {
		
		int id= Integer.parseInt(request.getParameter("ID"));
		int pass = Integer.parseInt(request.getParameter("password"));
		
		String result=null;
		Student student = new Student(id,pass);
		
		ModelAndView mv= new ModelAndView();
		if(dao.validate(student)) {
			mv.setViewName("grades.jsp");
			mv.addObject("grades",dao.getStudentGrades(student));
		}else {
			mv.setViewName("LoginFailed.jsp");
		}
		
		return mv;
		
	}

	
	@RequestMapping("/course")
	public ModelAndView courseInfo(HttpServletRequest request,HttpServletResponse response) {

		String course= request.getParameter("courseName");
		ArrayList<Integer> info = dao.getCourseInfo(course);

		ModelAndView mv= new ModelAndView();
		mv.addObject("course",course);
		mv.addObject("avg",info.get(0));
		mv.addObject("max",info.get(1));
		mv.addObject("min",info.get(2));
		mv.setViewName("CourseInfo.jsp");
		return mv;
	}
}
