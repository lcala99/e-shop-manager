package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MotherBoardTest {
    @Test
    public void MotherBoard_GetType_OK() {
        EItem motherboard = new MotherBoard("Minnie", 999.70);
        EItem.itemType type = motherboard.getType();
        assertEquals(EItem.itemType.MotherBoard, type);
    }

    @Test
    public void MotherBoard_GetName_OK() {
        EItem motherboard = new MotherBoard("Minnie", 999.70);
        String name = motherboard.getName();
        assertEquals("Minnie", name);
    }

    @Test
    public void MotherBoard_GetPrice_OK() {
        EItem motherboard = new MotherBoard("Minnie", 999.70);
        Double price = motherboard.getPrice();
        assertEquals(999.70, price, 0.001);
    }
}
