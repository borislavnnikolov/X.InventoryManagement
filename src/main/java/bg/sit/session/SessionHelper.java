/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.session;

import bg.sit.business.entities.User;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class SessionHelper {

    private static User currentUser;
    private static double maLimit;
    private static Date currentDate = new Date();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SessionHelper.currentUser = currentUser;
    }

    public static double getMaLimit() {
        return maLimit;
    }

    public static void setMaLimit(double maLimit) {
        SessionHelper.maLimit = maLimit;
    }

    public static Date getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(Date currentDate) {
        SessionHelper.currentDate = currentDate;
    }
}
