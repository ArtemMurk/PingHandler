package com.murk.telegram.ping.handler.core.utils;


public class ValidationUtil {

    public static final String PARAMS_IS_EMPTY = "params can't be empty";
    public static final String PARAMS_CONTAINS_NOT_VALID_SYMBOLS = "params must contains only numbers,letters and white spaces";
    public static final String PARAMS_IS_TOO_LONG = "param length must be less then 50 symbols";
    public static void validate(String... params)
    {
        for (String param : params) {
            validate(param);
        }
    }

    private static void validate(String param)
    {
        checkNull(param);
        checkIllegalSymbols(param);
        checkParamLength(param);
    }

    private static void checkNull(String param) {
        if (param == null || param.isEmpty())
        {
            throw new IllegalArgumentException(PARAMS_IS_EMPTY);
        }
    }

    private static void checkIllegalSymbols(String param) {
        if (!param.matches("[0-9a-zA-Z\\s]+"))
        {
            throw new IllegalArgumentException(PARAMS_CONTAINS_NOT_VALID_SYMBOLS);
        }
    }

    private static void checkParamLength(String param) {
        if (param.length()>50)
        {
            throw new IllegalArgumentException(PARAMS_IS_TOO_LONG);
        }
    }
}
