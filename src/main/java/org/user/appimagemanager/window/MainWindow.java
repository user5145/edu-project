package org.user.appimagemanager.window;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.appimagemanager.Data;
import org.user.appimagemanager.model.Desktop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainWindow {
    Logger l = LoggerFactory.getLogger(MainWindow.class);



    //region properties & constructors
    MainWindow(){
        refreshFileList();
    }


    public List<File> files = new ArrayList<>();
    public ObservableList<String> filenames = FXCollections.observableArrayList();
    public StringProperty filename = new SimpleStringProperty();
    public StringProperty name = new SimpleStringProperty();
    public StringProperty exec = new SimpleStringProperty();
    public StringProperty comment = new SimpleStringProperty();
    public StringProperty icon = new SimpleStringProperty();
    public StringProperty categories = new SimpleStringProperty();
    //endregion



    //region functions
    /**
     * refresh list of files in .local/share/applications
     */
    public void refreshFileList(){
        l.info("Searching for Desktop files");

        files.addAll(FileUtils.listFiles(
                new File(Data.applicationPath),
                new String[] {"desktop", "Desktop"},
                true));

        for ( File file: files ) {
            filenames.add(file.getName());
        }


        l.info("Found " + String.valueOf(files.size()) + " files");
    }

    /**
     * fulfill main window's fields with data from provided file
     * @param file
     */
    public void setUpFields(Desktop file){
        l.debug("set up fields for a controller");

        filename.setValue(file.getName());
        name.setValue(file.getVisibleName());
        exec.setValue(file.getExec());
        comment.setValue(file.getComment());
        icon.setValue(file.getIcon());
        categories.setValue(file.getCategories());
    }

    /**
     * searches for the first occurrence of a file
     * @param name filename to identify by
     * @return null if doesn't exist
     */
    public File getSpecificFile(@NotNull String name){
        l.debug("searching for: " + name);

        File file;
        //iterate over a list of files
        Iterator it = files.iterator();
        while(it.hasNext()) {
            file = (File) it.next();

            //if it's it, return it
            if (file.getName().equals(name))
                return file;
        }

        return null;
    }


    public void Save() {
        l.debug("exec models save function");
        if(filename.getValueSafe().isBlank() || exec.getValueSafe().isBlank() || name.getValueSafe().isBlank()){
            l.warn("tried to save a file without a name, exec or filename, interrupted");
            return;
        }

        Desktop d = new Desktop(Data.applicationPath + "/" + addDesktopExt(filename.getValue()));
        d.setVisibleName(name.getValueSafe());
        d.setComment(comment.getValueSafe());
        d.setExec(exec.getValueSafe());
        d.setIcon(icon.getValueSafe());
        d.setCategories(categories.getValueSafe());

        try {
            d.save();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * check if the String end with .desktop and if not add the suffix
     * @param s
     */
    private String addDesktopExt(String s) {
        if(!s.toLowerCase().endsWith(".desktop"))
            s+=".desktop";

        return s;
    }
    //endregion
}
