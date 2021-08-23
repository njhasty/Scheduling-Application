package Model;

import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Executes SQL queries and throws SQL, Class Not Found Exceptions
     * @param resultSet    the result set
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<User> getUserObjects(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<User> userList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("User_ID"));
                user.setUserUsername(resultSet.getString("User_Name"));
                user.setUserPassword(resultSet.getString("Password"));

                userList.add(user);
            }

            return userList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets user records in db
     * @return  observable list of users in user table
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<User> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from users";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            ObservableList<User> userList = getUserObjects(resultSet);

            return userList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Get user id of logged in user
     * @param username  username of user
     * @return          id of user
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCurrentUserId(String username) throws ClassNotFoundException, SQLException {
        String query = "select User_ID from users where User_Name = '"+username+"'";

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

}