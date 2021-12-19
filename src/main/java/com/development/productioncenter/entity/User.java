package com.development.productioncenter.entity;

import java.math.BigInteger;

public class User extends AbstractEntity {
    private String login;
    private String password;
    private String surname;
    private String name;
    private String email;
    private BigInteger phoneNumber;
    private UserRole userRole;
    private UserStatus userStatus;

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != getClass())
            return false;
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (password != null ? !password.equals(user.password) : user.password != null) {
            return false;
        }
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) {
            return false;
        }
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) {
            return false;
        }
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) {
            return false;
        }
        return userStatus != null ? userStatus.equals(user.userStatus) : user.userStatus == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (login != null ? login.hashCode() : 0);
        result = result * 31 + (password != null ? password.hashCode() : 0);
        result = result * 31 + (surname != null ? surname.hashCode() : 0);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        result = result * 31 + (email != null ? email.hashCode() : 0);
        result = result * 31 + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = result * 31 + (userRole != null ? userRole.hashCode() : 0);
        result = result * 31 + (userStatus != null ? userStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("login='").append(login);
        sb.append("', password='").append(password);
        sb.append("', surname='").append(surname);
        sb.append("', name='").append(name);
        sb.append("', email='").append(email);
        sb.append("', phoneNumber=").append(phoneNumber);
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus).append("}");
        return sb.toString();
    }

    public static class UserBuilder {
        private final User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            user.surname = surname;
            return this;
        }

        public UserBuilder setName(String name) {
            user.name = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        public UserBuilder setPhoneNumber(BigInteger phoneNumber) {
            user.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            user.userRole = userRole;
            return this;
        }

        public UserBuilder setUserStatus(UserStatus userStatus) {
            user.userStatus = userStatus;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
