package tul.swiercz.thesis.bookmind.exception;


public class ExceptionMessages {

    private static final String EXCEPTION_PREFIX = "Exception.";

    public static final String GET_NOT_FOUND = EXCEPTION_PREFIX + "GET_NOT_FOUND";
    public static final String UPDATE_NOT_FOUND = EXCEPTION_PREFIX + "UPDATE_NOT_FOUND";
    public static final String CODE_NOT_FOUND = EXCEPTION_PREFIX + "CODE_NOT_FOUND";

    public static final String INTERNAL_EXCEPTION = EXCEPTION_PREFIX + "INTERNAL_EXCEPTION";

    public static final String OPTIMISTIC_LOCK = EXCEPTION_PREFIX + "OPTIMISTIC_LOCK";

    public static final String SYNC_ERROR = EXCEPTION_PREFIX + "SYNC_ERROR";
}
