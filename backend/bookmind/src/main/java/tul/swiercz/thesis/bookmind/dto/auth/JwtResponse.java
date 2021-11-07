package tul.swiercz.thesis.bookmind.dto.auth;

public class JwtResponse {

    private String jwtToken;

    //region Accessors
    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
    //endregion

}
