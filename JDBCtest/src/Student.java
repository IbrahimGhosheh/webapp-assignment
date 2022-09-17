import java.util.HashMap;

public class Student implements java.io.Serializable {
    private final String name;
    private final int ID;
    private int password;
    private HashMap<String,Integer> grades;

    public Student(String name, int ID,int pass) {
        this.name = name;
        this.ID = ID;
        password = pass;
        grades = new HashMap<>();
    }

    public int getID() {
        return ID;
    }

    public HashMap<String,Integer> getGrades() {
        return grades;
    }

    public String getName() {
        return name;
    }

    public void addGrade(String course, int grade){
        grades.put(course,grade);
    }

    public int getPassword() {
        return  password;
    }
}