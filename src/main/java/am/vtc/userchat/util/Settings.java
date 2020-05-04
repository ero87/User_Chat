package am.vtc.userchat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private Properties properties;
    private Settings() {
        this.load();
    }

    private void load(){
        this.properties = new Properties();
        try (InputStream inputStream = Settings.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            this.properties.load(inputStream);
        } catch (IOException e) {
           throw  new RuntimeException();
        }
    }

    public String getString(String key) {
        return this.properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(this.properties.getProperty(key));
    }

    public static Settings getInstance() {
        return SettingsInstanceCreator.settings;
    }

    private static class SettingsInstanceCreator {
        private static final Settings settings = new Settings();
    }
}
