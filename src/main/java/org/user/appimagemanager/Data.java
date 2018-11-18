package org.user.appimagemanager;

/**
 * class which stores static strings
 */
public class Data {
    public static final String appName = "Shortcuts";
    public static final String mainFXMLPath = "/MainFX.fxml";

    /**
     * path to a folder containing per user desktop files
     */
    public static String applicationPath = "/home/" + System.getenv("USER") + "/.local/share/applications";
}
