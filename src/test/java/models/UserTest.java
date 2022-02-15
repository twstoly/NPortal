package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User setUpNewUser() {
        return new User("peter","CEO",5);
    }
    @Test
    public void newUserInstantiatesCorrectly_true() throws Exception {
        User testUser = setUpNewUser();
        assertTrue(true);
    }



    @Test
    public void newuser_getsName_String() throws Exception {
        User testUser = setUpNewUser();
        assertEquals("john",testUser.getName());
    }

    @Test
    public void getAge__getsDescription_manager() throws Exception {
        User testUser = setUpNewUser();
        assertEquals("CEO", testUser.getRole());
    }

    @Test
    public void getPower_getsNumberofemployees_String() throws Exception{
        User testUser = setUpNewUser();
        assertEquals(5, testUser.getDepartmentId());
    }

    @Test
    public void getId_userInstantiatesWithAnId_0() {
        User testUser = setUpNewUser();
        assertEquals(0,testUser.getId());
    }
}
