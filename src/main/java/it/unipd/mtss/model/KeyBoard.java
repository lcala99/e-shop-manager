////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public class KeyBoard implements EItem{
    String name;
    Double price;

    public KeyBoard(String name, Double price) {
        super();
        this.name = name;
        this.price = price;
    }

    @Override
    public itemType getType() {
        return EItem.itemType.KeyBoard;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }
}
