////////////////////////////////////////////////////////////////////
// Luca Calabrese 1226305
// Elia Scandaletti 1216751
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import java.util.Date;

public class UserImpl implements User {
    String email;
    Date birthday;

    public UserImpl(String email, Date birthday) {
        super();
        this.email = email;
        this.birthday = birthday;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public Date getBirthday() {
        return this.birthday;
    }
}
