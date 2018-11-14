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

    private enum Type {
        APPLICATION{
            @Override
            public String toString() {
                return "Application";
            }
        },
        LINK{
            @Override
            public String toString() {
                return "Link";
            }
        },
        DIRECTORY{
            @Override
            public String toString() {
                return "Directory";
            }
        };

        Type() {}
    }

    /**
     * Create a file with additional properties to store the most common data
     * Tries to load data from the file if that file exists
     * @param uri path to the file
     */
    public Desktop(String uri) {
        super(uri);
        l.info(this.getPath());

        try {
            lines = FileUtils.readLines(this);
        } catch (IOException e) { e.printStackTrace(); return;}

        Iterator it = lines.iterator();
        while(it.hasNext()){
            var temp = (String)it.next();
            l.debug("load " + this.getName() + ", interprets a line:" + temp);

            //search for every field and save it
            if (temp.startsWith("Name=")){
                visibleName = temp.substring(temp.indexOf("Name=")+5);
            }
            else if (temp.startsWith("Comment=")){
                comment = temp.substring(temp.indexOf("Comment=")+8);
            }
            else if (temp.startsWith("Exec=")){
                exec = temp.substring(temp.indexOf("Exec=")+5);
            }
            else if (temp.startsWith("Icon=")){
                icon = temp.substring(temp.indexOf("Icon=")+5);
            }
            else if (temp.startsWith("Terminal=")){
                terminal = (temp.substring(temp.indexOf("Terminal=")+9).toLowerCase().equals("true"));
            }
            else if (temp.startsWith("Type=")){
                type = Type.valueOf(temp.substring(temp.indexOf("Type=")+5).trim().toUpperCase());
            }
            else if (temp.startsWith("Categories=")){
                categories = temp.substring(temp.indexOf("Categories=")+11);
            }
        }
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
    private Type type;
    private Boolean hidden;
    private Boolean terminal;
    //endregion

    //region getter and setters
    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String name) {
        this.visibleName = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public List getLines() {
        return lines;
    }

    public void setLines(List lines) {
        this.lines = lines;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
    }
    //endregion
}
