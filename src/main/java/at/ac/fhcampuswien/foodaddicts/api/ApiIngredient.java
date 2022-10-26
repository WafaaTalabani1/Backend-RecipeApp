package at.ac.fhcampuswien.foodaddicts.api;

public class ApiIngredient {
    private int id;
    private double amount;
    private String unit;
    private String name;

    public ApiIngredient(int id, double amount, String unit, String name) {
        this.id = id;
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
