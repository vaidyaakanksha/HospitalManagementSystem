package HospitalManagementSystem;

import java.sql.*;
import java.util.*;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "14feb19oct";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            DOCTOR doctor = new DOCTOR(connection);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM.");
                System.out.println("1. Add patients: ");
                System.out.println("2. View patients: ");
                System.out.println("3. View doctor: ");
                System.out.println("4. Book Appointments: ");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatients();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDOCTOR();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter a valid choice.");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, DOCTOR doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient id: ");
        int PATIENT_ID = scanner.nextInt();
        System.out.print("Enter Doctor id: ");
        int DOCTOR_ID = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patient.getPatientsByID(PATIENT_ID) && doctor.getDOCTORByID(DOCTOR_ID)) {
            if (checkDoctorAvailability(DOCTOR_ID, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO APPOINTMENTS (PATIENT_ID, DOCTOR_ID, APPOINTMENT_DATE) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, PATIENT_ID);
                    preparedStatement.setInt(2, DOCTOR_ID);
                    preparedStatement.setString(3, appointmentDate);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Appointment booked.");
                    } else {
                        System.out.println("Failed to book appointment.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor is not available.");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist.");
        }
    }

    public static boolean checkDoctorAvailability(int DOCTOR_ID, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM APPOINTMENTS WHERE DOCTOR_ID = ? AND APPOINTMENT_DATE = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, DOCTOR_ID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
