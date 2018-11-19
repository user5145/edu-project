package org.user.appimagemanager.model;

import com.google.common.base.Enums;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;


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
    public Desktop(@NotNull String uri) {
        super(uri);

        try {
            if (this.exists())
                lines = FileUtils.readLines(this);
        } catch (IOException e) { e.printStackTrace(); return;}

        //searches for appropriate lines and saves their indexes (AKA a bunch of ifs)
        for(int i=0; i < lines.size(); i++){
            String line = (String) lines.get(i);
            if (line.trim().startsWith("Name="))
                visibleNameIndex= i;
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


        // search for every field and save it
        // index 5 because name= has 5 symbols, "comment=" has 8, etc
        if(visibleNameIndex != -1)
            this.setVisibleName(( (String)lines.get(visibleNameIndex) ).trim().substring(5));

        if(commentIndex != -1)
            this.setComment(( (String)lines.get(commentIndex) ).trim().substring(8));

        if(execIndex != -1)
            this.setExec(( (String)lines.get(execIndex) ).trim().substring(5));

        if(iconIndex != -1)
            this.setIcon(( (String)lines.get(iconIndex) ).trim().substring(5));

        if(terminalIndex != -1)
            this.setTerminal(( (String)lines.get(terminalIndex) ).trim().substring(9).toLowerCase().equals("true"));

        if(categoriesIndex != -1)
            this.setCategories(( (String)lines.get(categoriesIndex) ).trim().substring(11));

        if(typeIndex != -1){
            var temp= ( (String)lines.get(typeIndex) ).trim().substring(5).trim();
            if (Enums.getIfPresent(DesktopType.class, temp.toUpperCase()).isPresent())
                this.setDesktopType(Enums.getIfPresent(DesktopType.class, temp.toUpperCase()).get());
        }
    }


    /**
     * Can override an index,
     * uses the index to localize a line to edit or create a new one
     * @param index index of the line to override. -1 means ignore, then it will override it.
     * @param lineToUse what to write
     * @param lines all the lines
     * @param desktopProperty e.g. "Name", "Comment", "Type"
     * @return overridden index, immutable int makes it thread safe.
     */
    @NotNull
    public int addLineSmallFacade( @NotNull String lineToUse, @NotNull List lines, @NotNull int index, @NotNull String desktopProperty){
        l.debug("add a line: " + lineToUse + " at " + index);

        if(index != -1)
            lines.set(index, desktopProperty + "=" + lineToUse);
        else if(!lineToUse.isBlank()){
            lines.add(desktopProperty + "=" + lineToUse);
            index = lines.size()-1;
        }
        return index;
    }

    public void save() throws IOException {
        l.info("save: " + this.getName());

        l.debug("saving: ");
        for (Object line_temp : lines) {
            l.debug((String) line_temp);
        }
        //FileUtils.writeLines(this, getLines());
    }



    //region properties
    private List lines = new ArrayList();
    /**
     * It should override Name property.
     */
    private String visibleName;
    private String comment;
    private String exec;
    private String icon;
    private String categories;
    private DesktopType type;
    private Boolean hidden;
    private Boolean terminal;

    //-1 means doesn't exist
    private int visibleNameIndex = -1;
    private int commentIndex = -1;
    private int execIndex = -1;
    private int iconIndex = -1;
    private int categoriesIndex = -1;
    private int typeIndex = -1;
    private int terminalIndex = -1;
    private int hiddenIndex = -1;
    //endregion



    //region getter and setters
    public void setVisibleName(String name) {
        this.visibleName = name;
        visibleNameIndex= addLineSmallFacade(visibleName, lines, visibleNameIndex, "Name");
    }

    public void setComment(String comment) {
        this.comment = comment;
        commentIndex= addLineSmallFacade(comment, lines, commentIndex, "Comment");
    }

    public void setExec(String exec) {
        this.exec = exec;
        execIndex= addLineSmallFacade(exec, lines, execIndex, "Exec");
    }

    public void setIcon(String icon) {
        this.icon = icon;
        iconIndex= addLineSmallFacade(icon, lines, iconIndex, "Icon");
    }

    public void setCategories(String categories) {
        this.categories = categories;
        categoriesIndex= addLineSmallFacade(categories, lines, categoriesIndex, "Categories");
    }

    public void setDesktopType(DesktopType type) {
        this.type = type;
        typeIndex= addLineSmallFacade(this.type.toString(), lines, typeIndex, "Type");
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setTerminal(Boolean terminal) {
        this.terminal = terminal;
        //terminalIndex= addLineSmallFacade(this.terminal, lines, terminalIndex);
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getComment() {
        return comment;
    }

    public String getExec() {
        return exec;
    }

    public String getIcon() {
        return icon;
    }

    public String getCategories() {
        return categories;
    }

    public DesktopType getType() {
        return type;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public Boolean getTerminal() {
        return terminal;
    }
    //endregion
}
