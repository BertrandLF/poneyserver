package com.ninja_squad.ponyserver.web.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a 401 response must be sent back
 * @author JB Nizet
 */
public class UnauthorizedException extends RuntimeException {
}
