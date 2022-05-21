package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
        double price = new BillImpl().getOrderPrice(emptyList, user);
        assertEquals(2.0, price, 0.0);
    }

    @Test
    public void getOrderPrice_OneItem_Calculated() throws BillException {
        List<EItem> oneItemList = new ArrayList<EItem>();
        oneItemList.add(new Mouse("jerry", 18.9));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
        double price = new BillImpl().getOrderPrice(oneItemList, user);
        assertEquals(18.9, price, 0.0);
    }

    @Test
    public void getOrderPrice_MultipleItems_Calculated() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("jerry", 18.9));
        itemsList.add(new Mouse("tom", 50.9));
        itemsList.add(new Mouse("spike", 90.9));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
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
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
        double price = new BillImpl().getOrderPrice(itemsList, user);
        assertEquals(231.8, price, 0.001);
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
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

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
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(105.4, price, 0.001);
    }

    @Test
    public void getOrderPrice_Exactly5Processors_DiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Processor("a", 18.9));
        itemsList.add(new Processor("b", 50.9));
        itemsList.add(new Processor("c", 15.9));
        itemsList.add(new Processor("d", 35.6));
        itemsList.add(new Processor("e", 31.1));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(152.4, price, 0.01);
    }

    @Test
    public void getOrderPrice_MoreThan10Mice_DiscountApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 18.9));
        itemsList.add(new Mouse("b", 50.9));
        itemsList.add(new Mouse("c", 15.9));
        itemsList.add(new Mouse("d", 35.6));
        itemsList.add(new Mouse("e", 31.1));
        itemsList.add(new Mouse("f", 42.8));
        itemsList.add(new Mouse("g", 18.9));
        itemsList.add(new Mouse("h", 50.9));
        itemsList.add(new Mouse("i", 16.0));
        itemsList.add(new Mouse("j", 35.6));
        itemsList.add(new Mouse("k", 31.1));
        itemsList.add(new Mouse("l", 42.8));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(374.6, price, 0.01);
    }

    @Test
    public void getOrderPrice_LessThan10Mice_DiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 35.6));
        itemsList.add(new KeyBoard("b", 50.9));
        itemsList.add(new Processor("c", 18.9));
        itemsList.add(new Mouse("d", 15.9));
        itemsList.add(new Mouse("e", 35.6));
        itemsList.add(new KeyBoard("f", 18.9));
        itemsList.add(new Mouse("g", 35.6));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(211.4, price, 0.001);
    }

    @Test
    public void getOrderPrice_Exactly10Mice_DiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 18.9));
        itemsList.add(new Mouse("b", 50.9));
        itemsList.add(new Mouse("c", 15.9));
        itemsList.add(new Mouse("d", 35.6));
        itemsList.add(new Mouse("e", 31.1));
        itemsList.add(new Mouse("f", 51.3));
        itemsList.add(new Mouse("g", 86.21));
        itemsList.add(new Mouse("h", 120.93));
        itemsList.add(new Mouse("i", 37.4));
        itemsList.add(new Mouse("j", 99.9));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(548.14, price, 0.01);
    }

    @Test
    public void getOrderPrice_SameNumberMiceKeyboard_DiscountAppliedOnMouse() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 18.9));
        itemsList.add(new Mouse("b", 50.9));
        itemsList.add(new Mouse("c", 15.9));
        itemsList.add(new Mouse("d", 35.6));
        itemsList.add(new KeyBoard("e", 31.1));
        itemsList.add(new KeyBoard("f", 51.3));
        itemsList.add(new KeyBoard("g", 86.21));
        itemsList.add(new KeyBoard("h", 120.93));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(394.94, price, 0.01);
    }

    @Test
    public void getOrderPrice_SameNumberMiceKeyboard_DiscountAppliedOnKeyBoard() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new KeyBoard("a", 18.9));
        itemsList.add(new KeyBoard("b", 50.9));
        itemsList.add(new KeyBoard("c", 15.9));
        itemsList.add(new KeyBoard("d", 35.6));
        itemsList.add(new Mouse("e", 31.1));
        itemsList.add(new Mouse("f", 51.3));
        itemsList.add(new Mouse("g", 86.21));
        itemsList.add(new Mouse("h", 120.93));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(394.94, price, 0.01);
    }

    @Test
    public void getOrderPrice_MoreThan10MiceAndKeyboard_DuplicateDiscountNotApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 18.9));
        itemsList.add(new Mouse("b", 50.9));
        itemsList.add(new Mouse("c", 15.9));
        itemsList.add(new Mouse("d", 35.6));
        itemsList.add(new Mouse("e", 31.1));
        itemsList.add(new Mouse("f", 42.8));
        itemsList.add(new Mouse("g", 18.9));
        itemsList.add(new Mouse("h", 50.9));
        itemsList.add(new Mouse("i", 16.0));
        itemsList.add(new Mouse("j", 35.6));
        itemsList.add(new Mouse("k", 31.1));
        itemsList.add(new Mouse("l", 42.8));
        itemsList.add(new KeyBoard("aa", 38.9));
        itemsList.add(new KeyBoard("bb", 56.9));
        itemsList.add(new KeyBoard("cc", 75.3));
        itemsList.add(new KeyBoard("dd", 65.6));
        itemsList.add(new KeyBoard("ee", 39.1));
        itemsList.add(new KeyBoard("ff", 52.8));
        itemsList.add(new KeyBoard("gg", 98.9));
        itemsList.add(new KeyBoard("hh", 500.09));
        itemsList.add(new KeyBoard("ii", 160.0));
        itemsList.add(new KeyBoard("jj", 95.6));
        itemsList.add(new KeyBoard("kk", 36.1));
        itemsList.add(new KeyBoard("ll", 40.8));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(1471.22, price, 0.01);
    }

    @Test
    public void getOrderPrice_MoreThan10MiceAndKeyboard_BothDiscountsApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new KeyBoard("a", 18.9));
        itemsList.add(new KeyBoard("b", 50.9));
        itemsList.add(new KeyBoard("c", 15.9));
        itemsList.add(new KeyBoard("d", 35.6));
        itemsList.add(new KeyBoard("e", 31.1));
        itemsList.add(new KeyBoard("f", 42.8));
        itemsList.add(new KeyBoard("g", 18.9));
        itemsList.add(new KeyBoard("h", 50.9));
        itemsList.add(new KeyBoard("i", 16.0));
        itemsList.add(new KeyBoard("j", 35.6));
        itemsList.add(new KeyBoard("k", 31.1));
        itemsList.add(new KeyBoard("l", 42.8));
        itemsList.add(new Mouse("aa", 38.9));
        itemsList.add(new Mouse("bb", 56.9));
        itemsList.add(new Mouse("cc", 75.3));
        itemsList.add(new Mouse("dd", 65.6));
        itemsList.add(new Mouse("ee", 39.1));
        itemsList.add(new Mouse("ff", 52.8));
        itemsList.add(new Mouse("gg", 98.9));
        itemsList.add(new Mouse("hh", 500.09));
        itemsList.add(new Mouse("ii", 160.0));
        itemsList.add(new Mouse("jj", 95.6));
        itemsList.add(new Mouse("kk", 36.1));
        itemsList.add(new Mouse("ll", 40.8));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(1438.73, price, 0.01);
    }

    @Test
    public void getOrderPrice_BillAbove1000_DiscountApplied() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Processor("jerry", 18.9));
        itemsList.add(new MotherBoard("tom", 50.9));
        itemsList.add(new Processor("spike", 900.2));
        itemsList.add(new KeyBoard("nibbles", 90.0));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
        double price = new BillImpl().getOrderPrice(itemsList, user);
        assertEquals(954.0, price, 0.001);
    }

    @Test(expected = BillException.class)
    public void getOrderPrice_MoreThan30Items_ExceptionThrown() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new Mouse("a", 18.9));
        itemsList.add(new Mouse("b", 50.9));
        itemsList.add(new MotherBoard("c", 15.9));
        itemsList.add(new Mouse("d", 35.6));
        itemsList.add(new Processor("e", 31.1));
        itemsList.add(new Mouse("f", 42.8));
        itemsList.add(new Processor("g", 18.9));
        itemsList.add(new Processor("h", 50.9));
        itemsList.add(new KeyBoard("i", 16.0));
        itemsList.add(new KeyBoard("j", 35.6));
        itemsList.add(new MotherBoard("k", 31.1));
        itemsList.add(new Mouse("l", 42.8));
        itemsList.add(new KeyBoard("aa", 38.9));
        itemsList.add(new KeyBoard("bb", 56.9));
        itemsList.add(new Mouse("cc", 75.3));
        itemsList.add(new KeyBoard("dd", 65.6));
        itemsList.add(new MotherBoard("ee", 39.1));
        itemsList.add(new Processor("ff", 52.8));
        itemsList.add(new MotherBoard("gg", 98.9));
        itemsList.add(new MotherBoard("hh", 500.09));
        itemsList.add(new MotherBoard("ii", 160.0));
        itemsList.add(new Mouse("jj", 95.6));
        itemsList.add(new KeyBoard("kk", 36.1));
        itemsList.add(new Processor("ll", 40.8));
        itemsList.add(new MotherBoard("ii", 160.0));
        itemsList.add(new KeyBoard("bb", 56.9));
        itemsList.add(new Mouse("cc", 75.3));
        itemsList.add(new KeyBoard("dd", 65.6));
        itemsList.add(new Mouse("jj", 95.6));
        itemsList.add(new KeyBoard("kk", 36.1));
        itemsList.add(new Processor("ll", 40.8));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        new BillImpl().getOrderPrice(itemsList, user);
    }

    @Test()
    public void getOrderPrice_Exactly30Items_Calculated() throws BillException {
        List<EItem> itemsList = new ArrayList<EItem>();
        itemsList.add(new MotherBoard("a", 18.9));
        itemsList.add(new MotherBoard("b", 50.9));
        itemsList.add(new MotherBoard("c", 15.9));
        itemsList.add(new MotherBoard("d", 35.6));
        itemsList.add(new MotherBoard("e", 31.1));
        itemsList.add(new MotherBoard("f", 42.8));
        itemsList.add(new MotherBoard("g", 18.9));
        itemsList.add(new MotherBoard("h", 50.9));
        itemsList.add(new MotherBoard("i", 16.0));
        itemsList.add(new MotherBoard("j", 35.6));
        itemsList.add(new MotherBoard("k", 31.1));
        itemsList.add(new MotherBoard("l", 42.8));
        itemsList.add(new MotherBoard("aa", 38.9));
        itemsList.add(new MotherBoard("bb", 56.9));
        itemsList.add(new MotherBoard("cc", 75.3));
        itemsList.add(new MotherBoard("dd", 65.6));
        itemsList.add(new MotherBoard("ee", 39.1));
        itemsList.add(new MotherBoard("ff", 52.8));
        itemsList.add(new MotherBoard("gg", 98.9));
        itemsList.add(new MotherBoard("hh", 500.09));
        itemsList.add(new MotherBoard("ii", 160.0));
        itemsList.add(new MotherBoard("jj", 95.6));
        itemsList.add(new MotherBoard("kk", 36.1));
        itemsList.add(new MotherBoard("ll", 40.8));
        itemsList.add(new MotherBoard("ii", 160.0));
        itemsList.add(new MotherBoard("bb", 56.9));
        itemsList.add(new MotherBoard("cc", 75.3));
        itemsList.add(new MotherBoard("dd", 65.6));
        itemsList.add(new MotherBoard("jj", 95.6));
        itemsList.add(new MotherBoard("kk", 36.1));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        double price = new BillImpl().getOrderPrice(itemsList, user);

        assertEquals(1926.081, price, 0.01);
    }

    @Test
    public void getOrderPrice_Less10Euros_CommissionApplied() throws BillException {
        List<EItem> oneItemList = new ArrayList<EItem>();
        oneItemList.add(new Mouse("jerry", 8.9));
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));
        double price = new BillImpl().getOrderPrice(oneItemList, user);
        assertEquals(10.9, price, 0.0);
    }

    @Test
    public void shouldGift_AboveAge_NotGiven() {
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2000, 10, 18));

        boolean given = new BillImpl().shouldGift(user, LocalDateTime.of(2021, 05, 21, 18, 31, 02));

        assertFalse(given);
    }

    @Test
    public void shouldGift_NoMoreThan10Gifts_Succeed() {
        List<User> users = new ArrayList<>();
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2006, 10, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2007, 10, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2008, 10, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2009, 10, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2010, 10, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2006, 03, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2007, 03, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2008, 03, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2009, 03, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2010, 03, 18)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2006, 05, 31)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2007, 05, 31)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2008, 05, 31)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2009, 05, 31)));
        users.add(new UserImpl("cicirello@mad.it", LocalDate.of(2010, 05, 31)));
        BillImpl bill = new BillImpl();
        int gifted = 0;

        for (User user : users) {
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 21, 18, 31, 02))) {
                gifted += 1;
            }
        }

        assertTrue(gifted <= 10);
    }

    @Test
    public void shouldGift_NoMoreThanOneGiftPerUser_NotGiven() {
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2006, 10, 18));
        BillImpl bill = new BillImpl();
        int gifted = 0;

        for (int i = 0; i < 1000; i++) {
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 21, 18, 31, 02))) {
                gifted += 1;
            }
        }

        assertTrue(gifted <= 1);
    }

    @Test
    public void shouldGift_AtLeastOneGiftGiven_GiftGiven() {
        BillImpl bill = new BillImpl();
        int gifted = 0;

        for (long i = 0; i < 100000L && gifted < 10; i++) {
            User user = new UserImpl("cicirello@mad.it", LocalDate.of(2006, 10, 18));
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 21, 18, 31, 02))) {
                gifted += 1;
            }
        }

        assertEquals("!!! WARNING: there's a small chance of false negative.", 10, gifted, 2);
    }

    @Test
    public void shouldGift_GiftGivenOutsideTime_NotGiven() {
        BillImpl bill = new BillImpl();
        int gifted = 0;

        for (int i = 0; i < 1000; i++) {
            User user = new UserImpl("cicirello@mad.it", LocalDate.of(2006, 10, 18));
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 21, 06, 31, 02))) {
                gifted += 1;
            }
        }

        assertEquals(0, gifted);
    }

    @Test
    public void shouldGift_GiftGivenInDifferentDays_Given() {
        User user = new UserImpl("cicirello@mad.it", LocalDate.of(2006, 10, 18));
        BillImpl bill = new BillImpl();
        int gifted = 0;

        for (long i = 0; i < 100000L && gifted < 1; i++) {
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 21, 18, 31, 02))) {
                gifted += 1;
            }
        }
        for (long i = 0; i < 100000L && gifted < 2; i++) {
            if (bill.shouldGift(user, LocalDateTime.of(2021, 05, 22, 18, 31, 02))) {
                gifted += 1;
            }
        }

        assertEquals("!!! WARNING: there's a small chance of false negative.", 2, gifted, 1);
    }
}
