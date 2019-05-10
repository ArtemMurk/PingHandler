package com.murk.telegram.ping.handler.core.utils;

import org.junit.Test;

import static mocks.model.MockModels.*;

import static org.junit.Assert.*;

public class ValidationUtilTest {

    @Test
    public void validateSuccess() {
        ValidationUtil.validate(PROJECT_NAME_1, MODULE_KEY_1);
    }

    @Test()
    public void projectNameContainsNotValidSymbols() {
        try {
            ValidationUtil.validate("f@345",MODULE_KEY_1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.PARAMS_CONTAINS_NOT_VALID_SYMBOLS);
        }
    }


    @Test()
    public void moduleKeyContainsNotValidSymbols() {
        try {
            ValidationUtil.validate(PROJECT_NAME_1,"123456789azxcvasdfqwertyhgfdsaww");
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
    public void tooLongParamProjName() {
        try {
            ValidationUtil.validate("toooooooooooooooooooooooooooooooolooooooooooooooooooooooooonnnnnnnnnnngggggggggggggg", MODULE_KEY_1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.ILLEGAL_PROJECT_NAME_LENGTH);
        }
    }

    @Test()
    public void moduleKeyHasWrongSize() {
        try {
            ValidationUtil.validate(PROJECT_NAME_1, "111");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),ValidationUtil.ILLEGAL_MODULE_KEY_LENGTH);
        }
    }
}