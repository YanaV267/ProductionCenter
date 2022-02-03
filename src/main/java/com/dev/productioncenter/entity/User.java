package com.dev.productioncenter.entity;

import java.math.BigInteger;

/**
 * @project Production Center
 * @author YanaV
 * The type User.
 */
public class User extends AbstractEntity {
    private String login;
    private String surname;
    private String name;
    private String email;
    private BigInteger phoneNumber;
    private UserRole userRole;
    private UserStatus userStatus;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets user role.
     *
     * @return the user role
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets user role.
     *
     * @param userRole the user role
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets user status.
     *
     * @return the user status
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * Sets user status.
     *
     * @param userStatus the user status
     */
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
        sb.append("', surname='").append(surname);
        sb.append("', name='").append(name);
        sb.append("', email='").append(email);
        sb.append("', phoneNumber=").append(phoneNumber);
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus).append("}");
        return sb.toString();
    }

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private final User user;

        /**
         * Instantiates a new User builder.
         */
        public UserBuilder() {
            user = new User();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public UserBuilder setId(long id) {
            user.setId(id);
            return this;
        }

        /**
         * Sets login.
         *
         * @param login the login
         * @return the login
         */
        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }

        /**
         * Sets surname.
         *
         * @param surname the surname
         * @return the surname
         */
        public UserBuilder setSurname(String surname) {
            user.surname = surname;
            return this;
        }

        /**
         * Sets name.
         *
         * @param name the name
         * @return the name
         */
        public UserBuilder setName(String name) {
            user.name = name;
            return this;
        }

        /**
         * Sets email.
         *
         * @param email the email
         * @return the email
         */
        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        /**
         * Sets phone number.
         *
         * @param phoneNumber the phone number
         * @return the phone number
         */
        public UserBuilder setPhoneNumber(BigInteger phoneNumber) {
            user.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * Sets user role.
         *
         * @param userRole the user role
         * @return the user role
         */
        public UserBuilder setUserRole(UserRole userRole) {
            user.userRole = userRole;
            return this;
        }

        /**
         * Sets user status.
         *
         * @param userStatus the user status
         * @return the user status
         */
        public UserBuilder setUserStatus(UserStatus userStatus) {
            user.userStatus = userStatus;
            return this;
        }

        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return user;
        }
    }
}
