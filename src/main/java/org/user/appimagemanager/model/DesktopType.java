package org.user.appimagemanager.model;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

public enum DesktopType {
    APPLICATION {
        @Override
        public String toString() {
            return "Application";
        }
    },
    DIRECTORY {
        @Override
        public String toString() {
            return "Directory";
        }
    },
    LINK {
        @Override
        public String toString() {
            return "Link";
        }
    };


    DesktopType() {
    }
}
