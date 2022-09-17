package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentDAO {
	private final String DB_URL = "jdbc:mysql://localhost:3306/assignment";
	private final String USER = "root";
	private final String PASS = "Ibrahim_1";
	private Connection connection;

	public StudentDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			connection = DriverManager
					.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean validate(Student student) {
		boolean status = false;

		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from students where ID = ? and spassword = ?");
			preparedStatement.setInt(1, student.getID());
			preparedStatement.setInt(2, student.getPassword());

			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return status;
	}
	public HashMap<String,Integer> getStudentGrades(Student student){

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs= stmt.executeQuery("select CourseName,grade from StudentsGrades where StudentID = " + student.getID());

			while(rs.next()) student.addGrade(rs.getString("CourseName"),rs.getInt("grade"));


		} catch (SQLException e) {
			printSQLException(e);
		}
		return student.getGrades();
	}
	public ArrayList<Integer> getCourseInfo(String course) {
		ArrayList<Integer> courseInfo = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select avg(grade) from StudentsGrades where CourseName = \"" + course + '"');
			rs.next();
			courseInfo.add((int) rs.getDouble(1));
			rs = stmt.executeQuery("select max(grade) from StudentsGrades where CourseName = \"" + course + '"');
			rs.next();
			courseInfo.add(rs.getInt(1));
			rs = stmt.executeQuery("select min(grade) from StudentsGrades where CourseName = \"" + course + '"');
			rs.next();
			courseInfo.add(rs.getInt(1));
			return courseInfo;
		}
		catch (SQLException e) {
			printSQLException(e);
		}
		return courseInfo;
	}


	private void printSQLException(SQLException ex) {
		for (Throwable e: ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}


}
