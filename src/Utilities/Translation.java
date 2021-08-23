package Utilities;

import java.util.Locale;

public class Translation {

    /**
     * Reads user location per system specifications
     * @return  string that specifies user location
     */
    public static String getUserLanguage() {
        String userLocation = Locale.getDefault().getLanguage();

        return userLocation;
    }

}
