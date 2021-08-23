package Model;

import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    /**
     * Executes SQL queries and throws SQL, Class Not Found Exceptions
     * @param query
     * @param errorMessage
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void execute(String query, String errorMessage) throws SQLException, ClassNotFoundException {
        try {
            DB_Connection.databaseExecuteQuery(query);
        } catch (SQLException exception) {
            System.out.println(errorMessage + exception);
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Creates observable array list for customers to be used when executing queries
     * @param resultSet
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Customer> getCustomerObjects(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Customer> customerList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("Customer_ID"));
                customer.setCityId(resultSet.getInt("Division_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setCustomerAddress(resultSet.getString("Address"));
                customer.setCustomerPostalCode(resultSet.getString("Postal_Code"));
                customer.setCustomerPhoneNumber(resultSet.getString("Phone"));

                customerList.add(customer);
            }

            return customerList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Inserts customer into db
     * @param name      customer name to be added
     * @param address   customer address to be added
     * @param city  customer division to be added
     * @param zip       customer zip to be added
     * @param phone     customer phone to be added
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertCustomer(String name, String address, int city, String zip, String phone) throws SQLException, ClassNotFoundException {
        String query = "insert into customers"
                + "(Customer_Name, Address, Division_ID, Postal_Code, Phone) "
                + "values('"+name+"', '"+address+"', '"+city+"', '"+zip+"', '"+phone+"')";
        execute(query, "Error inserting customer in database");
    }

    /**
     * Updates customer that already exists inside of the db
     * @param id        customer id for update
     * @param name      customer name for update
     * @param address   customer address for update
     * @param city  customer division for update
     * @param zip       customer zip for update
     * @param phone     customer phone for update
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void updateCustomer(int id, String name, String address, int city, String zip, String phone) throws ClassNotFoundException, SQLException {
        String query = "update customers set Customer_Name = '"+name+"', "
                + "Address = '"+address+"', Postal_Code = '"+zip+"', Phone = '"+phone+"', "
                + "Division_ID = '"+city+"' "
                + "where Customer_ID = '"+id+"' ";
        execute(query, "Error updating customer in db");
    }

    /**
     * Deletes customer from db
     * @param id    id of customer to be deleted
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void deleteCustomer(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from customers where Customer_ID = '"+id+"' ";
        execute(query, "Error deleting customer in db");
    }

    /**
     * Gets customer records from customer table in the db
     * @return  observable list of customer records
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from customers";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            ObservableList<Customer> customerList = getCustomerObjects(resultSet);

            return customerList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets list of customer records added to db in past month
     * @return  observable list of customers
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllRecordsAddedThisMonth() throws ClassNotFoundException, SQLException {
        String query = "select * from customers WHERE Create_Date >= DATE_ADD(NOW(), INTERVAL -30 DAY)";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            ObservableList<Customer> customerList = getCustomerObjects(resultSet);

            return customerList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}