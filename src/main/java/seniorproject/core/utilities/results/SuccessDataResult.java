package seniorproject.core.utilities.results;

public class SuccessDataResult<T> extends DataResult<T> {
    public SuccessDataResult(T data, String message) {
        super(data, true, message);
    }

    public SuccessDataResult(T data) {
        super(data, true);
    }

    public SuccessDataResult(String message) {
        super(null, true, message);
    }

    public SuccessDataResult() {
        super(null, true);
    }

    public SuccessDataResult(T data, int pageSize, long totalElements, int totalPages, int number) {
        super(data, true, null, pageSize, totalElements, totalPages, number);
    }

}
