package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KeyBoardTest {
    @Test
    public void KeyBoard_GetType_OK() {
        EItem keyboard = new KeyBoard("EtaBeta", 224.23);
        EItem.itemType type = keyboard.getType();
        assertEquals(EItem.itemType.KeyBoard, type);
    }

    @Test
    public void KeyBoard_GetName_OK() {
        EItem keyboard = new KeyBoard("EtaBeta", 224.23);
        String name = keyboard.getName();
        assertEquals("EtaBeta", name);
    }

    @Test
    public void KeyBoard_GetPrice_OK() {
        EItem keyboard = new KeyBoard("EtaBeta", 224.23);
        Double price = keyboard.getPrice();
        assertEquals(224.23, price, 0.001);
    }
}
