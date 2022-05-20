package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import org.junit.Test;

public class UserImplTest {
    @Test
    public void UserImpl_getEmail_Success() {
        User antonio = new UserImpl("antonio.cognome@mailg.com", new Date(971890332000L));
        String mail = antonio.getEmail();
        assertEquals("antonio.cognome@mailg.com", mail);
    }

    @Test
    public void UserImpl_getBirthDay_Success() {
        User antonio = new UserImpl("antonio.cognome@mailg.com", new Date(971890332000L));
        Date birthday = antonio.getBirthday();
        assertEquals(new Date(971890332000L), birthday);
    }
}
