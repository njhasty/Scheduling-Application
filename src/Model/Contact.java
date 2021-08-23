package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {

    /**
     * The contact id property
     */
    private IntegerProperty idProperty;

    /**
     * The contact name property
     */
    private StringProperty nameProperty;

    /**
     * The contact email property
     */
    private StringProperty emailProperty;

    /**
     * Establishes a contact object
     */
    public Contact() {
        this.idProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
    }

    /**
     * Getter for contact idProperty
     * @return
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Setter for contactId
     * Sets the contact id property
     * @param id
     */
    public void setContactId(int id) {
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
     * Getter for contact name property
     * @return
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }

    /**
     * Setter for contact name property
     * @param name
     */
    public void setContactName(String name) {
        this.nameProperty.set(name);
    }

    /**
     * Setter for nameProperty
     * @param nameProperty
     */
    public void setNameProperty(StringProperty nameProperty) {
        this.nameProperty = nameProperty;
    }

    /**
     * Getter for contact email property
     * @return
     */
    public StringProperty getEmailProperty() {
        return emailProperty;
    }

    /**
     * Setter for contact email property
     * @param email
     */
    public void setContactEmail(String email) {
        this.emailProperty.set(email);
    }

    /**
     * Setter for email property
     * @param emailProperty
     */
    public void setEmailProperty(StringProperty emailProperty) {
        this.emailProperty = emailProperty;
    }

}
