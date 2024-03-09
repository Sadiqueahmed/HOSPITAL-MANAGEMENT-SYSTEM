package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    
    private Connection connection;


    public Doctor(Connection connection, Scanner scanner){
        this.connection = connection;
        
    }

    public void  viewAllDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+-------------------+------------------+");
            System.out.println("| Doctor id  | Name              | specialiazation  |");
            System.out.println("+------------+-------------------+------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialiazation = resultSet.getString("specialiazation");
                System.out.printf("| %-10s | %-17s | %-16s |\n", id, name, specialiazation );
                System.out.println("+------------+-------------------+------------------+");
            }

                }catch (SQLException e){
                e.printStackTrace();
            }
        }
        
        public boolean getDoctorById(int Id){
            String query  = "SELECT * FROM doctors WHERE ID=?" ;
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

