import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentServer {
    private ObjectOutputStream outputToClient;
    private DataInputStream inputFromClient;
    static final String DB_URL = "jdbc:mysql://localhost/assignment";
    static final String USER = "root";
    static final String PASS = "Ibrahim_1";

    public static void main(String[] args) {
        new StudentServer();
    }
    public StudentServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started ");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            while (true) {
                Socket socket = serverSocket.accept();
                Statement stmt = conn.createStatement();

                inputFromClient = new DataInputStream(socket.getInputStream());

                int id = inputFromClient.readInt();
                int pass = inputFromClient.readInt();
                ResultSet rs = stmt.executeQuery("SELECT sname FROM students WHERE ID = " + id + " AND spassword = " + pass);
                outputToClient = new ObjectOutputStream(socket.getOutputStream());

                if(rs.next()){
                    outputToClient.writeUTF("welcome " + rs.getString("sname"));
                    Student student = new Student(rs.getString("sname"), id,pass);
                    rs = stmt.executeQuery("select CourseName,grade from StudentsGrades where StudentID = " + id);
                    while(rs.next()) student.addGrade(rs.getString("CourseName"),rs.getInt("grade"));
                    outputToClient.writeObject(student);
                    String response = inputFromClient.readUTF();
                    while(!response.equals("exit")){
                        ArrayList<Integer> courseInfo = new ArrayList<>();
                        rs = stmt.executeQuery("select avg(grade) from StudentsGrades where CourseName = \""+response + '"');
                        rs.next();
                        courseInfo.add((int)rs.getDouble(1));
                        rs = stmt.executeQuery("select max(grade) from StudentsGrades where CourseName = \"" + response + '"');
                        rs.next();
                        courseInfo.add(rs.getInt(1));
                        rs = stmt.executeQuery("select min(grade) from StudentsGrades where CourseName = \"" + response +'"');
                        rs.next();
                        courseInfo.add(rs.getInt(1));
                        outputToClient.writeObject(courseInfo);
                        response = inputFromClient.readUTF();
                    }
                }
                else{
                    outputToClient.writeUTF("wrong ID or password");
                    outputToClient.flush();}
            }
        }
        catch (IOException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                inputFromClient.close();
                outputToClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
