package util;

import uuid.UuidHelper;

import java.util.ResourceBundle;

public class Config {

    private final static ResourceBundle configBundle = ResourceBundle.getBundle("config");

    public final static String LOG_HOST = configBundle.getString("log_host");

    public final static String LOG_USER = configBundle.getString("log_user");

    public final static String LOG_PW = configBundle.getString("log_pw");

    public final static String TEST_URL = configBundle.getString("test_url") + UuidHelper.getInstance().getUuid();

    public final static String CODECAST_TEST_URL = configBundle.getString("codecast_test_url") + UuidHelper.getInstance().getUuid();

    public final static String QUESTIONAIRE_URL = configBundle.getString("questionaire_url") + UuidHelper.getInstance().getUuid();

    public final static String CODECAST_PLUGIN_ID = configBundle.getString("codecast_plugin_id");

    public final static String EDITOR_CSV_FILENAME = UuidHelper.getInstance().getUuid().toString() + "_editor.csv";
    public final static String EDITOR_CSV_PATH = System.getProperty("user.home") + "/" + "codecast" + "/" + EDITOR_CSV_FILENAME;

    public final static String PLAYER_CSV_FILENAME = UuidHelper.getInstance().getUuid().toString() + "_player.csv";
    public final static String PLAYER_CSV_PATH = System.getProperty("user.home") + "/" + "codecast" + "/" + PLAYER_CSV_FILENAME;
}
