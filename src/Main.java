import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner  = new Scanner(System.in);
        DatabaseHandler databaseHandler = new DatabaseHandler();
        int command;
        //------------------------------------------------------------------------------------------------------
        //Initialize DB if isn't initialized:
        try {
            databaseHandler.initStudents();
            databaseHandler.initFriendShips();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //------------------------------------------------------------------------------------------------------
        System.out.println((char) 27 + "[32m"+"                                                                         Welcome to School Database!");
        do{
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |");
            System.out.println("|                                                                                  [Menu]                                                                                 |");
            System.out.println("| 1 - The average school grades                                                                                                                                           |");
            System.out.println("| 2 - The average grade of male students'                                                                                                                                 |");
            System.out.println("| 3 - The average grade of female students'                                                                                                                               |");
            System.out.println("| 4 - The average height of the students is two meters or more, who have a purple car                                                                                     |");
            System.out.println("| 5 - The id of a student and all the ids of his friends, and all their friends (first and second circle)                                                                 |");
            System.out.println("| 6 - The percentage of popular students, the percentage of regular students, and the percentage of single students.                                                      |\n|     A popular student is a student who has two or more friends, a regular student is a student who has one friend, and a lonely student is a student who has no friends |");
            System.out.println("| 7 - The student's ID card and grade point average                                                                                                                       |");
            System.out.println("| 8 - Exit                                                                                                                                                                |");
            System.out.println("|_________________________________________________________________________________________________________________________________________________________________________|");
            System.out.print("command--->");
            command = scanner.nextInt();
            switch (command){
                case 1:
                    databaseHandler.command1();
                    break;
                case 2:
                    databaseHandler.command2();
                    break;
                case 3:
                    databaseHandler.command3();
                    break;
                case 4:
                    databaseHandler.command4();
                    break;
                case 5:
                    System.out.print("Enter id:");
                    databaseHandler.command5(scanner.nextInt());
                    break;
                case 6:
                    databaseHandler.command6();
                    break;
                case 7:
                    System.out.print("Enter id:");
                    databaseHandler.command7(scanner.nextInt());
                    break;
                case 8:
                    System.out.println("Thank you for using the School Database!");
                    break;
                default:
                    System.out.println("Incorrect input!");
                    break;
            }
        }while(command != 8);
    }
}