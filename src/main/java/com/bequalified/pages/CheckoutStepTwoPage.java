package com.bequalified.pages;

import com.microsoft.playwright.Page;

public class CheckoutStepTwoPage {
    private Page page;
    
    // Selectors for elements on the Checkout Step Two page
    private String cartItemNameSelector = ".inventory_item_name";
    private String cartItemPriceSelector = ".inventory_item_price";
    private String itemQuantitySelector = ".cart_quantity";
    private String subtotalLabelSelector = "[data-test='subtotal-label']";
    private String taxLabelSelector = "[data-test='tax-label']";
    private String totalLabelSelector = "[data-test='total-label']";
    private String finishButtonSelector = "#finish";
    private String cancelButtonSelector = "#cancel";

    public CheckoutStepTwoPage(Page page) {
        this.page = page;
    }

    // Method to get the names of items in the cart
    public String getCartItemName() {
        return page.locator(cartItemNameSelector).textContent(); 
    }

    // Method to get the price of items in the cart
    public String getCartItemPrice() {
        return page.locator(cartItemPriceSelector).textContent(); 
    }

    // Method to get subtotal price
    public String getSubtotal() {
        return page.locator(subtotalLabelSelector).textContent();
    }

    // Method to get tax amount
    public String getTax() {
        return page.locator(taxLabelSelector).textContent();
    }

    // Method to get total price
    public String getTotal() {
        return page.locator(totalLabelSelector).textContent();
    }

    // Method to finalize checkout
    public void finishCheckout() {
        page.click(finishButtonSelector);
    }

    // Method to cancel checkout
    public void cancelCheckout() {
        page.click(cancelButtonSelector);
    }
}