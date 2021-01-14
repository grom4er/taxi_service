package taxiservice.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String text, Throwable ex){
        super(text, ex);
    }
}
