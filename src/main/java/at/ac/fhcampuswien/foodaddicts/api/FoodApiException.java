package at.ac.fhcampuswien.foodaddicts.api;

public class FoodApiException extends Exception{
    public FoodApiException(String msg){
        super(msg);
    }

    public FoodApiException(String msg, Throwable cause){
        super(msg, cause);
    }
}
