package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {

    /**
     * The id property for an appointment
     */
    private IntegerProperty idProperty;

    /**
     * The customer id property for an appointment
     */
    private IntegerProperty custIdProperty;

    /**
     * The user id property for an appointment
     */
    private IntegerProperty userIdProperty;

    /**
     * The contact id property for an appointment
     */
    private IntegerProperty contactIdProperty;

    /**
     * The name property for an appointment
     */
    private StringProperty nameProperty;

    /**
     * The description property for an appointment
     */
    private StringProperty descriptionProperty;

    /**
     * The location property for an appointment
     */
    private StringProperty locationProperty;

    /**
     * The type property for an appointment
     */
    private StringProperty typeProperty;

    /**
     * The starting time property for an appointment
     */
    private StringProperty startingTimeProperty;

    /**
     * The ending time property for an appointment
     */
    private StringProperty endingTimeProperty;

    /**
     * Sets up an appointment object
     */
    public Appointment() {
        this.idProperty = new SimpleIntegerProperty();
        this.custIdProperty = new SimpleIntegerProperty();
        this.userIdProperty = new SimpleIntegerProperty();
        this.contactIdProperty = new SimpleIntegerProperty();

        this.nameProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.locationProperty = new SimpleStringProperty();
        this.typeProperty = new SimpleStringProperty();
        this.startingTimeProperty = new SimpleStringProperty();
        this.endingTimeProperty = new SimpleStringProperty();
    }

    /**
     * Getter for appointment id property
     *
     * @return appointment id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Setter for appointment id
     *
     * @param id appointment id
     */
    public void setAppointmentId(int id) {
        this.idProperty.set(id);
    }

    /**
     * Setter for appointment id property
     *
     * @param idProperty    the appointment id property
     */
    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }

    /**
     * Getter for customer id
     *
     * @return  the customer id property
     */
    public IntegerProperty getCustIdProperty() {
        return custIdProperty;
    }

    /**
     * Setter for customer id
     *
     * @param id    the customer id
     */
    public void setCustomerId(int id) {
        this.custIdProperty.set(id);
    }

    /**
     * Setter for customer id property
     *
     * @param idProperty    the customer id property
     */
    public void setCustIdProperty(IntegerProperty idProperty) {
        this.custIdProperty = idProperty;
    }

    /**
     * Getter for user id property
     *
     * @return  the user id property
     */
    public IntegerProperty getUserIdProperty() {
        return userIdProperty;
    }

    /**
     * Setter for user id
     *
     * @param id    the user id
     */
    public void setUserId(int id) {
        this.userIdProperty.set(id);
    }

    /**
     * Setter for user id property
     *
     * @param idProperty    the user id property
     */
    public void setUserIdProperty(IntegerProperty idProperty) {
        this.userIdProperty = idProperty;
    }

    /**
     * Getter for contact id property
     *
     * @return  the contact id property
     */
    public IntegerProperty getContactIdProperty() {
        return contactIdProperty;
    }

    /**
     * Setter for contact id
     *
     * @param id    the contact id
     */
    public void setContactId(int id) {
        this.contactIdProperty.set(id);
    }

    /**
     * Setter for contact id property
     *
     * @param idProperty    the contact id property
     */
    public void setContactIdProperty(IntegerProperty idProperty) {
        this.contactIdProperty = idProperty;
    }

    /**
     * Getter for title property
     *
     * @return  the title property
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }

    /**
     * Setter for appointment title
     *
     * @param title
     */
    public void setAppointmentTitle(String title) {
        this.nameProperty.set(title);
    }

    /**
     * Setter for appointment title property
     *
     * @param nameProperty
     */
    public void setNameProperty(StringProperty nameProperty) {
        this.nameProperty = nameProperty;
    }

    /**
     * Getter for description property
     *
     * @return  the description property
     */
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    /**
     * Setter for appointment description
     *
     * @param desc  the appointment description
     */
    public void setAppointmentDescription(String desc) {
        this.descriptionProperty.set(desc);
    }

    /**
     * Setter for description property
     *
     * @param descriptionProperty   the description property
     */
    public void setDescriptionProperty(StringProperty descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }

    /**
     * Getter for location property
     *
     * @return  the location property
     */
    public StringProperty getLocationProperty() {
        return locationProperty;
    }

    /**
     * Setter for appointment location
     *
     * @param location  the appointment location
     */
    public void setAppointmentLocation(String location) {
        this.locationProperty.set(location);
    }

    /**
     * Setter for location property
     *
     * @param locationProperty  the location property
     */
    public void setLocationProperty(StringProperty locationProperty) {
        this.locationProperty = locationProperty;
    }

    /**
     * Getter for typeProperty
     * @return typeProperty
     */
    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    /**
     * Setter for typeProperty
     * @param type type
     */
    public void setAppointmentType(String type) {
        this.typeProperty.set(type);
    }

    /**
     * Setter for typeProperty
     * @param typeProperty typeProperty
     */
    public void setTypeProperty(StringProperty typeProperty) {
        this.typeProperty = typeProperty;
    }

    /**
     * Getter for startingTimeProperty
     * @return startingTimeProperty
     */
    public StringProperty getStartingTimeProperty() {
        return startingTimeProperty;
    }

    /**
     * Setter for startingTimeProperty
     * @param time
     */
    public void setAppointmentStartTime(String time) {
        this.startingTimeProperty.set(time);
    }

    /**
     * Setter for startingTimeProperty
     * @param startingTimeProperty
     */
    public void setStartingTimeProperty(StringProperty startingTimeProperty) {
        this.startingTimeProperty = startingTimeProperty;
    }

    /**
     * Getter for endingTimeProperty
     * @return
     */
    public StringProperty getEndingTimeProperty() {
        return endingTimeProperty;
    }

    /**
     * Setter for endingTimeProperty
     * @param time endingTimeProperty
     */
    public void setAppointmentEndTime(String time) {
        this.endingTimeProperty.set(time);
    }

    /**
     * Setter for endingTimeProperty
     * @param endingTimeProperty endingTimeProperty
     */
    public void setEndingTimeProperty(StringProperty endingTimeProperty) {
        this.endingTimeProperty = endingTimeProperty;
    }

}