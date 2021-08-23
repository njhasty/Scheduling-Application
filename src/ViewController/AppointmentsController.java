package ViewController;

import Model.*;
import Utilities.CustomerSingletonInstance;
import Utilities.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AppointmentsController implements Initializable {

    /**
     * label for generating current customer name
     */
    @FXML
    private Label titleTxt;

    /**
     * label for displaying current customer id
     */
    @FXML
    private Label customerIdTxt;

    /**
     * table for displaying  appt data
     */
    @FXML
    private TableView<Appointment> apptTV;

    /**
     * column for displaying appt id data
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    /**
     * column for displaying appt title data
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;

    /**
     * column for displaying appt description data
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    /**
     * column for displaying appt location data
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;

    /**
     * column for displaying appt contact id data
     */
    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;

    /**
     * column for displaying appt type data
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;

    /**
     * column for displaying appt starting time data
     */
    @FXML
    private TableColumn<Appointment, String> startingTimeCol;

    /**
     * column for displaying appt ending time data
     */
    @FXML
    private TableColumn<Appointment, String> endingTimeCol;

    /**
     * column for displaying appt customer id data
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    /**
     * text field for displaying appt id data
     */
    @FXML
    private TextField apptIdTxtField;

    /**
     * text field for displaying and collecting appt title data
     */
    @FXML
    private TextField titleTxtField;

    /**
     * text field for displaying and collecting appt description data
     */
    @FXML
    private TextField descriptionTxtField;

    /**
     * text field for displaying and collecting appt location data
     */
    @FXML
    private TextField locationTxtField;

    /**
     * combo box for displaying and collecting appt contact data
     */
    @FXML
    private ComboBox<String> contactCB;

    /**
     * combo box for displaying and collecting appt type data
     */
    @FXML
    private ComboBox<String> typeCB;

    /**
     * date picker for displaying and collecting appt starting date data
     */
    @FXML
    private DatePicker startingDatePicker;

    /**
     * text field for displaying and collecting appt starting time data
     */
    @FXML
    private TextField startingTimeTxtField;

    /**
     * date picker for displaying and collecting appt ending date data
     */
    @FXML
    private DatePicker endingDatePicker;

    /**
     * text field for displaying and collecting appt ending time data
     */
    @FXML
    private TextField endingTimeTxtField;

    /**
     * label for displaying current user id
     */
    @FXML
    private Label userIdTxt;

    /**
     * label for displaying current function of the window
     */
    @FXML
    private Label functionTitleTxt;

    /**
     * button for adding appt to the db
     */
    @FXML
    private Button addApptBtn;

    /**
     * button for updating appt in the db
     */
    @FXML
    private Button updateApptBtn;

    /**
     * button for deleting appt from db
     */
    @FXML
    private Button deleteApptBtn;

    /**
     * Initializes the appt controller class.
     *
     * Lambda expression used to iterate through contacts and add contact names to list
     *
     * Lambda expression used to listen for if selection is new or old and make decisions based on selected object
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startingDatePicker.setValue(LocalDate.now());
        endingDatePicker.setValue(LocalDate.now());
        fillTypeList();

        addApptBtn.setDisable(false);
        updateApptBtn.setDisable(true);
        deleteApptBtn.setDisable(true);

        functionTitleTxt.setText("Add Appointment");


        apptIdCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        contactIdCol.setCellValueFactory(cellData -> cellData.getValue().getContactIdProperty().asObject());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        startingTimeCol.setCellValueFactory(cellData -> cellData.getValue().getStartingTimeProperty());
        endingTimeCol.setCellValueFactory(cellData -> cellData.getValue().getEndingTimeProperty());
        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().getCustIdProperty().asObject());


        int customerId = CustomerSingletonInstance.instanceId;
        System.out.println("Customer ID: " + customerId);

        ObservableList<Appointment> apptList;
        try {
            apptList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTV(apptList);
        } catch (ClassNotFoundException | SQLException exception) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, exception);
        }

        ObservableList<Contact> contactsList;
        try {
            contactsList = ContactDAO.getAllRecords();

            ObservableList<String> contactNames = FXCollections.observableArrayList();

            contactsList.forEach((contact) -> {
                String contactToAdd = contact.getNameProperty().getValue();
                contactNames.add(contactToAdd);
            });

            contactCB.setItems(contactNames);
        } catch (ClassNotFoundException | SQLException exception) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, exception);
        }


        apptTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                addApptBtn.setDisable(true);
                updateApptBtn.setDisable(false);
                deleteApptBtn.setDisable(false);

                functionTitleTxt.setText("Update or Delete");

                Appointment appt = apptTV.getSelectionModel().getSelectedItem();

                int apptId = appt.getIdProperty().getValue();
                String title = appt.getNameProperty().getValue();
                String description = appt.getDescriptionProperty().getValue();
                String location = appt.getLocationProperty().getValue();
                int contactId = appt.getContactIdProperty().getValue();
                String type = appt.getTypeProperty().getValue();
                String start = appt.getStartingTimeProperty().getValue();
                String end = appt.getEndingTimeProperty().getValue();



                try {
                    String contact = ContactDAO.getContactNameFromId(contactId);

                    String[] startingDateAndTime = convertStringToDateAndTime(start);
                    String[] endingDateAndTime = convertStringToDateAndTime(end);
                    LocalDate startingDate = convertStringToLocalDate(startingDateAndTime[0]);
                    String startingTime = startingDateAndTime[1];
                    LocalDate endingDate = convertStringToLocalDate(endingDateAndTime[0]);
                    String endingTime = endingDateAndTime[1];


                    apptIdTxtField.setText(Integer.toString(apptId));
                    titleTxtField.setText(title);
                    descriptionTxtField.setText(description);
                    locationTxtField.setText(location);
                    contactCB.setValue(contact);
                    typeCB.setValue(type);
                    startingDatePicker.setValue(startingDate);
                    startingTimeTxtField.setText(startingTime);
                    endingDatePicker.setValue(endingDate);
                    endingTimeTxtField.setText(endingTime);

                } catch (ClassNotFoundException | SQLException exception) {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        });
    }

    //timestamp.tolocaldatetime method to change reference

    /**
     * Converts local date to string in format for date picker
     *
     * @param date  string for conversion
     * @return      returns local date converted from string
     */
    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        return localDate;
    }

    /**
     * Converts string to a date and time, puts into array object
     *
     * @param str   string for conversion to date and time
     * @return      the array object that contains converted date and time
     */
    private String[] convertStringToDateAndTime(String str) {
        String[] answer = new String[2];

        String[] getDateAndTime = str.split(" ");
        String date = getDateAndTime[0];
        String time = getDateAndTime[1];

        String[] dateArray = date.split("-");
        String yy = dateArray[0];
        String mo = dateArray[1];
        String dd = dateArray[2];

        answer[0] = dd + "-" + mo + "-" + yy;

//        String[] timeArray = time.split(":");
        String[] timeArray = time.split(":");
//        int hh = Integer.parseInt(timeArray[0]);
        String hh = timeArray[0];
        String mm = timeArray[1];

        answer[1] = hh + ":" + mm;

//        if (hh == 0) {
//            hh = 12;
//            answer[1] = Integer.toString(hh) + ":" + mm + " AM";
//        } else if (hh < 13) {
//            answer[1] = Integer.toString(hh) + ":" + mm + " PM";
//        } else if (hh >= 13) {
//            hh -= 12;
//            answer[1] = Integer.toString(hh) + ":" + mm + " PM";
//        }


        return answer;
    }

    /**
     * Shows appt data in table view
     *
     * @param appointmentList   list of appointments for populating table view
     */
    private void populateTV(ObservableList<Appointment> appointmentList) {
        apptTV.setItems(appointmentList);
    }

    /**
     * Gets data from customer controller for displaying in window
     *
     * @param customer  current customer object sent from customer controller
     * @param userId    current user id sent from customer controller
     */
    public void getData(Customer customer, int userId) {
        titleTxt.setText("Appts for " + customer.getNameProperty().getValue());
        userIdTxt.setText("User ID: " + userId);
        customerIdTxt.setText("Customer ID: " + customer.getIdProperty().getValue());
    }

    /**
     * Resets txt fields in window
     */
    public void clearTxtFields() {
        apptIdTxtField.clear();
        titleTxtField.clear();
        descriptionTxtField.clear();
        locationTxtField.clear();
        contactCB.getSelectionModel().clearSelection();
        typeCB.getSelectionModel().clearSelection();
        startingDatePicker.getEditor().clear();
        startingTimeTxtField.clear();
        endingDatePicker.getEditor().clear();
        endingTimeTxtField.clear();
    }

    /**
     * Conversion of the date and time strings to LocalDateTime object in UTC format
     *
     * @param date  date string for conversion
     * @param time  time string for conversion
     * @return      LocalDateTime object in UTC format
     */
    public LocalDateTime convertToTimestamp(String date, String time) {

        // get date
        String[] dateArray = date.split("-");
        int yy = Integer.parseInt(dateArray[0]);
        int mo = Integer.parseInt(dateArray[1]);
        int dd = Integer.parseInt(dateArray[2]);

        // get time

        String[] timeArray = time.split(":");
        int hh = Integer.parseInt(timeArray[0]);
        int mm = Integer.parseInt(timeArray[1]);
//        if (timeArray[1].equals("AM") && Integer.parseInt(hourMinArray[0]) == 12) {
//            hh = 0;
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("PM") && Integer.parseInt(hourMinArray[0]) == 12) {
//            hh = 12;
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("AM")) {
//            hh = Integer.parseInt(hourMinArray[0]);
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("PM")) {
//            hh = Integer.parseInt(hourMinArray[0]) + 12;
//            mm = Integer.parseInt(hourMinArray[1]);
//        }

        LocalDateTime localDateTime = LocalDateTime.of(yy, mo, dd, hh, mm);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime timeToAdd = zonedDateTime.toLocalDateTime();

        return timeToAdd;

    }


//    /**
//     * Converts date and time strings into a LocalDateTime object in EST
//     *
//     * @param date  the date string to be converted
//     * @param time  the time string to be converted
//     * @return      the LocalDateTime object in EST
//     */
//    public LocalDateTime convertToESTFormat(String date, String time) {
//
//        String[] dateArray = date.split("-");
//        int yy = Integer.parseInt(dateArray[0]);
//        int mo = Integer.parseInt(dateArray[1]);
//        int dd = Integer.parseInt(dateArray[2]);
//
//        int hh = 0;
//        int mm = 0;
//        String[] timeArray = time.split(" ");
//        String[] hourMinArray = timeArray[0].split(":");
//
//        if (timeArray[1].equals("AM") && Integer.parseInt(hourMinArray[0]) == 12) {
//            hh = 0;
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("PM") && Integer.parseInt(hourMinArray[0]) == 12) {
//            hh = 12;
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("AM")) {
//            hh = Integer.parseInt(hourMinArray[0]);
//            mm = Integer.parseInt(hourMinArray[1]);
//        } else if (timeArray[1].equals("PM")) {
//            hh = Integer.parseInt(hourMinArray[0]) + 12;
//            mm = Integer.parseInt(hourMinArray[1]);
//        }
//
//        LocalDateTime localDateTime = LocalDateTime.of(yy, mo, dd, hh, mm);
////        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
////        LocalDateTime timeToAdd = zonedDateTime.toLocalDateTime();
//
//        return localDateTime;
//    }

    /**
     * Checks appt time for adding or updating
     *
     * @param startingTime     starting LocalDateTime object
     * @param endingTime       ending LocalDateTime object
     * @return         boolean if LocalDateTime objects is valid
     */
    public boolean validateApptTime(LocalTime startingTime, LocalTime endingTime) {
        int startingHH = startingTime.getHour();
        int startingMM = startingTime.getMinute();
        int endingHH = endingTime.getHour();
        int endingMM = endingTime.getMinute();

        if (startingHH >= 8 && startingHH < 22 && endingHH >= 8 && endingHH <= 22) {
            if (endingHH == 22 && endingMM > 8) {
                return false;
            }
            return true;
        }
        System.out.println("Invalid appointment time.");
        return false;
    }


    /**
     * Creates ObservableList of appt types
     * Inserts list into typeCB
     */
    private void fillTypeList() {
        /**
         * populates list with four categories of appointment types
         */
        ObservableList<String> apptTypeList = FXCollections.observableArrayList();
        apptTypeList.addAll("In-person", "Email", "Phone");
        typeCB.setItems(apptTypeList);
    }


    /**
     * Queries database for maximum Appointment ID after new appointment has been created, then returns as int
     * @return apptMaxId
     */
    public int getMaxApptId() {
        int apptMaxId = 0;
        String maxApptIdQuery = "select max(Appointment_ID) from appointments";
        try {
            ResultSet resultSet = DB_Connection.databaseQuery(maxApptIdQuery);

            if(resultSet.next()) {
                apptMaxId = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return apptMaxId;
    }

    /**
     * Manages add appt btn press. Validates input data and updates db with
     * new appt.
     *
     * Lambda expression used to validate user response before program runs and simplifies code.
     * Expression shortens the code to show alert message and receive user input.
     *
     * Lambda expressions used to show alert message and take user input response
     *
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void addApptBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String id = apptIdTxtField.getText();
            String title = titleTxtField.getText();
            String description = descriptionTxtField.getText();
            String location = locationTxtField.getText();
            String contact = contactCB.getValue();
            String startingDate = startingDatePicker.getValue().toString();
            String startingTime = startingTimeTxtField.getText();
            String endingDate = endingDatePicker.getValue().toString();
            String endingTime = endingTimeTxtField.getText();
            String typeString = typeCB.getValue();
            String type;
            if (typeString.equals("In-person") || typeString.equals("Email") || typeString.equals("Phone")) {
                type = typeString;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Combo box must either be 'In-person' or 'Email' or 'Phone'.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
                return;
            }

            String customerIdString = customerIdTxt.getText();
            int customerId = Integer.parseInt(customerIdString.split(" ")[2]);

            String userIdString = userIdTxt.getText();
            int userId = Integer.parseInt(userIdString.split(" ")[2]);

            int contactId = ContactDAO.getContactIdFromName(contact);

            LocalDateTime startingTimestamp = convertToTimestamp(startingDate, startingTime);
            LocalDateTime endingTimestamp = convertToTimestamp(endingDate, endingTime);

            LocalTime startingLT = LocalTime.parse(startingTime);
            LocalTime endingLT = LocalTime.parse(endingTime);


//            LocalDateTime startESTFormat = convertToESTFormat(startingDate, startingTime);
//            LocalDateTime endESTFormat = convertToESTFormat(endingDate, endingTime);

            boolean isValidAppointmentTime = validateApptTime(startingLT, endingLT);
            boolean isDuplicateAppointmentTime = AppointmentDAO.isDuplicateAppointmentTime(customerId, startingTimestamp, endingTimestamp);

            if (isValidAppointmentTime) {
                if (!isDuplicateAppointmentTime) {
                    AppointmentDAO.insertAppointment(title, description, location, type, startingTimestamp, endingTimestamp, customerId, userId, contactId);

                    ObservableList<Appointment> appointmentList = AppointmentDAO.getAllRecordsForCustomer(customerId);
                    populateTV(appointmentList);


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully added appointment " + getMaxApptId() + " of type " + typeString + " to database.", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);

                    clearTxtFields();

                    addApptBtn.setDisable(false);
                    updateApptBtn.setDisable(true);
                    deleteApptBtn.setDisable(true);

                    startingDatePicker.setValue(LocalDate.now());
                    endingDatePicker.setValue(LocalDate.now());

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Customer already has an appointment scheduled at this time.", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
                }
            } else if (startingTimestamp.isAfter(endingTimestamp)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Start date/time must be before end date/time.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Appointment must be between 0800 and 2200.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        } catch (SQLException exception) {
            System.out.println("Error adding appointment to database: " + exception);
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * Manages update appt button pressed. Validates user input data and updates db
     * with user selected appt.
     *
     * Lambda expressions show messages and handle user input response
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void updateApptBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String title = titleTxtField.getText();
            String description = descriptionTxtField.getText();
            String location = locationTxtField.getText();
            String contact = contactCB.getValue();
            String type = typeCB.getValue();
            String startingDate = startingDatePicker.getValue().toString();
            String startingTime = startingTimeTxtField.getText();
            String endingDate = endingDatePicker.getValue().toString();
            String endingTime = endingTimeTxtField.getText();

            String customerIdString = customerIdTxt.getText();
            int customerId = Integer.parseInt(customerIdString.split(" ")[2]);

            String userIdString = userIdTxt.getText();
            int userId = Integer.parseInt(userIdString.split(" ")[2]);

            int contactId = ContactDAO.getContactIdFromName(contact);

            LocalDateTime startingTimestamp = convertToTimestamp(startingDate, startingTime);
            LocalDateTime endingTimestamp = convertToTimestamp(endingDate, endingTime);

            LocalTime startingLT = LocalTime.parse(startingTime);
            LocalTime endingLT = LocalTime.parse(endingTime);

//            LocalDateTime startingTimeEST = convertToESTFormat(startingDate, startingTime);
//            LocalDateTime endingTimeEST = convertToESTFormat(endingDate, endingTime);

            boolean isValidApptTime = validateApptTime(startingLT, endingLT);
            boolean isDuplicateAppointmentTime = AppointmentDAO.isDuplicateAppointmentTime(customerId, startingTimestamp, endingTimestamp);

            Appointment appt = apptTV.getSelectionModel().getSelectedItem();
            int apptId = appt.getIdProperty().getValue();

            if (!isValidApptTime) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Appointment must be made between 8am-10pm.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else if (startingTimestamp.isAfter(endingTimestamp)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Start date/time must be before end date/time.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else if (isDuplicateAppointmentTime) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Customer already has an appointment scheduled at this time.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else {
                AppointmentDAO.updateAppointment(apptId, title, description, location, type, startingTimestamp, endingTimestamp, customerId, userId, contactId);

                ObservableList<Appointment> apptList = AppointmentDAO.getAllRecordsForCustomer(customerId);
                populateTV(apptList);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully updated appointment " + apptId + " of type " + type + " in database.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);

                addApptBtn.setDisable(false);
                updateApptBtn.setDisable(true);
                deleteApptBtn.setDisable(true);
            }
        } catch (NumberFormatException | SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        clearTxtFields();
        functionTitleTxt.setText("Add Appointment");
    }

    /**
     * Manages delete appt btc pressed. Deletes selected appt from db.
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void deleteApptBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException {

        Appointment appt = apptTV.getSelectionModel().getSelectedItem();
        int id = appt.getIdProperty().getValue();
        String type = appt.getTypeProperty().getValue();

        try {
            AppointmentDAO.deleteAppointment(id);
            String customerIdString = customerIdTxt.getText();
            int customerId = Integer.parseInt(customerIdString.split(" ")[2]);
            ObservableList<Appointment> apptList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTV(apptList);
        } catch (SQLException sqlException) {
            System.out.println("Unable to delete appt from db: " + sqlException);
            sqlException.printStackTrace();
            throw sqlException;
        }

        clearTxtFields();

        addApptBtn.setDisable(false);
        updateApptBtn.setDisable(true);
        deleteApptBtn.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully updated appointment " + id + " of type " + type + " in database.", ButtonType.OK);
        alert.showAndWait().filter(response -> response == ButtonType.OK);

        functionTitleTxt.setText("Add");

    }

    /**
     * Manages back btn pressed. Sends user back to customer view.
     *
     * @param event
     */
    @FXML
    void backBtnPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            CustomersController controller = loader.getController();

            String userIdString = userIdTxt.getText();
            int userId = Integer.parseInt(userIdString.split(" ")[2]);
            controller.getData(userId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
