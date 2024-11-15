package com.essobhi.bookscape.enums;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");
    private String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
