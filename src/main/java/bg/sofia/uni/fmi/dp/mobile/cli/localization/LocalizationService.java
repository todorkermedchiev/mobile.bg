package bg.sofia.uni.fmi.dp.mobile.cli.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationService {
    private final ResourceBundle bundle;

    public LocalizationService(Locale locale) {
        this.bundle = ResourceBundle.getBundle("i18n/messages", locale);
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}

