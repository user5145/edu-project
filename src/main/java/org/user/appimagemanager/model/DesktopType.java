package org.user.appimagemanager.model;

public enum DesktopType {



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



    DesktopType() {}
}
