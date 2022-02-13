package models;

import java.util.Objects;

public class Department {

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return numberOfEmployees == that.numberOfEmployees &&
                id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, numberOfEmployees, id);
    }


}
