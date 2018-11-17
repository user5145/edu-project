package org.user.appimagemanager.window;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.appimagemanager.ApplicationFX;
import org.user.appimagemanager.Data;
import org.user.appimagemanager.model.TemplateChangeListenerContAccess;
import org.user.appimagemanager.model.Desktop;

import java.io.IOException;

/**
 * main controller of this application controls the main window
 */
public class ControllerMainW {


    //region Properties
    private static final Logger l = LoggerFactory.getLogger(ControllerMainW.class);

    private MainWindow model = new MainWindow();
    private Stage stage;
    private BorderPane root;

    private TemplateChangeListenerContAccess onListViewValueChange = new TemplateChangeListenerContAccess(this) {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            l.info("new field selected: " + newValue);

            ControllerMainW contMW = (ControllerMainW) cont;
            String uri = contMW.model.getSpecificFile(String.valueOf(newValue)).getAbsolutePath();
            contMW.model.setUpFields(contMW, new Desktop(uri));
        }
    };

    // for whatever reason fxml doesn't initialize ListView
    @FXML
    private ListView<String> listView = new ListView<String>();
    @FXML
    public TextField filename;
    @FXML
    public TextField name;
    @FXML
    public TextField exec;
    @FXML
    public TextField comment;
    @FXML
    public TextField icon;
    @FXML
    public TextField categories;
    //endregion


    //region constructors

    /**
     * fx controllers are required to contain default public constructors
     */
    public ControllerMainW() {
    }

    ;

    /**
     * It seems to be safer to use javafx initialize function rather than the constructor for javafx related stuff
     */
    @FXML
    public void initialize() {
        l.info("initialized fx window");

        //add values to listview
        listView.getItems().addAll(model.filenames);

        //onValueChange.setModel(model);
        listView.getSelectionModel().selectedItemProperty().addListener(onListViewValueChange);
    }
    //endregion


    //region actions
    public void onSelect(Event e) {
        l.info("button clicked");

        String uri;
        try {
            uri= new FileChooser().showOpenDialog(stage).getAbsolutePath();
        } catch (NullPointerException ex) {
            l.info("no file specified");
            return;
        }

        model.setUpFields(this, new Desktop(uri));
    }

    public void onDrag(Event e) {
        l.info("File dragged over");
    }
    //endregion


    //region functions
    /**
     * prepares the window
     *
     * @throws IOException
     * @throws ExceptionInInitializerError requires Stage to be initialized
     */
    public void setMainWindow() {
        if (getStage() == null)
            new ExceptionInInitializerError().printStackTrace();

        try {
            root = FXMLLoader.load(getClass().getResource(Data.mainFXMLPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        getStage().setScene(scene);
        getStage().setMinHeight(400);
        getStage().setMinWidth(500);
        getStage().setTitle(Data.appName);
    }

    /**
     * shows the window
     */
    public void show() {
        getStage().show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //endregion

}
