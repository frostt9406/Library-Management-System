package com.abes.lms.dto;

package com.abes.lms.dao;

import com.abes.lms.dao.UserDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }

    // Happy Path
    @Test
    void addAndRetrieveValidUser() {
        UserDTO user = new UserDTO("user1", "user1@example.com", "pass123");
        assertTrue(userDAO.addUser(user));

        UserDTO result = userDAO.getUser("user1");
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    // Invalid: Duplicate user
    @Test
    void addDuplicateUserShouldFail() {
        UserDTO user1 = new UserDTO("user2", "email1@example.com", "abc123");
        UserDTO user2 = new UserDTO("user2", "email2@example.com", "xyz456");

        userDAO.addUser(user1);
        boolean addedAgain = userDAO.addUser(user2);

        assertFalse(addedAgain);
    }

    // Invalid: Null user
    @Test
    void addNullUserShouldThrowException() {
        assertThrows(NullPointerException.class, () -> userDAO.addUser(null));
    }

    // Invalid: Get unknown user
    @Test
    void getNonExistentUserShouldReturnNull() {
        UserDTO result = userDAO.getUser("ghostUser");
        assertNull(result);
    }
}
