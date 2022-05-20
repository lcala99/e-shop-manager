package it.unipd.mtss.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EItemTest {

    @Test
    public void EItemType_EnumExists_OK() {
        assertNotNull(EItem.itemType.Mouse);
    }
}
