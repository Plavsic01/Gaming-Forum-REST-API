package com.forum.gamingforum.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Category Not Found!");
    }
}
