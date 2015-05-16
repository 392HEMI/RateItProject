package com.rateit.api;

/**
 * Created by alexander on 21.04.15.
 */
public enum SelectorAction {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    EQUALITY("="),
    NOTEQUALTY("!="),
    LESSTHEN("<"),
    MORETHEN(">"),
    LESSTHENOREQUAL("<="),
    MORETHENOREQUAL(">="),
    AND("&"),
    OR("|");

    String value;

    private SelectorAction(String value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
