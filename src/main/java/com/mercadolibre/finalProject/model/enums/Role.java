package com.mercadolibre.finalProject.model.enums;

public enum Role {

    REPRESENTATIVE(1, "ROLE_REPRESENTATIVE"),
    BUYER(2, "ROLE_BUYER");

    private int code;
    private String description;

    Role(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Role toEnum(int code) {
        for(Role r : Role.values()) {
            if(r.getCode() == code)
                return r;
        }

        return null;
    }
}
