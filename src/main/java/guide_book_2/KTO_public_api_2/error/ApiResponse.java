package guide_book_2.KTO_public_api_2.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private int errorId;
    private String errorMessage;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(int errorId, String errorMessage) {
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }
}
