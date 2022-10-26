package at.ac.fhcampuswien.foodaddicts.model;

public enum Unit {
    KG,
    G,
    L,
    ML,
    PIECE,
    SPOON,
    CUP,
    SMALL_SPOON;

    public static Unit fromString(String value){
        if (value.equalsIgnoreCase("g") || value.equalsIgnoreCase("gram"))
            return Unit.G;
        else if (value.equalsIgnoreCase("kg") || value.contains("kilo"))
            return Unit.KG;
        else if (value.equalsIgnoreCase("l") || value.equalsIgnoreCase("liter"))
            return Unit.L;
        else if (value.equalsIgnoreCase("ml") || value.contains("milli"))
            return Unit.ML;
        else if (value.equalsIgnoreCase("p") || value.equalsIgnoreCase("piece"))
            return Unit.PIECE;
        else if (value.equalsIgnoreCase("s") || value.equalsIgnoreCase("spoon"))
            return Unit.SPOON;
        else if (value.equalsIgnoreCase("c") || value.equalsIgnoreCase("cup"))
            return Unit.CUP;
        else if (value.equalsIgnoreCase("ss") || value.contains("small"))
            return Unit.SMALL_SPOON;
        else
            return null;
    }


}
