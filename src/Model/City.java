package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    /**
     * city id property
     */
    private IntegerProperty idProperty;

    /**
     * city country id property
     */
    private IntegerProperty countryIdProperty;

    /**
     * city name property
     */
    private StringProperty cityNameProperty;

    /**
     * Establishes city object
     */
    public City() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryIdProperty = new SimpleIntegerProperty();
        this.cityNameProperty = new SimpleStringProperty();
    }

    /**
     * Getter for city id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Setter for city id property
     * @param id
     */
    public void setDivisionId(int id) {
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
     * Getter for city country id property
     */
    public IntegerProperty getCountryIdProperty() {
        return countryIdProperty;
    }

    /**
     * Setter for city country id property
     * @param id
     */
    public void setDivisionCountryId(int id) {
        this.countryIdProperty.set(id);
    }

    /**
     * Setter for countryIdProperty
     * @param countryIdProperty
     */
    public void setCountryIdProperty(IntegerProperty countryIdProperty) {
        this.countryIdProperty = countryIdProperty;
    }

    /**
     * Getter for city name property
     */
    public StringProperty getCityNameProperty() {
        return cityNameProperty;
    }

    /**
     * Setter for city name property
     * @param name
     */
    public void setDivisionDivisionName(String name) {
        this.cityNameProperty.set(name);
    }

    /**
     * Setter for cityNameProperty
     * @param cityNameProperty
     */
    public void setCityNameProperty(StringProperty cityNameProperty) {
        this.cityNameProperty = cityNameProperty;
    }

}
