package com.abes.lms.dao;

import com.abes.lms.dto.LibrarianDTO;
import com.abes.lms.util.CollectionUtil;
import org.junit.jupiter.api.*;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianDAOImplTest {

    private LibrarianDAO librarianDAO;
    private List<LibrarianDTO> mockLibrarianList;
    private MockedStatic<CollectionUtil> mockedStatic;

    @BeforeEach
    public void setup() {
        mockLibrarianList = new ArrayList<>();
        mockedStatic = Mockito.mockStatic(CollectionUtil.class);
        mockedStatic.when(CollectionUtil::getLibarianList).thenReturn(mockLibrarianList);
        librarianDAO = new LibrarianDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        mockedStatic.close(); // Clean up the static mock
    }
    // test case for librarian login
    @Test
    public void testLibrarianLogin_valid() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin("admin", "password123");
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }
    // test case for wrong password
    @Test
    public void testLibrarianLogin_invalid_wrongPassword() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin("admin", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testLibrarianLogin_invalid_wrongUsername() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin("wrongusername", "password123");
        assertNull(result);
    }

    @Test
    public void testLibrarianLogin_invalid_bothIncorrect() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin("wrongusername", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testLibrarianLogin_edge_nullUsername() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin(null, "password123");
        assertNull(result);
    }

    @Test
    public void testLibrarianLogin_edge_nullPassword() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin("admin", null);
        assertNull(result);
    }

    @Test
    public void testLibrarianLogin_edge_nullUsernameAndPassword() {
        LibrarianDTO librarian = new LibrarianDTO("admin", "password123", "admin@gmail.com");
        mockLibrarianList.add(librarian);

        LibrarianDTO result = librarianDAO.librarianLogin(null, null);
        assertNull(result);
    }
}
