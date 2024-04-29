package seniorproject.models.dto;

import lombok.Getter;

@Getter
public enum EType {
    TITLE("title"),

    AUTHOR("authors"),

    KEYWORDS("keywords");

    private final String type;

    EType(String type) {
        this.type = type;
    }

}
