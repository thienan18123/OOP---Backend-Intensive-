package oop;

public class User {
    private String firstName;
    private String lastName;
    private String dob;
    private String userName;
    private String password;

    // Constructor — inside the class!
    public User(String firstName, String lastName, String dob, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.userName = userName;
        this.password = password;
    }


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String toFileString() {
        return firstName + "|" + lastName + "|" + dob + "|" + userName + "|" + password;
    }

}  