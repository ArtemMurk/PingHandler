package com.murk.telegram.ping.handler.core.utils;


public class ValidationUtil {

    public static final String PARAMS_IS_EMPTY = "params can't be empty";
    public static final String PARAMS_CONTAINS_NOT_VALID_SYMBOLS = "not valid symbols in your params";
    public static final String PARAMS_IS_TOO_LONG = "not valid length in your param";
    public static final String ILLEGAL_MODULE_KEY = "Illegal module key length";

    private static final int PROJECT_NAME_LENGTH = 50;
    private static final int MODEULE_KEY_LENGTH = 32;

    private static final String PROJECT_NAME_PATTERN = "[0-9a-zA-Z_]+";
    private static final String MODEULE_KEY_MD5_PATTERN = "[a-fA-F0-9]";


    public static void validate(String projectName, String moduleKey)
    {
        validateProjectName(projectName);
        validateKey(moduleKey);
    }

    private static void validateProjectName(String projectName) {
        checkNull(projectName);
        checkIllegalSymbols(projectName, PROJECT_NAME_PATTERN);
        checkProjectLength(projectName);
    }

    private static void validateKey(String moduleKey) {
        checkNull(moduleKey);
        checkKeyLength(moduleKey);
        checkIllegalSymbols(moduleKey,MODEULE_KEY_MD5_PATTERN);
    }


    private static void checkNull(String param) {
        if (param == null || param.trim().isEmpty())
        {
            throw new IllegalArgumentException(PARAMS_IS_EMPTY);
        }
    }

    private static void checkIllegalSymbols(String param, String pattern) {
        if (!param.matches(pattern))
        {
            throw new IllegalArgumentException(PARAMS_CONTAINS_NOT_VALID_SYMBOLS);
        }
    }

    private static void checkProjectLength(String param) {
        if (param.length()>PROJECT_NAME_LENGTH)
        {
            throw new IllegalArgumentException(PARAMS_IS_TOO_LONG);
        }
    }

    private static void checkKeyLength(String moduleKey)
    {
        if (moduleKey.length()!= MODEULE_KEY_LENGTH)
        {
            throw new IllegalArgumentException(ILLEGAL_MODULE_KEY);
        }
    }
}
