package tul.swiercz.thesis.bookmind.exception;


public class ExceptionMessages {

    private static final String EXCEPTION_PREFIX = "Exception.";

    public static final String UNAUTHORIZED = EXCEPTION_PREFIX + "UNAUTHORIZED";
    public static final String NOT_AUTHENTICATED = EXCEPTION_PREFIX + "NOT_AUTHENTICATED";
    public static final String ACCESS_DENIED = EXCEPTION_PREFIX + "ACCESS_DENIED";

    public static final String GET_NOT_FOUND = EXCEPTION_PREFIX + "GET_NOT_FOUND";
    public static final String UPDATE_NOT_FOUND = EXCEPTION_PREFIX + "UPDATE_NOT_FOUND";
    public static final String CODE_NOT_FOUND = EXCEPTION_PREFIX + "CODE_NOT_FOUND";

    public static final String INTERNAL_EXCEPTION = EXCEPTION_PREFIX + "INTERNAL_EXCEPTION";

    public static final String OPTIMISTIC_LOCK = EXCEPTION_PREFIX + "OPTIMISTIC_LOCK";

    public static final String SYNC_ERROR = EXCEPTION_PREFIX + "SYNC_ERROR";

    public static final String SIGNATURE_NOT_VALID_EXCEPTION = EXCEPTION_PREFIX + "SIGNATURE_NOT_VALID_EXCEPTION";
}
