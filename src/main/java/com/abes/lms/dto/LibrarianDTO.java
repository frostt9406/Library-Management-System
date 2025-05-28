package com.abes.lms.dto;

public class LibrarianDTO {
    private String name;
    private String password;
    private String id;

    public LibrarianDTO(String name, String password, String id) {
        setName(name);
        setPassword(password);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
