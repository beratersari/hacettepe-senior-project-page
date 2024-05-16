package seniorproject.models.concretes.enums;

import lombok.Getter;

@Getter
public enum ERole {
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PROFESSOR("ROLE_PROFESSOR"),
    ROLE_USER("ROLE_USER");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public Object valueOf() {
        return role;
    }
}