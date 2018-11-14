package org.user.appimagemanager;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.appimagemanager.window.ControllerMainW;

import java.io.IOException;


public class ApplicationFX extends Application {
    private static final Logger l = LoggerFactory.getLogger(ApplicationFX.class);

    private ControllerMainW contmw = new ControllerMainW();

    /**
     * init program's gui
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) {
        l.debug("java: " + System.getProperty("java.version") + ", javafx: " + System.getProperty("javafx.version"));

        contmw.setStage(stage);
        contmw.setMainWindow();
        contmw.show();
    }

    /**
     * main function
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}