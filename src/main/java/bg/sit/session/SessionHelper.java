/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.session;

import bg.sit.business.entities.User;
import java.time.LocalDate;
import java.util.Date;

public class SessionHelper {

    // This is the current logged used setted up on successfull login
    private static User currentUser;

    // This is the limit which a product can be material active type
    private static double maLimit = 500;

    // This is the current date, can be changed for testing purposes
    private static Date currentDate = new Date();

    private static int yearsBeforeDiscard = 5;

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

    public static int getYearsBeforeDiscard() {
        return yearsBeforeDiscard;
    }

    public static void setYearsBeforeDiscard(int yearsBeforeDiscard) {
        SessionHelper.yearsBeforeDiscard = yearsBeforeDiscard;
    }

    public static void setMALimit(double parseDouble) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void setCurrentDate(LocalDate DP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
