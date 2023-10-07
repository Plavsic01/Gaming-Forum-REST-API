package com.forum.gamingforumauth.exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException() {
        super("User Profile Not Found!");
    }
}
