package com.ninja_squad.poneyserver.web.user;

/**
 * Created by blefoulgoc on 2/27/17.
 */
public class AuthenticatedUser {

    private User user;
    private Token token;

    public AuthenticatedUser(User user, Token token) {
        this.user = user;
        this.token = token;
    }

    @SuppressWarnings("unused")
    public String getLogin() {
        return user.getLogin();
    }

    @SuppressWarnings("unused")
    public String getToken() {
        return token.getToken();
    }
}
