package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatients(){
        System.out.println("Enter Patient Name: ");
        String NAME = scanner.next();
        System.out.println("Enter Patient Age: ");
        int AGE = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String GENDER = scanner.next();

        try{
            String query = "INSERT INTO Patients( NAME, AGE, GENDER) VALUES (?, ? ,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, NAME);
            preparedStatement.setInt(2, AGE);
            preparedStatement.setString(3, GENDER);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patients added successfully.");
            } else{
                System.out.println("Failed to add patient.");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "select * from Patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+------------------+---------+-----------------+");
            System.out.println("|         Id |  NAME            | AGE     |  GENDER         |");
            System.out.println("+------------+------------------+---------+-----------------+");
            while(resultSet.next()){
                int Id = resultSet.getInt("Id");
                String NAME = resultSet.getString("NAME");
                int AGE = resultSet.getInt("AGE");
                String GENDER = resultSet.getString("GENDER");
                System.out.printf("|%-13s|%-18s|%-9s|%-17s|\n", Id, NAME, AGE, GENDER);
            }
            System.out.println("+------------+------------------+---------+-----------------+");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientsByID(int Id) {
        String query = "SELECT * FROM Patients WHERE Id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;

            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

}
