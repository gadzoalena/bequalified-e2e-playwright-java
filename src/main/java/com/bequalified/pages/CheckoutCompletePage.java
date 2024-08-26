package com.bequalified.pages;

import com.microsoft.playwright.Page;

public class CheckoutCompletePage {
    private Page page;
    
    // Selectors for elements on the Checkout Complete page
    private String completeHeaderSelector = "[data-test='complete-header']";
    private String completeTextSelector = "[data-test='complete-text']";
    private String backToProductsButtonSelector = "[data-test='back-to-products']";

    public CheckoutCompletePage(Page page) {
        this.page = page;
    }

    // Method to get the complete header text
    public String getCompleteHeader() {
        return page.textContent(completeHeaderSelector);
    }
    
    // Method to get the complete text message
    public String getCompleteText() {
        return page.textContent(completeTextSelector);
    }

    // Method to return to the home page
    public void goBackToProducts() {
        page.click(backToProductsButtonSelector);
    }
}