package by.java.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class User {
    private Integer userId;
    private String name;
    private String surname;

    private User() {

    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .toString();
    }

    public static class UserBuilder {
        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder withId(Integer userId){
            user.userId = userId;
            return this;
        }

        public UserBuilder withName(String name){
            user.name = name;
            return this;
        }

        public UserBuilder withSurname(String surname){
            user.surname = surname;
            return this;
        }

        public User build(){
            return user;
        }

    }
}
