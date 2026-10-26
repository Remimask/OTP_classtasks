package app.controller;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class languageToggle {
    private Locale locale;
    public static final Locale FINNISH = new Locale("fi", "FI");
    public static final Locale SWEDISH = new Locale("sv", "SV");
    private ResourceBundle translations;
    private final String bundleBasename = "Translations";

    public languageToggle(Locale initLocale){
        setLocale(initLocale);
    }
    public void setLocale(Locale newLocale){
        this.locale = newLocale;
        this.translations = ResourceBundle.getBundle(bundleBasename, locale);
    }
    public String getString(String key){
        return translations.getString(key);
    }
}
