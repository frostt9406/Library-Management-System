package com.abes.lms.dao;

import com.abes.lms.dto.UserDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private UserDAO userDAO;
    private List<UserDTO> userList;

    @BeforeEach
    public void setup() {
        userList = CollectionUtil.getUserList();
        userList.clear(); // Reset shared list for isolation
        userDAO = new UserDAOImpl();
    }

    @Test
    public void testUserLogin_valid() {
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        userList.add(user);

        UserDTO result = userDAO.userLogin("john_doe", "password123");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
    }

    @Test
    public void testUserLogin_invalid_wrongPassword() {
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        userList.add(user);

        UserDTO result = userDAO.userLogin("john_doe", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testUserLogin_invalid_wrongUsername() {
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        userList.add(user);

        UserDTO result = userDAO.userLogin("wrong_username", "password123");
        assertNull(result);
    }

    @Test
    public void testUserLogin_invalid_bothIncorrect() {
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        userList.add(user);

        UserDTO result = userDAO.userLogin("wrong_username", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testUserRegister_valid() {
        UserDTO user = new UserDTO("new_user", "new_password", "new@example.com");

        boolean result = userDAO.userRegister(user);
        assertTrue(result);

        UserDTO registeredUser = userDAO.getUser("new_user");
        assertNotNull(registeredUser);
        assertEquals("new_user", registeredUser.getUsername());
    }

    @Test
    public void testUserRegister_invalid_userExists() {
        UserDTO existingUser = new UserDTO("existing_user", "password123", "existing@example.com");
        userList.add(existingUser);

        UserDTO newUser = new UserDTO("existing_user", "new_password", "new@example.com");

        boolean result = userDAO.userRegister(newUser);
        assertFalse(result);
    }

    @Test
    public void testGetUser_valid() {
        UserDTO user = new UserDTO("john_doe", "password123", "john@example.com");
        userList.add(user);

        UserDTO result = userDAO.getUser("john_doe");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
    }

    @Test
    public void testGetUser_invalid_userNotFound() {
        UserDTO result = userDAO.getUser("non_existing_user");
        assertNull(result);
    }

    @Test
    public void testGetAllUsers_valid() {
        UserDTO user1 = new UserDTO("john_doe", "password123", "john@example.com");
        UserDTO user2 = new UserDTO("jane_doe", "password456", "jane@example.com");
        userList.add(user1);
        userList.add(user2);

        List<UserDTO> result = userDAO.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllUsers_edge_noUsers() {
        List<UserDTO> result = userDAO.getAllUsers();
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
