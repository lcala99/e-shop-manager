package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.KeyBoard;
import it.unipd.mtss.model.MotherBoard;
import it.unipd.mtss.model.Mouse;
import it.unipd.mtss.model.Processor;
import it.unipd.mtss.model.User;
import it.unipd.mtss.model.UserImpl;

public class BillImplTest {

    @Test
    public void getOrderPrice_EmptyList_Calculated() throws BillException {
        List<EItem> emptyList = new ArrayList<EItem>();
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));
        double price = new BillImpl().getOrderPrice(emptyList, user);
        assertEquals(0.0, price, 0.0);
    }

    @Test
    public void getOrderPrice_OneItem_Calculated() throws BillException {
        List<EItem> oneItemList = new ArrayList<EItem>();
        oneItemList.add(new Mouse("jerry", 18.9));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));
        double price = new BillImpl().getOrderPrice(oneItemList, user);
        assertEquals(18.9, price, 0.0);
    }

    @Test
    public void getOrderPrice_MultipleItems_Calculated() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("jerry", 18.9));
        itemsList.add(new Mouse("tom", 50.9));
        itemsList.add(new Mouse("spike", 90.9));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));
        double price = new BillImpl().getOrderPrice(itemsList, user);
        assertEquals(160.7, price, 0.0);
    }

    @Test
    public void getOrderPrice_DifferentItemTypes_Calculated() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("jerry", 18.9));
        itemsList.add(new MotherBoard("tom", 50.9));
        itemsList.add(new Processor("spike", 90.9));
        itemsList.add(new KeyBoard("nibbles", 90.0));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));
        double price = new BillImpl().getOrderPrice(itemsList, user);
        assertEquals(250.7, price, 0.0);
    }

    @Test
    public void getOrderPrice_MoreThan5Processors_DiscountApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Processor("a", 18.9));
        itemsList.add(new Processor("b", 50.9));
        itemsList.add(new Processor("c", 15.9));
        itemsList.add(new Processor("d", 35.6));
        itemsList.add(new Processor("e", 31.1));
        itemsList.add(new Processor("f", 42.8));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(187.25, price, 0.01);
    }

    @Test
    public void getOrderPrice_LessThan5Processors_DiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new KeyBoard("a", 18.9));
        itemsList.add(new Processor("b", 50.9));
        itemsList.add(new Mouse("c", 15.9));
        itemsList.add(new Processor("d", 35.6));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(121.3, price, 0.001);
    }

    @Test
    public void getOrderPrice_Exactly5Processors_DiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Processor("a", 18.9));
        itemsList.add(new Processor("b", 50.9));
        itemsList.add(new Processor("c", 15.9));
        itemsList.add(new Processor("d", 35.6));
        itemsList.add(new Processor("e", 31.1));
        User user = new UserImpl("cicirello@mad.it", new Date(17186400000L));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(152.4, price, 0.01);
    }
}
