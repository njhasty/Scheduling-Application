package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Country {

    /**
     * Country id property
     */
    private IntegerProperty idProperty;

    /**
     * Country name property
     */
    private StringProperty countryNameProperty;

    /**
     * Establishes country object
     */
    public Country() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryNameProperty = new SimpleStringProperty();
    }

    /**
     * Getter for country idProperty
     * @return
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Setter for countryId
     * @param id
     */
    public void setCountryId(int id) {
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
     * Getter for countryNameProperty
     * @return
     */
    public StringProperty getCountryNameProperty() {
        return countryNameProperty;
    }

    /**
     * Setter for countryNameProperty
     * @param name
     */
    public void setCountryCountryName(String name) {
        this.countryNameProperty.set(name);
    }

    /**
     * Setter for countryNameProperty
     * @param countryNameProperty
     */
    public void setCountryNameProperty(StringProperty countryNameProperty) {
        this.countryNameProperty = countryNameProperty;
    }

}