package org.user.appimagemanager.window;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.user.appimagemanager.model.Desktop;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainWindow {
    Logger l = LoggerFactory.getLogger(MainWindow.class);


    public List<File> files = new ArrayList<>();
    public ObservableList<String> filenames = FXCollections.observableArrayList();

    MainWindow(){
        refreshFilesList();
    }


    /**
     * refresh list of files in .local/share/applications
     */
    public void refreshFilesList(){
        l.info("Searching for Desktop files");

        files.addAll(FileUtils.listFiles(
                new File("/home/" + System.getenv("USER") + "/.local/share/applications/"),
                new String[] {"desktop", "Desktop"},
                true));

        for ( File file: files ) {
            filenames.add(file.getName());
        }


        l.info("Found " + String.valueOf(files.size()) + " files");
    }

    /**
     * fulfill main window's fields with data from provided file
     * @param cont
     * @param file
     */
    public void setUpFields(ControllerMainW cont, Desktop file){
        l.debug("set up fields for a controller");

        cont.filename.setText(file.getName());
        cont.name.setText(file.getVisibleName());
        cont.exec.setText(file.getExec());
        cont.comment.setText(file.getComment());
        cont.icon.setText(file.getIcon());
        cont.categories.setText(file.getCategories());
    }

    /**
     * searches for the first occurrence of a file
     * @param name filename to identify by
     * @return null if doesn't exist
     */
    public File getSpecificFile(String name){
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

}
