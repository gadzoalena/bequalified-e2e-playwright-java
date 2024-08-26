package com.bequalified.pages;

import com.microsoft.playwright.Page;

public class CheckoutStepOnePage {
    private Page page;

    // Selectors for the checkout information form fields and buttons
    private String firstNameSelector = "#first-name";
    private String lastNameSelector = "#last-name";
    private String postalCodeSelector = "#postal-code";
    private String continueButtonSelector = "#continue";
    private String cancelButtonSelector = "#cancel";
    private String errorMessageSelector = ".error-message-container";

    public CheckoutStepOnePage(Page page) {
        this.page = page;
    }

    // Method to fill out checkout information
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        page.fill(firstNameSelector, firstName);
        page.fill(lastNameSelector, lastName);
        page.fill(postalCodeSelector, postalCode);
    }

    // Method to click the continue button to proceed
    public void clickContinue() {
        page.click(continueButtonSelector);
    }

    // Method to retrieve error messages displayed
    public String getErrorMessage() {
        return page.textContent(errorMessageSelector);
    }

    // Method to cancel and go back to the previous page
    public void cancelCheckout() {
        page.click(cancelButtonSelector);
    }
}