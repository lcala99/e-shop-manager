package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import org.junit.Test;

public class UserImplTest {
    @Test
    public void UserImpl_getEmail_Success() {
        User antonio = new UserImpl("antonio.cognome@mailg.com", LocalDate.of(1980, 12, 21));
        String mail = antonio.getEmail();
        assertEquals("antonio.cognome@mailg.com", mail);
    }

    @Test
    public void UserImpl_getBirthDay_Success() {
        User antonio = new UserImpl("antonio.cognome@mailg.com", LocalDate.of(1980, 12, 21));
        LocalDate birthday = antonio.getBirthday();
        assertEquals(LocalDate.of(1980, 12, 21), birthday);
    }
}
