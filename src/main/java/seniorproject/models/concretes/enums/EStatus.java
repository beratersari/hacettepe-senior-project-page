package seniorproject.models.concretes.enums;

import lombok.Getter;

@Getter
public enum EStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");
    private final String status;

    EStatus(String status) {
        this.status = status;
    }

}
