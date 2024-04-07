package seniorproject.core.utilities.results;

import lombok.Getter;

@Getter
public class DataResult<T> extends Result{
    private final T data;

    public DataResult(T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }

    public DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }

    public DataResult(T data, boolean success, String message, int pageSize, long totalElements, int totalPages, int number) {
        super(success, message, pageSize, totalElements, totalPages, number);
        this.data = data;
    }

}
