package com.mercadolibre.finalProject.model.enums;

import com.mercadolibre.finalProject.exceptions.NotFoundException;

public enum RoleType {

    REPRESENTATIVE(1, "ROLE_REPRESENTATIVE"),
    BUYER(2, "ROLE_BUYER"),
    SELLER(3, "ROLE_SELLER");

    private int code;
    private String description;

    RoleType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RoleType toEnum(Integer code) {
        for (RoleType r : RoleType.values()) {
            if (r.getCode().equals(code))
                return r;
        }
        throw new NotFoundException("Role not found.");
    }
}
