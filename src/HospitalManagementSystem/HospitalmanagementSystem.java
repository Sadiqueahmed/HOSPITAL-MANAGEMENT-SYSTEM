package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class HospitalmanagementSystem {
    
    private static final String url = "jdbc:mysql://localhost:3306/hospital"; // Enter your database URL here. Example: 
    //jdbc:mysql://localhost:3306/hospital

    //dbc://username:password@localhost/databasename
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        
        try{
            Class.forName("com.mysql.jdbc.Driver"); 
            
        }catch(ClassNotFoundException ex){
            e.printStackTrace();
        }

         Scanner scanner = new Scanner(System.in);

        try {
            Connection connection =  DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection,scanner);

            while (true) {
                        System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                        System.out.println("1. Add a new patient. ");
                        System.out.println("2. View for a patient. ");
                        System.out.println("3. Display all doctors. ");
                        System.out.println("4. Book  an appointment. ");
                        System.out.println("5. Log out.");
                        System.out.println("Enter your choice : ");
                        int choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                            //add patient
                                patient.addPatient();
                                System.out.println();
                                break;
                            case 2:
                            //view patient
                                patient.viewAllPatients();
                                System.out.println();
                                break;
                            case 3:
                            //display doctors
                                doctor.viewAllDoctors();
                                System.out.println();
                                break;
                            case 4: 
                            //book appointment
                                bookAppointment(patient, doctor, connection, scanner);
                                System.out.println( "Appointment booked successfully." );
                                System.out.println();
                                break;
                            case 5: 
                                System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM !!");
                                return;
                            default: 
                            System.out.println("Invalid Choice!");
                            break;
                        }
                    }



                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
        }

        public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
            System.out.print("Enter Patient Id: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor's Id: ");
            int docId=scanner.nextInt();
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String appointmentDate = scanner.next();

            if(patient.getPatientById(patientId) && doctor.getDoctorById(docId)){
                if (checkDoctorAvailability(docId, appointmentDate, connection)) {
                   String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?) ";
                   try{
                        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                        preparedStatement.setInt(1, patientId);
                        preparedStatement.setInt(2, docId);
                        preparedStatement.setString(3,appointmentDate);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected>0) {
                            System.out.println("Appointment booked successfully");
                        } else {
                            System.out.println("Error in booking the appointment.");
                            
                        }

                   }catch(SQLException e){
                        e.printStackTrace();
                   }
                }else{
                    System.out.println("Doctor  is not available on the given Date and Time.");
                }
        }else{
            System.out.println("Either the doctor or patient doesn't exist !! ");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection){

        //(*) indicates the number of rows  affected by a SQL statement. If it returns (*1), then there was one row affected.
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointments_date = ?" ;
        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt( 1);
                if(count == 0 ){
                    return true;
                
                }else{
                    return false;
                }
            }    
        }catch (SQLException e){
        e.printStackTrace();
        }
        return false;
    }
}  
   

