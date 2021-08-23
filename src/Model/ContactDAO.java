package Model;

import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {

    /**
     * Creates an observable array list of contacts used to execute queries
     * @param resultSet
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Contact> getContactObjects(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Contact> contactList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt("Contact_ID"));
                contact.setContactName(resultSet.getString("Contact_Name"));
                contact.setContactEmail(resultSet.getString("Email"));

                contactList.add(contact);
            }

            return contactList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets contact records
     * @return  an observable list of contacts
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from contacts";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            ObservableList<Contact> contactList = getContactObjects(resultSet);

            return contactList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets contact id from the contact name
     * @param name  the contact name
     * @return      the contact id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getContactIdFromName(String name) throws ClassNotFoundException, SQLException {
        String query = "select Contact_ID from contacts where Contact_Name = '"+name+"'";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            if(resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString(1));
                return id;
            }
            return 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets contact name from the id
     * @param id    the contact id
     * @return      the contact name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getContactNameFromId(int id) throws ClassNotFoundException, SQLException {
        String query = "select Contact_Name from contacts where Contact_ID = "+id+"";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            if(resultSet.next()) {
                String name = resultSet.getString(1);
                return name;
            }
            return "";

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}