package com.codecool.helpinghands.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EventCategory {
    PEOPLE,
    ANIMALS,
    ENVIRONMENT,
    SMALL,
    CONCERT,
    FASHION_SHOW,
    UNDEFINED;

    @JsonCreator
    public static EventCategory fromString(String s) {
        if (s.compareToIgnoreCase("animals") == 0)
            return ANIMALS;
        if (s.compareToIgnoreCase("people") == 0)
            return PEOPLE;
        if (s.compareToIgnoreCase("enviroment") == 0)
            return ENVIRONMENT;
        if (s.compareToIgnoreCase("small") == 0)
            return SMALL;
        if (s.compareToIgnoreCase("concert") == 0)
            return CONCERT;
        if (s.compareToIgnoreCase("fashion show") == 0)
            return FASHION_SHOW;
        return UNDEFINED;
    }
}
