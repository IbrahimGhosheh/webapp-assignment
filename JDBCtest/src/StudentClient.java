import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class StudentClient  {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("enter your ID: ");
            int inputID = sc.nextInt();
            System.out.println("enter your password: ");
            int inputPass = sc.nextInt();

            Socket socket = new Socket("localhost", 8080);
            DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());

            toServer.writeInt(inputID);
            toServer.writeInt(inputPass);

            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());

            String checkCredentials = fromServer.readUTF();
            if(checkCredentials.startsWith("welcome")){
                System.out.println(checkCredentials);
                Student student =(Student) fromServer.readObject();
                HashMap<String,Integer> grades = student.getGrades();

                while(true) {
                    for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    }
                    System.out.println("enter a course name to view details or enter exit to logout:");
                    String response = sc.next();
                    toServer.writeUTF(response);
                    if(response.equals("exit")) break;
                    ArrayList<Integer> courseInfo =(ArrayList<Integer>) fromServer.readObject();
                    System.out.println("average: " + courseInfo.get(0));
                    System.out.println("max score: " + courseInfo.get(1));
                    System.out.println("min score: " + courseInfo.get(2));
                    System.out.println("enter anything to go back:");
                    sc.next();
                }
            }
            else System.out.println(checkCredentials);
        } catch (IOException | ClassNotFoundException ex ) {
            ex.printStackTrace();
        }
    }
}


