package seniorproject.core.utilities.results;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    private boolean success;
    private String message;

    private int pageSize;
    private long totalElements;
    private int totalPages;
    private int number;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

    public Result(boolean success, String message, int pageSize, long totalElements, int totalPages, int number) {
        this(success, message);
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

}
