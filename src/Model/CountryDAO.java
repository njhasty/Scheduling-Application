package Model;

import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    /**
     * Creates observable array list of countries for the execution of queries
     * @param resultSet    the result set
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Country> getCountryObjects(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Country> countryList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt("Country_ID"));
                country.setCountryCountryName(resultSet.getString("Country"));

                countryList.add(country);
            }

            return countryList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets country records in the db
     * @return  observable list of country objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Country> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from countries";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            ObservableList<Country> countryList = getCountryObjects(resultSet);

            return countryList;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets country id from country name
     * @param countryName   country name
     * @return              country id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getSelectedCountryId(String countryName) throws ClassNotFoundException, SQLException {
        String query = "select Country_ID from countries where Country = '"+countryName+"' ";

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
     * Gets country id from the city id from city table
     * @param id    city id
     * @return      country id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCountryIdFromDivisionId(int id) throws ClassNotFoundException, SQLException {
        String query = "select COUNTRY_ID from first_level_divisions where Division_ID = "+id+"";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            if(resultSet.next()) {
                int countryId = Integer.parseInt(resultSet.getString(1));
                return countryId;
            }
            return 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Gets country name from country id
     * @param id    country id
     * @return      country name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getCountryName(int id) throws ClassNotFoundException, SQLException {
        String query = "select Country from countries where Country_ID = "+id+"";

        try {
            ResultSet resultSet = DB_Connection.databaseQuery(query);
            if(resultSet.next()) {
                String countryName = resultSet.getString(1);
                return countryName;
            }
            return "";

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}