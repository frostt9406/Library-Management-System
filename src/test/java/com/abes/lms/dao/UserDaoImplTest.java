package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        // Initializing the UserDAO implementation
        userDAO = new UserDAOImpl();
    }

    // Test for userLogin (Valid - Correct username and password)
    @Test
    public void testUserLogin_valid() {
        // Valid: Logging in with correct username and password
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        CollectionUtil.getUserList().add(user);  // Adding a user to the list

        UserDTO result = userDAO.userLogin("john_doe", "password123");
        assertNotNull(result);  // The user should be found
        assertEquals("john_doe", result.getUsername());  // The username should match
    }

    // Test for userLogin (Invalid - Incorrect password)
    @Test
    public void testUserLogin_invalid_wrongPassword() {
        // Invalid: Logging in with the correct username but incorrect password
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        CollectionUtil.getUserList().add(user);  // Adding a user to the list

        UserDTO result = userDAO.userLogin("john_doe", "wrongpassword");
        assertNull(result);  // The user should not be found due to incorrect password
    }

    // Test for userLogin (Invalid - Incorrect username)
    @Test
    public void testUserLogin_invalid_wrongUsername() {
        // Invalid: Logging in with an incorrect username
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        CollectionUtil.getUserList().add(user);  // Adding a user to the list

        UserDTO result = userDAO.userLogin("wrong_username", "password123");
        assertNull(result);  // The user should not be found due to incorrect username
    }

    // Test for userLogin (Invalid - Both username and password incorrect)
    @Test
    public void testUserLogin_invalid_bothIncorrect() {
        // Invalid: Logging in with both incorrect username and password
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        CollectionUtil.getUserList().add(user);  // Adding a user to the list

        UserDTO result = userDAO.userLogin("wrong_username", "wrongpassword");
        assertNull(result);  // The user should not be found because both are incorrect
    }

    // Test for userRegister (Valid - New user registration)
    @Test
    public void testUserRegister_valid() {
        // Valid: Registering a new user
        UserDTO user = new UserDTO("new_user", "new_password", "new@example.com");

        boolean result = userDAO.userRegister(user);
        assertTrue(result);  // The registration should be successful

        UserDTO registeredUser = userDAO.getUser("new_user");
        assertNotNull(registeredUser);  // The user should exist in the list after registration
        assertEquals("new_user", registeredUser.getUsername());  // The username should match
    }

    // Test for userRegister (Invalid - User already exists)
    @Test
    public void testUserRegister_invalid_userExists() {
        // Invalid: Trying to register an existing user
        UserDTO existingUser = new UserDTO("existing_user", "password123", "existing@example.com");
        CollectionUtil.getUserList().add(existingUser);  // Adding an existing user to the list

        UserDTO newUser = new UserDTO("existing_user", "new_password", "new@example.com");

        boolean result = userDAO.userRegister(newUser);
        assertFalse(result);  // The registration should fail because the user already exists
    }

    // Test for getUser (Valid - Existing user)
    @Test
    public void testGetUser_valid() {
        // Valid: Fetching an existing user by username
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        CollectionUtil.getUserList().add(user);  // Adding a user to the list

        UserDTO result = userDAO.getUser("john_doe");
        assertNotNull(result);  // The user should be found
        assertEquals("john_doe", result.getUsername());  // The username should match
    }

    // Test for getUser (Invalid - Non-existing user)
    @Test
    public void testGetUser_invalid_userNotFound() {
        // Invalid: Fetching a non-existing user by username
        UserDTO result = userDAO.getUser("non_existing_user");
        assertNull(result);  // The user should not be found
    }

    // Test for getAllUsers (Valid - Fetching all users)
    @Test
    public void testGetAllUsers_valid() {
        // Valid: Fetching all users in the system
        UserDTO user1 = new UserDTO("john_doe", "password123", "john@example.com");
        UserDTO user2 = new UserDTO("jane_doe", "password456", "jane@example.com");
        CollectionUtil.getUserList().add(user1);  // Adding users to the list
        CollectionUtil.getUserList().add(user2);

        List<UserDTO> result = userDAO.getAllUsers();
        assertNotNull(result);  // The result should not be null
        assertEquals(2, result.size());  // The list should contain two users
    }

    // Test for getAllUsers (Edge Case - No users available)
    @Test
    public void testGetAllUsers_edge_noUsers() {
        // Edge Case: Fetching users when no users are registered
        CollectionUtil.getUserList().clear();  // Clearing the list

        List<UserDTO> result = userDAO.getAllUsers();
        assertNotNull(result);  // The result should not be null
        assertEquals(0, result.size());  // The list should be empty
    }
}