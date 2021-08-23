package Model;

import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDAO {

    /**
     * Executes SQL queries and throws SQL, Class Not Found Exceptions
     *
     * @param query
     * @param errorMessage
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void executeQuery(String query, String errorMessage) throws SQLException, ClassNotFoundException {
        try {
            DB_Connection.databaseExecuteQuery(query);
        } catch (SQLException exception) {
            System.out.println(errorMessage + errorMessage);
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Creates an observable array list of appointments that will be used to execute queries
     *
     * @param resultSet
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Appointment> getAppointmentObjects(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(resultSet.getInt("Appointment_ID"));
            appointment.setAppointmentTitle(resultSet.getString("Title"));
            appointment.setAppointmentDescription(resultSet.getString("Description"));
            appointment.setAppointmentLocation(resultSet.getString("Location"));
            appointment.setContactId(resultSet.getInt("Contact_ID"));
            appointment.setAppointmentType(resultSet.getString("Type"));
            appointment.setAppointmentStartTime(resultSet.getString("Start"));
            appointment.setAppointmentEndTime(resultSet.getString("End"));
            appointment.setCustomerId(resultSet.getInt("Customer_ID"));

            appointmentList.add(appointment);
        }

        return appointmentList;

    }


    /**
     * Checks if a customer has any appointments in the db
     *
     * @param customerId
     * @return  true/false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static boolean hasAppointments(int customerId) throws SQLException, ClassNotFoundException {
        String query = "select * from appointments where Customer_ID = "+customerId+"";

        ResultSet resultSet = DB_Connection.databaseQuery(query);
        ObservableList<Appointment> appointmentList = getAppointmentObjects(resultSet);

        if (appointmentList.isEmpty()) {
            return false;
        }

        return true;

    }

    /**
     * Checks if there is a time conflict with existing appointments
     *
     * @param customerId    the customer id to check for matching appointments
     * @param startTime     the start time of the appointment to be checked
     * @param endTime       the end time of the appointment to be checked
     * @return  true/false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static boolean isDuplicateAppointmentTime(int customerId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) as Duplicate_Count FROM appointments " +
                "WHERE Customer_ID = "+customerId+" " +
                "AND (('"+startTime+"' BETWEEN Start AND End) " +
                "OR ('"+endTime+"' BETWEEN Start AND End) " +
                "OR (Start BETWEEN '"+startTime+"' AND '"+endTime+"') " +
                "OR (End BETWEEN '"+startTime+"' AND '"+endTime+"')) ";

        ResultSet resultSet = DB_Connection.databaseQuery(query);
        if(resultSet.next()) {
            if (Integer.parseInt(resultSet.getString(1)) >= 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Inserts an appt into the db
     *
     * @param title         appt title to be added
     * @param description   appt description to be added
     * @param location      appt location to be added
     * @param type          appt type to be added
     * @param start         appt start date/time to be added
     * @param end           appt end date/time to be added
     * @param customerId    appt customer id to be added
     * @param userId        appt user id to be added
     * @param contactId     appt contact id to be added
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        String query = "insert into appointments"
                + "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                + "values('"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', "+customerId+", "+userId+", "+contactId+")";
        executeQuery(query, "Error inserting entry appt into db");
    }

    /**
     * Updates appt data in the database
     *
     * @param id            appt id to be updated
     * @param title         appt title to be updated
     * @param description   appt description to be updated
     * @param location      appt location to be updated
     * @param type          appt type to be updated
     * @param start         appt start date or time to be updated
     * @param end           appt end date or time to be updated
     * @param customerId    appt customer id to be updated
     * @param userId        appt user id to be updated
     * @param contactId     appt contact id to be updated
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws ClassNotFoundException, SQLException {
        String query = "update appointments set Title = '"+title+"', "
                + "Description = '"+description+"', Location = '"+location+"', Type = '"+type+"', "
                + "Start = '"+start+"', End = '"+end+"', Customer_ID = "+customerId+", User_ID = "+userId+", Contact_ID = '"+contactId+"' "
                + "where Appointment_ID = '"+id+"' ";
        executeQuery(query, "Error updating appt into db");
    }

    /**
     * Deletes appt from the database
     * @param id    the appt id of appt to be deleted
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void deleteAppointment(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from appointments where Appointment_ID = '"+id+"' ";
        executeQuery(query, "Error deleting appt in db");
    }

    /**
     * Gets all appt records for a customer
     *
     * @param customerId    the customer id of the appt to be returned
     * @return              an observable list of appt objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllRecordsForCustomer(int customerId) throws ClassNotFoundException, SQLException {
        String query = "select * from appointments where Customer_ID = "+customerId+"";

        ResultSet resultSet = DB_Connection.databaseQuery(query);

        return getAppointmentObjects(resultSet);

    }

    /**
     * Gets all appointment records in the database
     *
     * @return an observable list of all appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments";

        ResultSet resultSet = DB_Connection.databaseQuery(query);

        return getAppointmentObjects(resultSet);

    }

    /**
     * Retrieves appt records within past 7 days
     *
     * @return an observable list of appt objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllRecordsInNext7Days() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)";

        ResultSet resultSet = DB_Connection.databaseQuery(query);

        return getAppointmentObjects(resultSet);

    }

    /**
     * Gets appt records within next month
     *
     * @return an observable list of appt objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllRecordsInNextMonth() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 MONTH)";

        ResultSet resultSet = DB_Connection.databaseQuery(query);

        return getAppointmentObjects(resultSet);

    }

    /**
     * Gets appt records over the next 15 minutes
     * @return an observable list of appt objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Boolean getAllRecordsInNext15Minutes() throws ClassNotFoundException, SQLException {
        LocalDateTime now = LocalDateTime.now();

        String query = "select * from appointments WHERE Start BETWEEN ? AND DATE_ADD(?, INTERVAL 15 MINUTE)";

        PreparedStatement ps = DB_Connection.databaseQuery2(query);
        ps.setTimestamp(1, Timestamp.valueOf(now));
        ps.setTimestamp(2, Timestamp.valueOf(now));
        ps.execute();
        ResultSet rs = ps.getResultSet();

        if (rs.next()) {
            System.out.println(rs.getTimestamp("Start"));
            String startId = rs.getString("Appointment_ID");
            Timestamp startTimeStamp = rs.getTimestamp("Start");

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + startId +", Start: " + startTimeStamp + "\n", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            return true;
        }
        System.out.println(rs.getFetchSize());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming scheduled appointments", ButtonType.OK);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        return false;
    }



    /**
     * Gets appt records from the db
     * @return an observable list of all appts
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsByType() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments";

        ResultSet resultSet = DB_Connection.databaseQuery(query);

        return getAppointmentObjects(resultSet);

    }

}