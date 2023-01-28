import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class DatabaseHandler extends Configs {


    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Connection dbConnection = null;
        String connectionString = "jdbc:mysql//" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    /*public static void executeMyQuery() {
Connection connection = getDbConnection();
    }*/

    public void command1() {
        String query = "SELECT AVG(" + Const.STUDENT_GRADE_AVG + ") FROM " + Const.STUDENTS_INFO_TABLE;
        System.out.print("The average grade of the school is: ");

    }

    public void command2() {
        String query = "SELECT AVG(" + Const.STUDENT_GRADE_AVG + ") FROM " + Const.STUDENTS_INFO_TABLE + " WHERE " + Const.STUDENT_GENDER + " = 'Male'";
        System.out.print("The average grade of boys is = ");
    }

    public void command3() {
        String query = "SELECT AVG(" + Const.STUDENT_GRADE_AVG + ") FROM " + Const.STUDENTS_INFO_TABLE + " WHERE " + Const.STUDENT_GENDER + " = 'Female'";
        System.out.print("The average grade of girls is = ");
    }

    public void command4() {
        String query = "SELECT " + Const.STUDENTS_INFO_TABLE + ".*, " + Const.STUDENT_HEIGHT_TABLE + ".*, " + Const.STUDENT_CAR_TABLE + ".*" +
                "FROM " + Const.STUDENTS_INFO_TABLE +
                "JOIN " + Const.STUDENT_HEIGHT_TABLE + " ON " + Const.STUDENTS_INFO_TABLE + "." + Const.STUDENT_ID + " = " + Const.STUDENT_HEIGHT_TABLE + "." + Const.STUDENT_ID +
                " JOIN " + Const.STUDENT_CAR_TABLE + " ON " + Const.STUDENTS_INFO_TABLE + "." + Const.STUDENT_ID + " = " + Const.STUDENT_CAR_TABLE + "." + Const.STUDENT_ID +
                "WHERE " + Const.STUDENTS_INFO_TABLE + "." + Const.STUDENT_GENDER + " = 'Male'" +
                "AND " + Const.STUDENT_HEIGHT_TABLE + "." + Const.STUDENT_HEIGHT + " >= 200" +
                "AND " + Const.STUDENT_CAR_TABLE + "." + Const.HAS_CAR + " = TRUE" +
                "AND " + Const.STUDENT_CAR_TABLE + "." + Const.CAR_COLOR + " = 'Purple'";
        System.out.print("The average height of boys above 2m which have purple cars is = ");
    }

    public void command5(int id) {
        Scanner scanner = new Scanner(System.in);
        String firstCircle = "SELECT " + Const.OTHER_FRIEND_ID + " AS " + Const.HIS_FRIENDS + " FROM " + Const.FRIENDSHIPS_TABLE + " WHERE friend_id=" + id;
        String secondCircle = "SELECT " + Const.OTHER_FRIEND_ID + " AS " + Const.THEIR_FRIENDS + " FROM " + Const.FRIENDSHIPS_TABLE + " WHERE friend_id = ";
        int chosenFriend = 0;
        String GetSecCycle = "";
        Connection connection = null;
        try {
            connection = getDbConnection();
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(firstCircle);
            while (resultSet.next()) // get the second cycle
            {
                chosenFriend = resultSet.getInt("MyFriends");
                GetSecCycle = firstCircle + chosenFriend;
                ResultSet secResultSet = statement.executeQuery(GetSecCycle);
                System.out.println("Friend: " + chosenFriend + " his friend: " + secResultSet.getInt("MyFriends"));
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void command6() {
        int currentId = 0;
        int CountPopularity = 0, CountNormal = 0, CountLonely = 0;
        Connection connection = null;
        try {
            connection = getDbConnection();
            String query = "SELECT COUNT(" + Const.OTHER_FRIEND_ID + ") AS " + Const.POPULARITY + " FROM " + Const.FRIENDSHIPS_TABLE + " WHERE " + Const.FRIEND_ID + " = " + currentId;
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                query = "SELECT COUNT(" + Const.OTHER_FRIEND_ID + ") AS " + Const.POPULARS + " FROM " + Const.FRIENDSHIPS_TABLE + " WHERE " + Const.FRIEND_ID + " = " + currentId;
                stmt = connection.createStatement();
                resultSet = stmt.executeQuery(query);
                if (resultSet.getInt(Const.POPULARITY) >= 2) CountPopularity++;
                else if (resultSet.getInt(Const.POPULARITY) == 1) CountNormal++;
                else CountLonely++;

                currentId++;
            }
            CountPopularity /= 10;
            CountNormal /= 10;
            CountLonely /= 10;

            System.out.println("Popular students:" + CountPopularity + "%, Normal Students:" + CountNormal + "%, Lonely Students:" + CountLonely + "%");
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void command7(int id) {
        String query = "SELECT AVG(" + Const.MY_AVERAGE + ") AS " + Const.MY_AVERAGE + " FROM " + Const.STUDENTS_INFO_TABLE + " WHERE "+Const.STUDENT_ID+" = " + id;
        Connection connection = null;
        try {
            connection = getDbConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            System.out.println("Student number " + id + " average is: " + resultSet.getFloat(Const.MY_AVERAGE));
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void initStudents() throws IOException {
        String student_info_query = "INSERT INTO " + Const.STUDENTS_INFO_TABLE + "(" + Const.STUDENT_ID + "," + Const.STUDENT_FIRSTNAME + "," + Const.STUDENT_LASTNAME
                + "," + Const.STUDENT_EMAIL + "," + Const.STUDENT_GENDER + "," + Const.STUDENT_AGE + "," + Const.STUDENT_GRADE + "," + Const.STUDENT_GRADE_AVG + ")"
                + "VALUES(?,?,?,?,?,?,?,?)";

        String car_info_query = "INSERT INTO" + Const.STUDENT_CAR_TABLE + "(" + Const.STUDENT_ID + "," + Const.HAS_CAR + "," + Const.CAR_COLOR + ")"
                + "VALUES(?,?,?)";

        String stu_height_query = "INSERT INTO" + Const.STUDENT_HEIGHT_TABLE + "(" + Const.STUDENT_ID + "," + Const.STUDENT_HEIGHT + ")"
                + "VALUES(?,?)";

        String stu_ip_query = "INSERT INTO" + Const.STUDENT_IP_TABLE + "(" + Const.STUDENT_ID + "," + Const.STUDENT_IP_ADDRESS + ")"
                + "VALUES(?,?)";

        PreparedStatement prSt_student_info = null;
        PreparedStatement prSt_car_info = null;
        PreparedStatement prSt_student_height = null;
        PreparedStatement prSt_student_ip = null;
        try {
            prSt_student_info = getDbConnection().prepareStatement(student_info_query);
            prSt_car_info = getDbConnection().prepareStatement(car_info_query);
            prSt_student_height = getDbConnection().prepareStatement(stu_height_query);
            prSt_student_ip = getDbConnection().prepareStatement(stu_ip_query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader lineReader = new BufferedReader(new FileReader(hSchoolFilePath));
        String linetext = null;
        int count = 0;
        lineReader.readLine(); // Skip header (arguments)

        while ((linetext = lineReader.readLine()) != null) {
            String[] data = linetext.split(",");
            String id = data[0];
            String first_name = data[1];
            String last_name = data[2];
            String email = data[3];
            String gender = data[4];
            String ip_address = data[5];
            String cm_height = data[6];
            String age = data[7];
            String has_car = data[8];
            String car_color = data[9];
            String grade = data[10];
            String grade_avg = data[11];
            String identification_card = data[12];

            try {
                prSt_student_info.setInt(1, Integer.parseInt(id));
                prSt_student_info.setString(2, first_name);
                prSt_student_info.setString(3, last_name);
                prSt_student_info.setString(4, email);
                prSt_student_info.setString(5, gender);
                prSt_student_info.setInt(6, Integer.parseInt(age));
                prSt_student_info.setInt(7, Integer.parseInt(grade));
                prSt_student_info.setDouble(8, Double.parseDouble(grade_avg));
                prSt_student_info.setInt(9, Integer.parseInt(identification_card));
                prSt_student_info.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                prSt_car_info.setInt(1, Integer.parseInt(id));
                prSt_car_info.setBoolean(2, Boolean.parseBoolean(has_car));
                if (Objects.equals(car_color, "")) prSt_car_info.setNull(10, Types.NULL);
                else prSt_car_info.setString(10, car_color);
                prSt_car_info.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                prSt_student_height.setInt(1, Integer.parseInt(id));
                prSt_student_height.setInt(2, Integer.parseInt(cm_height));
                prSt_student_height.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                prSt_student_ip.setInt(1, Integer.parseInt(id));
                prSt_student_ip.setString(2, ip_address);
                prSt_student_ip.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initFriendShips() throws IOException {
        String friendships_query = "INSERT INTO" + Const.FRIENDSHIPS_TABLE + "(" + Const.FRIEND_ID + "," + Const.OTHER_FRIEND_ID + ")"
                + "VALUES(?,?)";
        PreparedStatement prSt_friendships = null;
        try {
            prSt_friendships = getDbConnection().prepareStatement(friendships_query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader lineReader = new BufferedReader(new FileReader(friendShipsFilePath));
        String linetext = null;
        int count = 0;
        lineReader.readLine(); // Skip header (arguments)

        while ((linetext = lineReader.readLine()) != null) {
            String[] data = linetext.split(",");
            String friend_id = data[1];
            String other_friend_id = data[2];

            try {
                prSt_friendships.setInt(2, Integer.parseInt(friend_id));
                prSt_friendships.setInt(3, Integer.parseInt(other_friend_id));
                prSt_friendships.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
