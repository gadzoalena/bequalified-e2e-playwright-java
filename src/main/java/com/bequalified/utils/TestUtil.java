package com.bequalified.utils;

import com.microsoft.playwright.Page;

public class TestUtil {

    public static void login(Page page, String username, String password) {
        page.fill("#user-name", username);
        page.fill("#password", password);
        page.click("#login-button");
    }
}