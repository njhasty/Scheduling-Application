package Utilities;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DB_Connection {

    /**
     * JDBC driver string
     */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Establish connection with db
     */
    private static Connection connection = null;

    /**
     * JDBC driver connection string and time zone parameters
     */
//    private static final String connectionString = "jdbc:mysql://wgudb.ucertify.com/WJ07mDi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String connectionString = "jdbc:mysql://wgudb.ucertify.com/WJ07mDi";

    /**
     * Connects to the db using JDBC driver
     * @throws SQLException
     * @throws ClassNotFoundException
     * @return
     */
    public static Connection dbConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException exception) {
            System.out.println("Please ensure JDBC is installed on local machine.");
            exception.printStackTrace();
            throw exception;
        }

        try {
            connection = DriverManager.getConnection(connectionString, "U07mDi", "53689070619");
        } catch (SQLException exception) {
            throw exception;
        }
        return null;
    }

    /**
     * Disconnects from db
     * @throws SQLException
     */
    public static void databaseDisconnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException exception) {
            throw exception;
        }
    }

    /**
     * Manages inserting, updating, and deleting data from db
     * @param sqlStatement  SQL query for execution
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void databaseExecuteQuery(String sqlStatement) throws SQLException, ClassNotFoundException {
        Statement statement = null;
        try {
            dbConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
        } catch (SQLException exception) {
            System.out.println("Problem occurred in dbExecuteQuery operation: " + exception);
            throw exception;
        } finally {
            if (statement != null) {
                statement.close();
            }
            databaseDisconnection();
        }
    }

    /**
     * Manages getting data from db
     * @param sqlQuery  the SQL query for execution
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet databaseQuery(String sqlQuery) throws SQLException, ClassNotFoundException {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSet cachedRowSet = null;

        try {
            dbConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(resultSet);
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            } if (statement != null) {
                statement.close();
            }
            databaseDisconnection();
        }
        return cachedRowSet;
    }

    public static PreparedStatement databaseQuery2(String sqlQuery) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = null;
        CachedRowSet cachedRowSet = null;

        try {
            dbConnection();
            statement = connection.prepareStatement(sqlQuery);

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return dbConnection();
    }

}