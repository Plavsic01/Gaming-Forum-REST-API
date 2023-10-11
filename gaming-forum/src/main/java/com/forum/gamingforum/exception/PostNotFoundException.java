package com.forum.gamingforum.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post Not Found!");
    }
}
