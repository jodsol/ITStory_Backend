package com.it.story.config.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("Can't find Member");
    }

}
