package oop.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import oop.model.User;

public class UserService {

	public void register(User user) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true));
			writer.write(user.toFileString());
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error saving user");
		}
	}
	public void showAll() {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            System.out.println("Name: " + parts[0] + " " + parts[1]);
	            System.out.println("DOB: " + parts[2]);
	            System.out.println("Username: " + parts[3]);
	            System.out.println("----------");
	        }
	        reader.close();
	    } catch (IOException e) {
	        System.out.println("Error reading file");
	    }
	}
	public void findByName(String search) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
	        String line;
	        boolean found = false;

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[0].contains(search) || parts[1].contains(search)) {
	                System.out.println("Name: " + parts[0] + " " + parts[1]);
	                System.out.println("DOB: " + parts[2]);
	                System.out.println("Username: " + parts[3]);
	                System.out.println("----------");
	                found = true;
	            }
	        }

	        if (!found) {
	            System.out.println("No users found matching: " + search);
	        }

	        reader.close();
	    } catch (IOException e) {
	        System.out.println("Error reading file");
	    }
	}
	public boolean login(String username, String password) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
	        String line;

	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split("\\|");
	            if (parts[3].equals(username) && parts[4].equals(password)) {
	                reader.close();
	                return true;
	            }
	        }

	        reader.close();
	    } catch (IOException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	    return false;
	}
}
