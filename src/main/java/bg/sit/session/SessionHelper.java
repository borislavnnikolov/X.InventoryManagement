/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.session;

import bg.sit.business.entities.User;

/**
 *
 * @author Dell
 */
public class SessionHelper {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SessionHelper.currentUser = currentUser;
    }
}
