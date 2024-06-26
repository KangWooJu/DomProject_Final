package ac.kr.dankook.ace.dom_t1.controller;

import lombok.Getter;


@Getter
public enum SiteuserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    SiteuserRole(String value) {
        this.value = value;
    }
}
