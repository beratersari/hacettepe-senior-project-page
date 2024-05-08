package seniorproject.models.dto;

import lombok.Getter;

@Getter
public enum EType {
    TITLE("title"),

    AUTHORS("authors"),

    KEYWORDS("keywords");

    private final String type;

    EType(String type) {
        this.type = type;
    }

}
