package at.ac.fhcampuswien.foodaddicts.exception;

public class InvalidRequestException extends Exception{
    public InvalidRequestException(String msg){
        super(msg);
    }

    public InvalidRequestException(String msg, Throwable cause){
        super(msg, cause);
    }


}
