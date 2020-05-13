package com.alastairappleton.enums;

public enum Importance {

    CRITICAL("Mission critical"),
    MAJOR("Pretty important"),
    MINOR("Not a huge deal"),
    TRIVIAL("Trifling");

    private String label;

    private Importance(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
