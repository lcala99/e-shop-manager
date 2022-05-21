////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import java.time.LocalDate;

public class UserImpl implements User {
    String email;
    LocalDate birthday;

    public UserImpl(String email, LocalDate birthday) {
        super();
        this.email = email;
        this.birthday = birthday;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public LocalDate getBirthday() {
        return this.birthday;
    }
}
