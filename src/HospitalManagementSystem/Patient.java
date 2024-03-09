package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
/**
 * Patient
 */
public class Patient {

    private Connection connection;

    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter Patients name: ");
        String name = scanner.next();
        System.out.print("Enter Patient's age : ");
        int age = scanner.nextInt();
        System.out.print("Enter Patients Gender: ");
        String gender = scanner.next();

        try{
            // Write a query to insert the patient into
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set the parameter values
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            // Execute the query
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0) {
                System.out.println("New patient added successfully!");
            } else {
                System.out.println("Unable to add new patient");
                
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void  viewAllPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-------------------+-----+-----------+");
            System.out.println("| patient id | Name              | Age | Gender    |");
            System.out.println("+------------+-------------------+-----+-----------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("|%-12s|%-19s|%-5s|%-11s|\n", id, name, age, gender);
                System.out.println("+------------+-------------------+-----+-----------+");

                }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
        
        public boolean getPatientById(int Id){
            String query  = "SELECT * FROM patients WHERE ID=?";
            
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,Id);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet.next()){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException e) {
               e.printStackTrace();
           }
           return false;
        }

    }

    
        
        