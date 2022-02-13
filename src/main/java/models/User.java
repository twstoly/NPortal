package models;

import java.util.Objects;


public class User {
    private int id;
    private String name;
    private String role;
    private int departmentId;

    public User(String name, String role, int departmentId) {
        this.name = name;
        this.role = role;
        this.departmentId = departmentId;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setId(int id) {
        this.id = id;
    }
}











