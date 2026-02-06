package HospitalManagemetnSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private static Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
    }

    public static void viewDoctors() throws SQLException {
        String query = "select * from doctor";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Doctor: ");
            System.out.println("+------------+-------------------+-----------------+");
            System.out.println("| Doctor Id  | Name              | Specialization  |");
            System.out.println("+------------+-------------------+-----------------+");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.printf("|%-12s|%-19s|%-17s|\n", id,name, specialization);
                System.out.println("+------------+-------------------+-----------------+");
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getDoctorById(int id){
        String query = "select * from doctor where id = ?";
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
