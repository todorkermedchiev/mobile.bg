package bg.sofia.uni.fmi.dp.mobile.cli.localization;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocalizationUtil {
    public static ResourceBundle getBundle(String baseName, Locale locale) {
        return ResourceBundle.getBundle(baseName, locale, new UTF8Control());
    }

    public static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws java.io.IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = bundleName + ".properties";
            try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(loader.getResourceAsStream(resourceName)), StandardCharsets.UTF_8)) {
                return new PropertyResourceBundle(reader);
            }
        }
    }
}
