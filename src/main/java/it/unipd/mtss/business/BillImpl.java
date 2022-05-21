////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill {

    static Set<User> giftedUsers = new HashSet<>();
    static LocalDate lastGiven = LocalDate.MIN;

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws BillException {

        if (itemsOrdered.size() > 30) {
            throw new BillException();
        }

        if (shouldGift(user, LocalDateTime.now())) {
            return 0;
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
        } else if (price < 10) {
            price += 2;
        }

        return price;
    }

    double getProcessorDiscount(EItem cheapestProc, int procCount) {
        if (procCount > 5) {
            return cheapestProc.getPrice() / 2;
        }
        return 0;
    }

    double getMouseDiscount(EItem cheapestMouse, int mouseCount) {
        if (mouseCount > 10) {
            return cheapestMouse.getPrice();
        }
        return 0;
    }

    double getSameNumberMouseKeyboardDiscount(
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

    boolean shouldGift(User user, LocalDateTime time) {

        // if it's the first order of the day...
        if (lastGiven.isBefore(time.toLocalDate())) {
            // ...resets giftedUsers because no one has been gifted today
            giftedUsers = new HashSet<>();
            // ...set the date which giftedUser is valid for
            lastGiven = time.toLocalDate();
        }

        // if time is correct...
        if (time.getHour() == 18) {

            // ...and user is underage...
            if (Period.between(user.getBirthday(), time.toLocalDate())
                    .getYears() < 18) {

                // ...and hasn't already been gifted today...
                if (!giftedUsers.contains(user)) {

                    // ...and not alla gift have been sent...
                    if (giftedUsers.size() < 10) {

                        // ...and user is lucky
                        if (new Random().nextDouble() < 10) {
                            giftedUsers.add(user);
                            // ...then user is gifted
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
