package com.snetwork.pageobjects;

import com.snetwork.utils.Constants;
import org.openqa.selenium.WebDriver;

public class PO_FriendsView extends PO_NavView {
    static public void checkFriend(WebDriver driver, String name, String email) {
        PO_HomeView.clickOption(driver, Constants.SIGNUP_STRING, Constants.CLASS_STRING, Constants.BTN_PRIMARY_STRING);
        //Home
    }
}
