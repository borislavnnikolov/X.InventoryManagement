/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import bg.sit.business.entities.User;
import bg.sit.business.enums.RoleType;
import bg.sit.business.services.UserService;
import java.util.List;

import org.junit.*;

   
public class UserServiceTest {

	   private UserService userService = new UserService();
	   
	    @Test
	    void addingUserShouldWorkCorrectly()
	    {
	        userService.addUser("Pesho", "username", "neheshiranaparola", RoleType.NONE);
	 
	        Assert.assertNotNull(userService.getUserByUsername("username", false));
	    }
	 
	    @Test
	    void gettingAllUsersTest()
	    {
	        userService.addUser("test", "test", "test", RoleType.NONE);
	 
	        List<User> users = userService.getUsers();
	 
	        Assert.assertFalse(users.isEmpty());
	    }
	 
	    @Test
	    void gettingByUsernameShouldReturnCorrectData()
	    {
	        User actualUser = userService.getUserByUsername("test", false);
	 
	        Assert.assertEquals("test", actualUser.getUsername());
	    }
	 
	    @Test
	    void updateShouldReturnCorrectUserValues()
	    {
	        userService.updateUser(1, "Ivan", "username", "neheshiranaparola", RoleType.NONE);
	        User actualUser = userService.getUserByID(6);
	 
	        Assert.assertEquals("Ivan", actualUser.getName());
	        Assert.assertEquals("username", actualUser.getUsername());
	    }
	 
	    @Test
	    void deletingUserShouldWorkCorrectly()
	    {
	        userService.deleteUser(1);
	 
	        Assert.assertNull(userService.getUserByID(1));
	    } 
    
}

