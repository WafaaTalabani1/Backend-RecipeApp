package at.ac.fhcampuswien.foodaddicts.exception;

public class FoodAddictException extends Exception{

    public FoodAddictException(String msg){
        super(msg);
    }

    public FoodAddictException(String msg, Throwable cause){
        super(msg, cause);
    }
}
