package com.murk.telegram.ping.handler.core.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationUtilTest {

    @Test
    public void validateSuccess() {
        ValidationUtil.validate("Valid","Valid valid", "valid   valid");
    }

    @Test()
    public void containsNotValidSymbols() {
        try {
            ValidationUtil.validate("NOT VALID.$3@");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_CONTAINS_NOT_VALID_SYMBOLS);
        }
    }

    @Test()
    public void paramIsEmpty() {
        try {
            ValidationUtil.validate("");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_IS_EMPTY);
        }
    }

    @Test()
    public void tooLongParam() {
        try {
            ValidationUtil.validate("toooooooooooooooooooooooooooooooo looooooooooooooooooooooooonnnnnnnnnnngggggggggggggg");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_IS_TOO_LONG);
        }
    }
}