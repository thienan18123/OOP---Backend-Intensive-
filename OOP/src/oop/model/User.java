package oop.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String dob;
    private String userName;
    private String password;

    public String toFileString() {
        return firstName + "|" + lastName + "|" + dob + "|" + userName + "|" + password;
    }
}