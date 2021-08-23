package ViewController;

import Model.*;
import Utilities.CustomerSingletonInstance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomersController {

    /**
     * table view for displaying customer data
     */
    @FXML
    private TableView<Customer> customerTV;

    /**
     * column for displaying customer id data
     */
    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    /**
     * column for displaying customer name data
     */
    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    /**
     * column for displaying customer address data
     */
    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    /**
     * column for displaying customer postal code data
     */
    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    /**
     * column for displaying customer city data
     */
    @FXML
    private TableColumn<Customer, Integer> customerDivisionCol;

    /**
     * column for displaying customer phone number data
     */
    @FXML
    private TableColumn<Customer, String> customerPhoneNumberCol;

    /**
     * text field for collecting and displaying customer name data
     */
    @FXML
    private TextField customerNameTxtField;

    /**
     * text field for collecting and displaying customer address data
     */
    @FXML
    private TextField customerAddressTxtField;

    /**
     * combo box for collecting and displaying customer country data
     */
    @FXML
    private ComboBox<String> countryCB;

    /**
     * combo box for collecting and displaying customer city data
     */
    @FXML
    private ComboBox<String> cityCB;

    /**
     * text field for collecting and displaying customer postal code data
     */
    @FXML
    private TextField postalCodeTxtField;

    /**
     * text field for collecting and displaying customer phone number data
     */
    @FXML
    private TextField phoneNumberTxtField;

    /**
     * label for collecting and displaying function of the window
     */
    @FXML
    private Label functionTitleTxt;

    /**
     * label for displaying the current user id
     */
    @FXML
    private Label userIdTxt;

    /**
     * button for adding new customer to the database
     */
    @FXML
    private Button addCustomerBtn;

    /**
     * button for updating customer in the database
     */
    @FXML
    private Button updateCustomerBtn;

    /**
     * button for deleting a customer from the database
     */
    @FXML
    private Button deleteCustomerBtn;

    /**
     * button for getting customer data and navigating to the appointment view
     */
    @FXML
    private Button getApptBtn;

    /**
     * Initializes customer controller class.
     *
     * @throws Exception
     */
    @FXML
    public void initialize() throws Exception {

        addCustomerBtn.setDisable(false);
        updateCustomerBtn.setDisable(true);
        deleteCustomerBtn.setDisable(true);
        getApptBtn.setDisable(true);

        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        customerAddressCol.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        customerPostalCodeCol.setCellValueFactory(cellData -> cellData.getValue().getPostalCodeProperty());
        customerDivisionCol.setCellValueFactory(cellData -> cellData.getValue().getCityIdProperty().asObject());
        customerPhoneNumberCol.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumberProperty());

        ObservableList<Customer> customersList = CustomerDAO.getAllRecords();
        populateTV(customersList);

        ObservableList<Country> countryList = CountryDAO.getAllRecords();
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        countryList.forEach((country) -> {
            String countryToAdd = country.getCountryNameProperty().getValue();
            countryNames.add(countryToAdd);
        });

        countryCB.setItems(countryNames);

        customerTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateCustomerBtn.setDisable(false);
                deleteCustomerBtn.setDisable(false);
                getApptBtn.setDisable(false);
                addCustomerBtn.setDisable(true);

                functionTitleTxt.setText("Update or Delete");

                Customer customer = customerTV.getSelectionModel().getSelectedItem();

                String customerName = customer.getNameProperty().getValue();
                String customerAddress = customer.getAddressProperty().getValue();
                String customerZip = customer.getPostalCodeProperty().getValue();
                String customerPhone = customer.getPhoneNumberProperty().getValue();

                int cityId = customer.getCityIdProperty().getValue();
                try {
                    String cityName = CityDAO.getDivisionName(cityId);
                    int countryId = CountryDAO.getCountryIdFromDivisionId(cityId);
                    String countryName = CountryDAO.getCountryName(countryId);

                    cityCB.setValue(cityName);
                    countryCB.setValue(countryName);

                } catch (ClassNotFoundException | SQLException exception) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, exception);
                }

                customerNameTxtField.setText(customerName);
                customerAddressTxtField.setText(customerAddress);
                postalCodeTxtField.setText(customerZip);
                phoneNumberTxtField.setText(customerPhone);
            }
        });

    }

    /**
     * Displays customer data in the table view
     *
     * @param customerList  the list of customers used to populate the table view
     */
    private void populateTV(ObservableList<Customer> customerList) {
        customerTV.setItems(customerList);
    }

    /**
     * Clears all of the text fields in the window
     */
    private void clearTxtFields() {
        customerNameTxtField.clear();
        customerAddressTxtField.clear();
        countryCB.getSelectionModel().clearSelection();
        countryCB.setPromptText("Select Country");
        cityCB.getSelectionModel().clearSelection();
        cityCB.setPromptText("Select City");
        postalCodeTxtField.clear();
        phoneNumberTxtField.clear();
    }

    /**
     * Receives data from login controller to display in the window
     *
     * @param id    current user id sent from login controller
     */
    public void getData(int id) {
        int userId = id;
        userIdTxt.setText("User ID: " + id);
    }

    /**
     * Checks inputted customer data in form fields
     *
     * @param name      the customer name to be validated
     * @param address   the customer address to be validated
     * @param country   the customer country to be validated
     * @param zip       the customer postal code to be validated
     * @param phone     the customer phone number to be validated
     * @param city       the customer first level division to be validated
     * @return          whether or not the customer data is valid
     */
    private boolean isCustomerValidated(String name, String address, String country, String zip, String phone, String city) {
        if (name == null || address == null || country == null || zip == null || phone == null || city == null ) {
            return false;
        }
        return true;
    }

    /**
     * Manages add customer button press. Checks data user inputted and updates db with the new customer.
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void addCustomerBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String customerName = customerNameTxtField.getText();
            String customerAddress = customerAddressTxtField.getText();
            String customerCountry = countryCB.getValue();
            String customerPostalCode = postalCodeTxtField.getText();
            String customerPhoneNumber = phoneNumberTxtField.getText();

            String customerFLD = cityCB.getValue();
            int selectedCityId = CityDAO.getSelectedCityId(customerFLD);

            boolean isValidatedCustomer = isCustomerValidated(customerName, customerAddress, customerCountry, customerPostalCode, customerPhoneNumber, customerFLD);

            if (isValidatedCustomer) {
                CustomerDAO.insertCustomer(customerName, customerAddress, selectedCityId, customerPostalCode, customerPhoneNumber);

                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTV(customerList);

                clearTxtFields();

                addCustomerBtn.setDisable(false);
                updateCustomerBtn.setDisable(true);
                deleteCustomerBtn.setDisable(true);
                getApptBtn.setDisable(true);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "One or more fields is blank.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Manages update customer button press. Checks data user inputted and updates db with the selected customer.
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void updateCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String customerName = customerNameTxtField.getText();
            String customerAddress = customerAddressTxtField.getText();
            String customerZip = postalCodeTxtField.getText();
            String customerPhone = phoneNumberTxtField.getText();
            String customerCountry = countryCB.getValue();

            Customer customer = customerTV.getSelectionModel().getSelectedItem();
            int customerId = customer.getIdProperty().getValue();

            String customerField = cityCB.getValue();
            int cityId = CityDAO.getSelectedCityId(customerField);

            boolean isValidatedCustomer = isCustomerValidated(customerName, customerAddress, customerCountry, customerZip, customerPhone, customerField);

            if (isValidatedCustomer) {
                CustomerDAO.updateCustomer(customerId, customerName, customerAddress, cityId, customerZip, customerPhone);

                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTV(customerList);

                clearTxtFields();

                addCustomerBtn.setDisable(false);
                updateCustomerBtn.setDisable(true);
                deleteCustomerBtn.setDisable(true);
                getApptBtn.setDisable(true);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error updating customer.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }



        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }

        functionTitleTxt.setText("Add Customer");
    }

    /**
     * Manages delete customer button press. Deletes selected customer from the db.
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void deleteCustomerBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException {

        Customer selectedCustomer = customerTV.getSelectionModel().getSelectedItem();
        int customerId = selectedCustomer.getIdProperty().getValue();

        if (!AppointmentDAO.hasAppointments(customerId)) {
            try {
                CustomerDAO.deleteCustomer(customerId);
                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTV(customerList);
            } catch (SQLException exception) {
                exception.printStackTrace();
                throw exception;
            }

            clearTxtFields();

            addCustomerBtn.setDisable(false);
            updateCustomerBtn.setDisable(true);
            deleteCustomerBtn.setDisable(true);
            getApptBtn.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "customer deleted", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);

            functionTitleTxt.setText("Add");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot delete customer with upcoming appointments.", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * Manages selection of the country combo box. Populates city combo box with the correct data.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void countrySelected(ActionEvent event) throws Exception {
        String selectedCountry = countryCB.getValue();
        System.out.println(countryCB.getValue());

        int selectedId = CountryDAO.getSelectedCountryId(selectedCountry);

        ObservableList<City> cityList = CityDAO.getAllRecords();
        ObservableList<String> cityNames = FXCollections.observableArrayList();

        cityList.forEach((city) -> {
            if (selectedId == city.getCountryIdProperty().getValue()) {
                String cityToAdd = city.getCityNameProperty().getValue();
                cityNames.add(cityToAdd);
            }
        });

        cityCB.setItems(cityNames);
    }

    /**
     * Manages the get appointments button pressed. Sends user to appointments view and sends necessary data.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void getApptsBtnPressed(ActionEvent event) throws IOException {
        try {
            Customer customer = customerTV.getSelectionModel().getSelectedItem();
            CustomerSingletonInstance.instanceId = customer.getIdProperty().getValue();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentsView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            AppointmentsController controller = loader.getController();

            String userIdString = userIdTxt.getText();
            int userId = Integer.parseInt(userIdString.split(" ")[2]);

            controller.getData(customer, userId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Manages schedule button pressed. Sends user to schedule view and sends necessary data.
     *
     * @param event
     */
    @FXML
    void scheduleBtnPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ScheduleController controller = loader.getController();

            String userIdString = userIdTxt.getText();
            int userId = Integer.parseInt(userIdString.split(" ")[2]);
            controller.getData(userId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Manages logout button pressed. Logs out user and sends back to login page.
     *
     * @param event
     */
    @FXML
    void logoutBtnPressed(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Required");
            alert.setHeaderText("Logout");
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                LoginController controller = loader.getController();
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Logout cancelled.");
            }

        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Manages get reports button pressed. Sends user to reports view and sends necessary data.
     *
     * @param event
     */
    @FXML
    void getReportsBtnPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportsView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ReportsController controller = loader.getController();

            String userIdStr = userIdTxt.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            controller.getData(userId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

}