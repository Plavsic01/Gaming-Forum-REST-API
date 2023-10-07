package com.forum.gamingforumauth.exception;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException() {
        super("Passwords Does Not Match!");
    }
}
