package ViewController;

import Model.AppointmentDAO;
import Model.User;
import Model.UserDAO;
import Utilities.Location;
import Utilities.Translation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    /**
     * text for displaying error login message
     */
    @FXML
    private Label errorMessage;
    /**
     * label for displaying title
     */
    @FXML
    private Label titleTxt;

    /**
     * text field for collecting username
     */
    @FXML
    private TextField usernameTxtField;

    /**
     * text field for collecting password
     */
    @FXML
    private PasswordField passwordTxtField;

    /**
     * button for logging in user
     */
    @FXML
    private Button loginBtn;

    /**
     * label for showing application is loading
     */
    @FXML
    private Label loadingTxt;

    /**
     * label for displaying country and time zone of user system
     */
    @FXML
    private Label userLocationTxt;

    /**
     * Initializes controller class. Gets user location data from system and adjusts application language.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("login", Locale.getDefault());
        titleTxt.setText(resourceBundle.getString("title"));
        usernameTxtField.setPromptText(resourceBundle.getString("username"));
        passwordTxtField.setPromptText(resourceBundle.getString("password"));
        loginBtn.setText(resourceBundle.getString("login"));

        String userLocation = Location.getUserLocationInfo();
        userLocationTxt.setText(userLocation);
    }

    /**
     * Manages the login button pressed. Checks username and password data is valid and if incorrect it gives in error message
     * Valid username and password inputs will result in user going to customer screen.
     * Logs the attempt to login_activity.txt.
     *
     * Lambda expression validates username and password inputs from user and iterates through each item in user to validate
     *
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void loginBtnPressed(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        String userLanguage = Translation.getUserLanguage();
        if (userLanguage.equals("fr")) {
            loadingTxt.setText("Se charge...");
        } else {
            loadingTxt.setText("Loading...");
        }
        loginBtn.setDisable(true);

        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();

//        Logger log = Logger.getLogger("login_activity.txt");
        PrintWriter pw = new PrintWriter(new FileOutputStream(
                new File("login_activity.txt"),
                true /* append = true */));

        ObservableList<User> userList = UserDAO.getAllRecords();


        userList.forEach((user) -> {
            String dbUsername = user.getUsernameProperty().getValue();
            String dbPassword = user.getPasswordProperty().getValue();


            if (username.equals(dbUsername) && password.equals(dbPassword)) {
                errorMessage.setText("");
                try {
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());

                    pw.append("Username entered: " + dbUsername + ", Password entered: " + dbPassword + ", DateTime: " + timestamp + ", SUCCESS\n");
                    pw.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomersView.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    int userId;
                    try {
                        userId = UserDAO.getCurrentUserId(usernameTxtField.getText());
                        CustomersController controller = loader.getController();
                        controller.getData(userId);
                    } catch (ClassNotFoundException | SQLException exception) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                    }

                    stage.setScene(scene);
                    stage.show();

                    showApptAlert();

                } catch (IOException | ClassNotFoundException | SQLException exception) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                }

            } else {
                try {
                    if (userLanguage.equals("fr")) {
                        errorMessage.setText("Message d'erreur : Nom d'utilisateur et/ou mot de passe incorrects.");
                    } else {
                        errorMessage.setText("Error Message: Incorrect username and or password.");
                        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());

                        pw.append("Username entered: " + username + ", Password entered: " + password + ", DateTime: " + timestamp + ", FAILURE\n");
                        pw.close();
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
//                    String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
//                    log.info("Username: " + dbUsername + ", DateTime: " + timestamp + ", Failure");
                }
                try {

                } catch (SecurityException exception) {
//                    log.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
//                    String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
//                    log.info("Username: " + dbUsername + ", DateTime: " + timestamp + ", Failure");
                }
            }
        });

        loadingTxt.setText("");
        loginBtn.setDisable(false);

    }

    /**
     * Displays alert to the user after login that indicates if user has appt within next 15 minutes
     *
     * Lambda expressions used to show alert message and take user input response
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void showApptAlert() throws ClassNotFoundException, SQLException {
        boolean result = AppointmentDAO.getAllRecordsInNext15Minutes();
    }
}


