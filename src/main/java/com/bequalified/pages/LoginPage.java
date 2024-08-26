package com.bequalified.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;

    private String usernameSelector = "#user-name";
    private String passwordSelector = "#password";
    private String loginButtonSelector = "#login-button";
    private String errorMessageSelector = ".error-message-container";

    public LoginPage(Page page) {
        this.page = page;
    }

    // Action to enter any of the accepted usernames
    public void enterUsername(String username) {
        page.fill(usernameSelector, username);
    }

    // Action to enter the password
    public void enterPassword(String password) {
        page.fill(passwordSelector, password);
    }

    // Action to click the login button
    public void clickLogin() {
        page.click(loginButtonSelector);
    }

    // Method to retrieve the error message in case of login failure
    public String getErrorMessage() {
        return page.textContent(errorMessageSelector);
    }
}