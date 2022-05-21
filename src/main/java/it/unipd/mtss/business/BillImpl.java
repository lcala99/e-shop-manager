////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill {

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws BillException {

        if (itemsOrdered.size() > 30) {
            throw new BillException();
        }

        Double price = 0.0;

        Map<EItem.itemType, Integer> counters = new EnumMap<>(
                EItem.itemType.class);
        Map<EItem.itemType, EItem> cheapestItems = new EnumMap<>(
                EItem.itemType.class);

        for (EItem.itemType type : EItem.itemType.values()) {
            counters.put(type, 0);
            cheapestItems.put(type, null);
        }

        for (EItem eItem : itemsOrdered) {
            price += eItem.getPrice();

            int count = counters.get(eItem.getType());
            counters.replace(eItem.getType(), count + 1);
            EItem cheapestItem = cheapestItems.get(eItem.getType());
            if (cheapestItem == null ||
                    eItem.getPrice() < cheapestItem.getPrice()) {
                cheapestItems.put(eItem.getType(), eItem);
            }
        }

        int procCount = counters.get(EItem.itemType.Processor);
        int mouseCount = counters.get(EItem.itemType.Mouse);
        int keyboardCount = counters.get(EItem.itemType.KeyBoard);
        EItem cheapestProc = cheapestItems.get(EItem.itemType.Processor);
        EItem cheapestMouse = cheapestItems.get(EItem.itemType.Mouse);
        EItem cheapestKB = cheapestItems.get(EItem.itemType.KeyBoard);

        price -= getProcessorDiscount(cheapestProc, procCount);

        price -= getMouseDiscount(cheapestMouse, mouseCount);

        price -= getSameNumberMouseKeyboardDiscount(
                cheapestMouse, mouseCount, cheapestKB, keyboardCount);

        if (price > 1000) {
            price *= 0.9;
        }

        return price;
    }

    private double getProcessorDiscount(EItem cheapestProc, int procCount) {
        if (procCount > 5) {
            return cheapestProc.getPrice() / 2;
        }
        return 0;
    }

    private double getMouseDiscount(EItem cheapestMouse, int mouseCount) {
        if (mouseCount > 10) {
            return cheapestMouse.getPrice();
        }
        return 0;
    }

    private double getSameNumberMouseKeyboardDiscount(
            EItem cheapestMouse, int mouseCount,
            EItem cheapestKB, int keyboardCount) {
        if (mouseCount == keyboardCount && mouseCount > 0) {
            if (cheapestMouse.getPrice() < cheapestKB.getPrice()) {
                if (mouseCount <= 10) {
                    return cheapestMouse.getPrice();
                }
            } else {
                return cheapestKB.getPrice();
            }
        }
        return 0;
    }

}
