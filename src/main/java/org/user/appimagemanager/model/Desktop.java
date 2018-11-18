package org.user.appimagemanager.model;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Represents a Desktop file
 * It's constructor tries to load all the important data and store it as properties
 */
public class Desktop extends File {
    Logger l = LoggerFactory.getLogger(Desktop.class);



    /**
     * Create a file with additional properties to store the most common data
     * Tries to load data from the file if that file exists
     * @param uri path to the file
     */
    public Desktop(String uri) {
        super(uri);

        try {
            lines = FileUtils.readLines(this);
        } catch (IOException e) { e.printStackTrace(); return;}

        //searches for appropriate lines and saves their indexes (AKA a bunch of ifs)
        for(int i=0; i < lines.size(); i++){
            String line = (String) lines.get(i);
            if (line.trim().startsWith("Name="))
                visilbeNameIndex= i;
            else if (line.trim().startsWith("Comment="))
                commentIndex= i;
            else if (line.trim().startsWith("Exec="))
                execIndex= i;
            else if (line.trim().startsWith("Icon="))
                iconIndex= i;
            else if (line.trim().startsWith("Terminal="))
                terminalIndex= i;
            else if (line.trim().startsWith("Type="))
                typeIndex= i;
            else if (line.trim().startsWith("Categories="))
                categoriesIndex= i;
        }


        //search for every field and save it
        // index 5 because name= has 5 symbols, "comment=" has 8, etc
        if(visilbeNameIndex != -1)
            visibleName = ( (String)lines.get(visilbeNameIndex) ).trim().substring(5);

        if(visilbeNameIndex != -1)
            comment = ( (String)lines.get(visilbeNameIndex) ).trim().substring(8);

        if(visilbeNameIndex != -1)
            exec = ( (String)lines.get(visilbeNameIndex) ).trim().substring(5);

        if(visilbeNameIndex != -1)
            icon = ( (String)lines.get(visilbeNameIndex) ).trim().substring(5);

        if(visilbeNameIndex != -1)
            terminal = ( (String)lines.get(visilbeNameIndex) ).trim().substring(9).toLowerCase().equals("true");

        //if(typeIndex != -1)
            //type = DesktopType.valueOf(( (String)lines.get(visilbeNameIndex) ).trim().substring(5).trim().toUpperCase());

        if(visilbeNameIndex != -1)
            categories = ( (String)lines.get(visilbeNameIndex) ).trim().substring(11);
    }

    /**
     * put properties data into lines property,
     * so lines should reflect file's final content
     */
    public void refreshLines() throws IOException {

        lines.set(visilbeNameIndex, visibleName);
        lines.set(commentIndex, comment);
        lines.set(execIndex, exec);
        lines.set(iconIndex, icon);
        lines.set(terminalIndex, terminal);
        lines.set(typeIndex, type);
        lines.set(categoriesIndex, categories);
    }

    public void save() throws IOException {
        l.info("save: " + this.getName());

        FileUtils.writeLines(this, getLines());
    }



    //region properties
    /**
     * It should override Name property.
     */
    private String visibleName;
    private String comment;
    private String exec;
    private String icon;
    private String categories;
    private List lines;
    private DesktopType type;
    private Boolean hidden;
    private Boolean terminal;

    //-1 means doesn't exist
    int visilbeNameIndex = -1;
    int commentIndex = -1;
    int execIndex = -1;
    int iconIndex = -1;
    int terminalIndex = -1;
    int typeIndex = -1;
    int categoriesIndex = -1;
    //endregion



    //region getter and setters
    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String name) {
        this.visibleName = name;
        lines.set(visilbeNameIndex, visibleName);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        lines.set(commentIndex, comment);
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
        lines.set(execIndex, exec);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        lines.set(iconIndex, icon);
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
        lines.set(categoriesIndex, categories);
    }

    public List getLines() {
        return lines;
    }

    public void setLines(List lines) {
        this.lines = lines;
    }

    public DesktopType getDesktopType() {
        return type;
    }

    public void setDesktopType(DesktopType type) {
        this.type = type;
        lines.set(typeIndex, type);
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getTerminal() {
        return terminal;
    }

    public void setTerminal(Boolean terminal) {
        this.terminal = terminal;
        lines.set(terminalIndex, terminal);
    }
    //endregion
}
