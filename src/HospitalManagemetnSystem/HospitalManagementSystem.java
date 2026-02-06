package HospitalManagemetnSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final String username = "root";

    private static final String password = "Dinesh@1508";

    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM: ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        System.out.println();
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.println("Enter patient ID: ");
        int patient_id = scanner.nextInt();
        System.out.println("Enter doctor ID");
        int doctor_id = scanner.nextInt();
        System.out.println("Enter appointment date(yyyy-mm-dd): ");
        String appointment_Date = scanner.next();
        if(Patient.getPatientById(patient_id) && Doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(doctor_id, appointment_Date, connection)){
                String appointmentQuery = "insert into appointments(patient_id, doctor_id, appointment_date) values(?, ?, ?)";
                try{
                    PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                    ps.setInt(1, patient_id);
                    ps.setInt(2, doctor_id);
                    ps.setString(3, appointment_Date);
                    int rowsAffected = ps.executeUpdate();
                    if(rowsAffected > 0){
                        System.out.println("Appointment Booked!!");
                    }
                    else{
                        System.out.println("Failed to book appointment!!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{
                System.out.println("Doctor is not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesnt exist!!");
        }
    }

    private static boolean checkDoctorAvailability(int doctor_id, String appointment_Date, Connection connection) {
        String query = "select count(*) from appointments where doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctor_id);
            ps.setString(2, appointment_Date);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0) return true;
                else return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


}
