////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.business.exception;

import org.junit.Test;

public class BillExceptionTest extends Throwable {

    @Test(expected = BillException.class)
    public void BillException_Throws_Thrown() throws BillException {
        throw new BillException();
    }
}
