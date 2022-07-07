package com.farukkavlak.weatherproject.Generic.Enum;

import com.farukkavlak.weatherproject.Generic.Exception.BaseErrorMessage;

public enum ErrorMessage implements BaseErrorMessage {

    RESTRICT_API_DATE("First date cannot be before the 27 November 2020."),
    LAST_DATE_CANNOT_BEFORE_FIRST_DATE("Last date cannot be before the First date."),
    NO_RESULT_IN_DB("The data of at least one day in the between these dates does not exist in the database."),
    ;

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
