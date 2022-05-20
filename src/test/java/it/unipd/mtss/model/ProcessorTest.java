package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProcessorTest {

    @Test
    public void Processor_GetType_OK() {
        EItem processor = new Processor("Celebro", 380.50);
        EItem.itemType type = processor.getType();
        assertEquals(EItem.itemType.Processor, type);
    }

    @Test
    public void Processor_GetName_OK() {
        EItem processor = new Processor("Celebro", 380.50);
        String name = processor.getName();
        assertEquals("Celebro", name);
    }

    @Test
    public void Processor_GetPrice_OK() {
        EItem processor = new Processor("Celebro", 380.50);
        Double price = processor.getPrice();
        assertEquals(380.50, price, 0.001);
    }
}
