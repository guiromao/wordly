package co.wordly.data.dto.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorResponse {

    private static final String PROPERTY_CODE = "code";
    private static final String PROPERTY_REASON = "reason";
    private static final String PROPERTY_MESSAGE = "message";

    private final Integer code;
    private final String reason;
    private final String message;

    @JsonCreator
    public ErrorResponse(@JsonProperty(PROPERTY_CODE) Integer code,
                         @JsonProperty(PROPERTY_REASON) String reason,
                         @JsonProperty(PROPERTY_MESSAGE) String message) {
        this.code = code;
        this.reason = reason;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @JsonProperty(PROPERTY_CODE)
    public Integer getCode() {
        return code;
    }

    @JsonProperty(PROPERTY_REASON)
    public String getReason() {
        return reason;
    }

    @JsonProperty(PROPERTY_MESSAGE)
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(code, that.code) && Objects.equals(reason, that.reason) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, reason, message);
    }

}
