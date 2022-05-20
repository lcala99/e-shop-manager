package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MouseTest {

    @Test
    public void Mouse_GetType_OK() {
        EItem mouse = new Mouse("Mickey", 30.70);
        EItem.itemType type = mouse.getType();
        assertEquals(EItem.itemType.Mouse, type);
    }

    @Test
    public void Mouse_GetName_OK() {
        EItem mouse = new Mouse("Mickey", 30.70);
        String name = mouse.getName();
        assertEquals("Mickey", name);
    }

    @Test
    public void Mouse_GetPrice_OK() {
        EItem mouse = new Mouse("Mickey", 30.70);
        Double price = mouse.getPrice();
        assertEquals(30.70, price, 0.001);
    }
}
