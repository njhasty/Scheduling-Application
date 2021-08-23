package ViewController;

import Model.Appointment;
import Model.AppointmentDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ScheduleController implements Initializable {

    /**
     * radio button for displaying schedule for this week
     */
    @FXML
    private RadioButton thisWeekSchedule;

    /**
     * radio button for displaying schedule for this month
     */
    @FXML
    private RadioButton thisMonthSchedule;

    /**
     * radio button for displaying appts schedule
     */
    @FXML
    private RadioButton allAppts;

    /**
     * table view for displaying appt data
     */
    @FXML
    private TableView<Appointment> apptsTV;

    /**
     * column for displaying appt id
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    /**
     * column for displaying appt title
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;

    /**
     * column for displaying appt description
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    /**
     * column for displaying appt location
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;

    /**
     * column for displaying appt contact id
     */
    @FXML
    private TableColumn<Appointment, Integer> contactIdCol;

    /**
     * column for displaying appt type
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;

    /**
     * column for displaying appt start date and time
     */
    @FXML
    private TableColumn<Appointment, String> startCol;

    /**
     * column for displaying appt end date and time
     */
    @FXML
    private TableColumn<Appointment, String> endCol;

    /**
     * column for displaying appt customer id
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    /**
     * label for displaying the current user id
     */
    @FXML
    private Label userIdTxt;

    /**
     * Initializes controller class. Sets up tg and rb.
     * Sets up tv and col. Gets appt data to display. Handles
     * selection of each rb.
     *
     * Lambda expression is used to assign objects to columns in table view
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final ToggleGroup group = new ToggleGroup();
        allAppts.setToggleGroup(group);
        allAppts.setSelected(true);
        thisWeekSchedule.setToggleGroup(group);
        thisMonthSchedule.setToggleGroup(group);

        apptIdCol.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        contactIdCol.setCellValueFactory(cellData -> cellData.getValue().getContactIdProperty().asObject());
        typeCol.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        startCol.setCellValueFactory(cellData -> cellData.getValue().getStartingTimeProperty());
        endCol.setCellValueFactory(cellData -> cellData.getValue().getEndingTimeProperty());
        customerIdCol.setCellValueFactory(cellData -> cellData.getValue().getCustIdProperty().asObject());

        ObservableList<Appointment> appointmentList;
        try {
            appointmentList = AppointmentDAO.getAllRecords();
            populateTable(appointmentList);
        } catch (ClassNotFoundException | SQLException exception) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, exception);
        }

        thisWeekSchedule.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecordsInNext7Days();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
            }
        });

        thisMonthSchedule.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecordsInNextMonth();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
            }
        });

        allAppts.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecords();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
            }
        });
    }

    /**
     * Displays appt data in the tv
     *
     * @param appointmentList
     */
    private void populateTable(ObservableList<Appointment> appointmentList) {
        apptsTV.setItems(appointmentList);
    }

    /**
     * Gets user id data from customer controller.
     *
     * @param id
     */
    public void getData(int id) {
        int userId = id;
        userIdTxt.setText("User ID: " + userId);
    }

    /**
     * Manages back btc pressed. Returns user back to customer view screen.
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
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

}
