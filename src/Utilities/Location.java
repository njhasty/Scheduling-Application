package Utilities;

import java.util.Locale;
import java.util.TimeZone;

public class Location {

    /**
     * Gets user's system location
     * @return  string containing default country and time zone of user per system specifications
     */
    public static String getUserLocationInfo() {

        Locale userLocale = Locale.getDefault();
        String UserLocaleCountry = userLocale.getDisplayCountry();

        TimeZone userTimeZone  = TimeZone.getDefault();
        String userZone = userTimeZone.getDisplayName(true, 0);

        String locale = UserLocaleCountry + " (" + userZone + ")";

        return locale;

    }

}