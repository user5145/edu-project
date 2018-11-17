package org.user.appimagemanager.model;

import javafx.beans.value.ChangeListener;


/**
 * Template for Change Listeners which need access to their controller
 */
public abstract class TemplateChangeListenerContAccess implements ChangeListener {



    public Object cont;

    public TemplateChangeListenerContAccess(Object cont){
        this.cont = cont;
    }
}
