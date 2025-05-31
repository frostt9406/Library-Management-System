package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {

    private UserDAOImpl userDAO;

    @BeforeEach
    void setup() {
        // Clear shared user list before each test for isolation
        CollectionUtil.getUserList().clear();
        userDAO = new UserDAOImpl();
    }

    // -------- userLogin --------

    @Test
    void userLogin_HappyPath_ValidCredentials_ReturnsUser() {
        UserDTO user = new UserDTO("john_doe", "password123", "John Doe");
        userDAO.userRegister(user);

        UserDTO loggedInUser = userDAO.userLogin("john_doe", "password123");

        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
    }

    @Test
    void userLogin_ValidEdge_UsernameCaseInsensitive() {
        UserDTO user = new UserDTO("JaneDoe", "securePass", "Jane Doe");
        userDAO.userRegister(user);

        UserDTO loggedInUser = userDAO.userLogin("janedoe", "securePass");

        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
    }

    @Test
    void userLogin_Invalid_WrongPassword() {
        UserDTO user = new UserDTO("alice", "pass123", "Alice");
        userDAO.userRegister(user);

        UserDTO loggedInUser = userDAO.userLogin("alice", "wrongpass");

        assertNull(loggedInUser);
    }

    @Test
    void userLogin_Invalid_UserNotFound() {
        UserDTO loggedInUser = userDAO.userLogin("nonexistent", "anyPassword");
        assertNull(loggedInUser);
    }

    // -------- userRegister --------

    @Test
    void userRegister_HappyPath_NewUserReturnsTrue() {
        UserDTO newUser = new UserDTO("bob", "bobpass", "Bob Builder");
        boolean registered = userDAO.userRegister(newUser);

        assertTrue(registered);
        assertEquals(1, CollectionUtil.getUserList().size());
        assertSame(newUser, CollectionUtil.getUserList().get(0));
    }

    @Test
    void userRegister_ValidEdge_RejectDuplicateUsername() {
        UserDTO user1 = new UserDTO("charlie", "pass1", "Charlie");
        UserDTO user2 = new UserDTO("Charlie", "pass2", "Charles");

        userDAO.userRegister(user1);
        boolean registered = userDAO.userRegister(user2);

        assertFalse(registered);
        assertEquals(1, CollectionUtil.getUserList().size());
    }

    @Test
    void userRegister_Invalid_NullUser() {
        assertThrows(NullPointerException.class, () -> userDAO.userRegister(null));
    }

    // -------- getUser --------

    @Test
    void getUser_HappyPath_ReturnsUser() {
        UserDTO user = new UserDTO("david", "davidpass", "David");
        userDAO.userRegister(user);

        UserDTO foundUser = userDAO.getUser("david");

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void getUser_ValidEdge_UsernameCaseInsensitive() {
        UserDTO user = new UserDTO("eve", "evepass", "Eve");
        userDAO.userRegister(user);

        UserDTO foundUser = userDAO.getUser("EVE");

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void getUser_Invalid_NotFound() {
        UserDTO foundUser = userDAO.getUser("ghost");
        assertNull(foundUser);
    }

    // -------- getAllUsers --------

    @Test
    void getAllUsers_HappyPath_ReturnsAllUsers() {
        UserDTO user1 = new UserDTO("user1", "pass1", "User One");
        UserDTO user2 = new UserDTO("user2", "pass2", "User Two");
        userDAO.userRegister(user1);
        userDAO.userRegister(user2);

        List<UserDTO> allUsers = userDAO.getAllUsers();

        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    void getAllUsers_ValidEdge_EmptyList() {
        List<UserDTO> allUsers = userDAO.getAllUsers();

        assertNotNull(allUsers);
        assertTrue(allUsers.isEmpty());
    }
}