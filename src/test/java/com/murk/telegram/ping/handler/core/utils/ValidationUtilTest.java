package com.murk.telegram.ping.handler.core.utils;

import org.junit.Test;

import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.MODULE_KEY_1;
import static com.murk.telegram.ping.handler.core.model.RequestModelConstants.PROJECT_NAME_1;
import static org.junit.Assert.*;

public class ValidationUtilTest {

    @Test
    public void validateSuccess() {
        ValidationUtil.validate(PROJECT_NAME_1, MODULE_KEY_1);
    }

    @Test()
    public void containsNotValidSymbols() {
        try {
            ValidationUtil.validate("NOT VALID.$3@","NOT VALID.$3@");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_CONTAINS_NOT_VALID_SYMBOLS);
        }
    }

    @Test()
    public void paramIsEmpty() {
        try {
            ValidationUtil.validate("", MODULE_KEY_1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_IS_EMPTY);
        }
    }

    @Test()
    public void tooLongParam() {
        try {
            ValidationUtil.validate("toooooooooooooooooooooooooooooooolooooooooooooooooooooooooonnnnnnnnnnngggggggggggggg", MODULE_KEY_1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_IS_TOO_LONG);
        }
    }
}