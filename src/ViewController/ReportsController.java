package ViewController;

import Model.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportsController implements Initializable {

    /**
     * label to display the current user id
     */
    @FXML
    private Label userIdText;

    /**
     * rb for getting appts by type
     */
    @FXML
    private RadioButton apptsByTypeRB;

    /**
     * rb for getting appts by month
     */
    @FXML
    private RadioButton apptsByMonthRB;

    /**
     * rb for getting schedule for contacts
     */
    @FXML
    private RadioButton contactSchedulesRB;

    /**
     * rb for getting customers added within past month
     */
    @FXML
    private RadioButton customersAddedRB;

    /**
     * txt area for showing report data
     */
    @FXML
    private TextArea txtArea;

    /**
     * Initializes controller class. Sets up the tg and rbs.
     * Manages selection of rbs.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final ToggleGroup group = new ToggleGroup();
        apptsByTypeRB.setToggleGroup(group);
        apptsByTypeRB.setSelected(true);
        apptsByMonthRB.setToggleGroup(group);
        contactSchedulesRB.setToggleGroup(group);
        customersAddedRB.setToggleGroup(group);

        // Initialize with default radio button selected
        handleApptsByType();

        // Handle appointment by type radio button selected
        apptsByTypeRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    txtArea.setText("");
                    handleApptsByType();
                }
            }
        });

        apptsByMonthRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    txtArea.setText("");
                    try {
                        ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();
                        String month;

                        HashMap<String, Integer> monthCountMap = new HashMap<String, Integer>();


                        for (int i = 0; i < apptList.size(); i++) {
                            String date = (apptList.get(i).getStartingTimeProperty().getValue());
                            month = date.split(" ")[0].split("-")[1];

                            if (monthCountMap.containsKey(month)) {
                                // If month is present in monthCountMap,
                                // incrementing it's count by 1
                                monthCountMap.put(month, monthCountMap.get(month) + 1);
                            } else {
                                // If month is not present in monthCountMap,
                                // putting this month to monthCountMap with 1 as it's value
                                monthCountMap.put(month, 1);
                            }
                        }
                        String result = "";

                        for (Map.Entry entry : monthCountMap.entrySet()) {
                            String monthString = convertNumberToMonth(entry.getKey().toString());
                            result += monthString + ": " + entry.getValue() + "\n";
                        }

                        txtArea.setText(result);

                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
            }
        });

        contactSchedulesRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    txtArea.setText("");

                    try {
                        ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();
                        ObservableList<Contact> contactList = ContactDAO.getAllRecords();


                        String contactName;
                        int id;
                        int contactId;

                        int apptId;
                        int customerId;
                        String title;
                        String type;
                        String description;
                        String start;
                        String end;

                        String result = "";

                        for (int i = 0; i < contactList.size(); i++) {
                            contactName = contactList.get(i).getNameProperty().getValue();
                            id = contactList.get(i).getIdProperty().getValue();

                            for (int j = 0; j < apptList.size(); j++) {
                                contactId = apptList.get(j).getContactIdProperty().getValue();

                                if (contactId == id) {

                                    apptId = apptList.get(j).getIdProperty().getValue();
                                    customerId = apptList.get(j).getContactIdProperty().getValue();
                                    title = apptList.get(j).getNameProperty().getValue();
                                    type = apptList.get(j).getTypeProperty().getValue();
                                    description = apptList.get(j).getDescriptionProperty().getValue();
                                    start = apptList.get(j).getStartingTimeProperty().getValue();
                                    end = apptList.get(j).getEndingTimeProperty().getValue();

                                    result += "Contact: " + contactName + "\n";
                                    result += " | Appointment ID: " + apptId;
                                    result += " | Customer ID: " + customerId;
                                    result += " | Title: " + title;
                                    result += " | Type: " + type;
                                    result += " | Description: " + description;
                                    result += " | Starting Time: " + start;
                                    result += " | Ending Time: " + end + " |\n";
                                }
                            }
                        }

                        txtArea.setText(result);

                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, exception);
                    }
                }
            }
        });

        customersAddedRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasSelected, Boolean isSelected) {
                if (isSelected) {
                    txtArea.setText("");

                    try {
                        ObservableList<Customer> customerList = CustomerDAO.getAllRecordsAddedThisMonth();

                        System.out.println(customerList.size());

                        int count = customerList.size();

                        txtArea.setText(Integer.toString(count));

                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    /**
     * User to convert number to month
     *
     * @param mm number to be converted to a month name
     * @return      month name converted from number
     */
    private String convertNumberToMonth(String mm) {
        switch (mm) {
            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUN";
            case "07":
                return "JUL";
            case "08":
                return "AUG";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
            default:
                return "";
        }
    }

    /**
     * Gets report to display appointments per type such as in-person, email, or phone.
     */
    private void handleApptsByType() {
        try {
            ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();

            int inPersonCount = 0;
            int emailCount = 0;
            int phoneCount = 0;

            for (int i = 0; i < apptList.size(); i++) {
                if (apptList.get(i).getTypeProperty().getValue().equals("In-Person")) {
                    inPersonCount++;
                } else if (apptList.get(i).getTypeProperty().getValue().equals("Email")) {
                    emailCount++;
                } else if (apptList.get(i).getTypeProperty().getValue().equals("Phone")){
                    phoneCount++;
                } else {
                    continue;
                }
            }

            String result = "";

            result += "In-Person appointments: " + inPersonCount + "\n";
            result += "Email appointments: " + emailCount + "\n";
            result += "Phone appointments: " + phoneCount + "\n";

            txtArea.setText(result);

        } catch (ClassNotFoundException | SQLException exception) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Gets user id data from the customer controller to display in a label.
     *
     * @param id
     */
    public void getData(int id) {
        int userId = id;
        userIdText.setText("User ID: " + userId);
    }

    /**
     * Returns the user back to the customer view.
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

            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            controller.getData(userId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

}
