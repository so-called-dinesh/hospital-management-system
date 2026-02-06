package HospitalManagemetnSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private static Connection connection;
    private static Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public static void addPatient(){
        System.out.print("Enter patient Name: ");
        String name = scanner.next();
        System.out.println("Enter patient age: ");
        int age = scanner.nextInt();
        System.out.print("Enter patient gender: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patient(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient added successfully!!");
            }
            else{
                System.out.println("Failed to add patient!!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void viewPatient() throws SQLException {
        String query = "select * from patient";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patient: ");
            System.out.println("+------------+-------------------+-----------+---------------+");
            System.out.println("| Patient Id | Name              | Age       | Gender        |");
            System.out.println("+------------+-------------------+-----------+---------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("|%-12s|%-19s|%-11s|%-15s|\n", id, name, age, gender);
                System.out.println("+------------+-------------------+-----------+---------------+");
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getPatientById(int id){
        String query = "select * from patient where id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}

