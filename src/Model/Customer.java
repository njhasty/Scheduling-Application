package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

    /**
     * customer id property
     */
    private IntegerProperty idProperty;

    /**
     * customer city id property
     */
    private IntegerProperty cityIdProperty;

    /**
     * customer name property
     */
    private StringProperty nameProperty;

    /**
     * customer address property
     */
    private StringProperty addressProperty;

    /**
     * customer postal code property
     */
    private StringProperty postalCodeProperty;

    /**
     * customer phone number property
     */
    private StringProperty phoneNumberProperty;

    /**
     * Establishes a customer object
     */
    public Customer() {
        this.idProperty = new SimpleIntegerProperty();
        this.cityIdProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.addressProperty = new SimpleStringProperty();
        this.postalCodeProperty = new SimpleStringProperty();
        this.phoneNumberProperty = new SimpleStringProperty();
    }

    /**
     * Getter for customer id property
     * @return  the customer id
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     *
     * @param id
     */
    public void setCustomerId(int id) {
        this.idProperty.set(id);
    }

    /**
     *
     * @param idProperty
     */
    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }

    /**
     * Getter for customer city id property
     * @return  the city id
     */
    public IntegerProperty getCityIdProperty() {
        return cityIdProperty;
    }

    /**
     * Setter for cityIdProperty
     * @param id
     */
    public void setCityId(int id) {
        this.cityIdProperty.set(id);
    }

    /**
     * Setter for cityIdProperty
     * @param cityIdProperty
     */
    public void setCityIdProperty(IntegerProperty cityIdProperty) {
        this.cityIdProperty = cityIdProperty;
    }

    /**
     * Getter for customer name property
     * @return  customer name
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }

    /**
     * Setter for nameProperty
     * @param name
     */
    public void setCustomerName(String name) {
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
     * Getter for customer address property
     * @return  the customer address
     */
    public StringProperty getAddressProperty() {
        return addressProperty;
    }

    /**
     * Setter for addressProperty
     * @param address
     */
    public void setCustomerAddress(String address) {
        this.addressProperty.set(address);
    }

    /**
     * Setter for addressProperty
     * @param addressProperty
     */
    public void setAddressProperty(StringProperty addressProperty) {
        this.addressProperty = addressProperty;
    }

    /**
     * Getter for customer postal code property
     * @return  the customer postal code
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }

    /**
     * Setter for postalCodeProperty
     * @param postalCode
     */
    public void setCustomerPostalCode(String postalCode) {
        this.postalCodeProperty.set(postalCode);
    }

    /**
     * Setter for postalCodeProperty
     * @param postalCodeProperty
     */
    public void setPostalCodeProperty(StringProperty postalCodeProperty) {
        this.postalCodeProperty = postalCodeProperty;
    }

    /**
     * Getter for customer phone number property
     * @return  customer phone number
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }

    /**
     * Setter for phoneNumberProperty
     * @param phoneNumber
     */
    public void setCustomerPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty.set(phoneNumber);
    }

    /**
     * Setter for phoneNumberProperty
     * @param phoneNumberProperty
     */
    public void setPhoneNumberProperty(StringProperty phoneNumberProperty) {
        this.phoneNumberProperty = phoneNumberProperty;
    }

}
