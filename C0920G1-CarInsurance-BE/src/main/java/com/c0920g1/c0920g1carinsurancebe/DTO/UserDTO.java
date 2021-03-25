package com.c0920g1.c0920g1carinsurancebe.DTO;

public class UserDTO {
    private long id;
    private String password;

    public UserDTO(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
