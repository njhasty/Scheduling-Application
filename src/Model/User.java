package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    /**
     * user id property
     */
    private IntegerProperty idProperty;

    /**
     * user username property
     */
    private StringProperty usernameProperty;

    /**
     * user password property
     */
    private StringProperty passwordProperty;

    /**
     * Establishes user object
     */
    public User() {
        this.idProperty = new SimpleIntegerProperty();
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
    }

    /**
     * Getter for user id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Setter for idProperty
     * @param id
     */
    public void setUserId(int id) {
        this.idProperty.set(id);
    }

    /**
     * Setter for idProperty
     * @param idProperty
     */
    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }

    /**
     * Getter for user username property
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    /**
     * Setter for usernameProperty
     * @param username
     */
    public void setUserUsername(String username) {
        this.usernameProperty.set(username);
    }

    /**
     * Setter for usernameProperty
     * @param usernameProperty
     */
    public void setUsernameProperty(StringProperty usernameProperty) {
        this.usernameProperty = usernameProperty;
    }

    /**
     * Getter for user password property
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    /**
     * Setter for passwordProperty
     * @param password
     */
    public void setUserPassword(String password) {
        this.passwordProperty.set(password);
    }

    /**
     * Setter for passwordProperty
     * @param passwordProperty
     */
    public void setPasswordProperty(StringProperty passwordProperty) {
        this.passwordProperty = passwordProperty;
    }

}