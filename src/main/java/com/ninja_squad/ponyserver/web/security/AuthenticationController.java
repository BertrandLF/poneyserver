package com.ninja_squad.ponyserver.web.security;

import javax.servlet.http.HttpServletResponse;

import com.ninja_squad.ponyserver.web.Database;
import com.ninja_squad.ponyserver.web.user.AuthenticatedUser;
import com.ninja_squad.ponyserver.web.user.Token;
import com.ninja_squad.ponyserver.web.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication
 * @author JB Nizet
 */
@Api("Authentication")
@RestController
@RequestMapping(value = "/api/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private Database database;

    /**
     * Authenticates the user with the given credentials, and sends back the token (the login of the user) which must
     * be sent by every subsequent request in a header named Custom-Authentication. If the credentials are invalid, a 401 response
     * is sent.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Authenticates a user and gets back the 'secret' token in the response. This token must be passed in a header named 'Custom-Authentication' in all subsequent requests")
    @ApiResponses(@ApiResponse(code = 401, message = "The credentials are incorrect"))
    public AuthenticatedUser authenticate(@ApiParam(value = "The authentication credentials", required = true) @RequestBody Credentials credentials,
                                          HttpServletResponse response) {
        for (User user : database.getUsers()) {
            if (user.getLogin().equals(credentials.getLogin())
                && user.getPassword().equals(credentials.getPassword())) {
                return new AuthenticatedUser(user, new Token(user.getLogin()));
            }
        }
        throw new UnauthorizedException();
    }
}
