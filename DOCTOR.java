package HospitalManagementSystem;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DOCTOR {
    private Connection connection;

    public DOCTOR(Connection connection) {
        this.connection = connection;
    }

    public void viewDOCTOR() {
        String query = "select * from DOCTOR";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DOCTOR: ");
            System.out.println("+------------+------------------+-----------------------+");
            System.out.println("| DOCTOR ID  |  NAME            | SPECIALIZATION        |");
            System.out.println("+------------+------------------+-----------------------+");
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String NAME = resultSet.getString("NAME");
                String SPECIALIZATION = resultSet.getString("SPECIALIZATION");
                System.out.printf("|%-13s|%1-18s|%-23s|\n", ID, NAME, SPECIALIZATION);
                System.out.println("+------------+------------------+-----------------------+");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDOCTORByID(int ID) {
        String query = "SELECT * FROM DOCTOR WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
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

