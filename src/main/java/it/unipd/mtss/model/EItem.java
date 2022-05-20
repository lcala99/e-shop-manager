////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public interface EItem {
    public enum itemType {
        Processor, MotherBoard, Mouse, KeyBoard
    }

    public itemType getType();

    public String getName();

    public Float getPrice();
}
