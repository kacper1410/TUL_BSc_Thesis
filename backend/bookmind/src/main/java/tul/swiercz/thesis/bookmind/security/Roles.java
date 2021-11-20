package tul.swiercz.thesis.bookmind.security;

public class Roles {

    public static final String ADMIN = "ADMIN";
    public static final String MODERATOR = "MODERATOR";
    public static final String READER = "READER";
    public static final String[] AUTHENTICATED = new String[] { ADMIN, MODERATOR, READER };

}
