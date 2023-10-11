package com.forum.gamingforum.exception;

public class ThreadNotFoundException extends RuntimeException {

    public ThreadNotFoundException() {
        super("Thread Not Found!");
    }
}
