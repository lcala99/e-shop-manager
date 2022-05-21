////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business;

import java.util.Iterator;
import java.util.List;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill {

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws BillException {
        Double price = 0.0;

        int procCount = 0;
        EItem cheapestProc = null;
        
        int mouseCount = 0;
        EItem cheapestMouse = null;

        for (EItem eItem : itemsOrdered) {
            price += eItem.getPrice();
            if (eItem.getType() == EItem.itemType.Processor) {
                procCount += 1;
                if (cheapestProc == null ||
                        cheapestProc.getPrice() > eItem.getPrice()) {
                    cheapestProc = eItem;
                }
            }

            if (eItem.getType() == EItem.itemType.Mouse) {
                mouseCount += 1;
                if (cheapestMouse == null ||
                        cheapestMouse.getPrice() > eItem.getPrice()) {
                    cheapestMouse = eItem;
                }
            }
        }

        if (procCount > 5) {
            price -= cheapestProc.getPrice() / 2;
        }

        if (mouseCount > 10) {
            price -= cheapestMouse.getPrice();
        }

        return price;
    }

}
