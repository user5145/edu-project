package org.user.appimagemanager.window;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.appimagemanager.Data;
import org.user.appimagemanager.model.DesktopType;
import org.user.appimagemanager.model.TemplateChangeListenerContAccess;
import org.user.appimagemanager.model.Desktop;

import java.io.File;
import java.io.IOException;

/**
 * main controller of this application controls the main window
 */
public class ControllerMainW {
    private static final Logger l = LoggerFactory.getLogger(ControllerMainW.class);



    //region Properties
    private MainWindow model = new MainWindow();
    private Stage stage;
    private BorderPane root;

    // for whatever reason fxml doesn't initialize ListView
    @FXML
    private ListView<String> listView = new ListView<String>();
    @FXML
    private TextField filename;
    @FXML
    private TextField name;
    @FXML
    private TextField exec;
    @FXML
    private TextField comment;
    @FXML
    private TextField icon;
    @FXML
    private TextField categories;
    @FXML
    private CheckBox terminal;
    @FXML
    private CheckBox hidden;
    @FXML
    private ChoiceBox choiceBoxDesktopType;

    /**
     * function that should run when listview selects new file to load
     */
    private TemplateChangeListenerContAccess onListViewValueChange = new TemplateChangeListenerContAccess(this) {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            l.info("new field selected: " + newValue);

            ControllerMainW contMW = (ControllerMainW) cont;
            String uri = contMW.model.getSpecificFile(String.valueOf(newValue)).getAbsolutePath();
            contMW.model.setUpFields(new Desktop(uri));
        }
    };
    //endregion



    //region constructors

    /**
     * fx controllers are required to contain default public constructors
     */
    public ControllerMainW() {
    }



    /**
     * It seems to be safer to use javafx initialize function rather than the constructor for javafx related stuff
     */
    @FXML
    public void initialize() {
        l.info("initialized fx window");

        //add values to listview
        listView.getItems().addAll(model.filenames);

        //apply listener
        listView.getSelectionModel().selectedItemProperty().addListener(onListViewValueChange);

        //set up combo box values
        choiceBoxDesktopType.getItems().setAll(DesktopType.values());
        choiceBoxDesktopType.getSelectionModel().selectFirst();

        //bind view fields' values to model's properties to have true mvc
        model.filename.bindBidirectional(filename.textProperty());
        model.name.bindBidirectional(name.textProperty());
        model.exec.bindBidirectional(exec.textProperty());
        model.comment.bindBidirectional(comment.textProperty());
        model.icon.bindBidirectional(icon.textProperty());
        model.categories.bindBidirectional(categories.textProperty());
        model.terminal.bindBidirectional(terminal.selectedProperty());
        model.hidden.bindBidirectional(hidden.selectedProperty());
        model.type.bindBidirectional(choiceBoxDesktopType.valueProperty());
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

        model.setUpFields(new Desktop(uri));
    }

    public void onSave(Event e) {
        l.info("Save button clicked");

        model.Save();
    }

    public void onRemove(Event e) {
        l.info("Remove button clicked");

        File f = model.getSpecificFile(model.filename.getValue());
        if(f != null)
            model.Remove(f);
        else
            l.warn("cannot remove the file, it doesn't exists");
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
