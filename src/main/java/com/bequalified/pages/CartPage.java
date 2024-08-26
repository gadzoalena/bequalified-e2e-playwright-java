package com.bequalified.pages;

import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private Page page;

    // Selectors for elements on the cart page
    private String cartItemSelector = ".cart_item";
    private String cartItemNameSelector = ".inventory_item_name";
    private String cartItemRemoveButtonSelector = "[data-test='remove-%s']";
    private String cartBadgeSelector = ".shopping_cart_badge";
    private String continueShoppingButtonSelector = "#continue-shopping";
    private String checkoutButtonSelector = "#checkout";

    public CartPage(Page page) {
        this.page = page;
    }

    // Get a list of all item names in the cart
    public List<String> getCartItemNames() {
        return page.locator(cartItemNameSelector).allTextContents();
    }

    // Remove an item from the cart by its name
    public void removeItemFromCart(String itemName) {
        String itemRemoveButton = String.format(cartItemRemoveButtonSelector, itemName.toLowerCase().replace(" ", "-").replace("()", "").replace(".", "-"));
        page.locator(itemRemoveButton).click();
    }

    // Get the number of items in the cart from the cart badge
    public String getCartItemCount() {
        return page.isVisible(cartBadgeSelector) ? page.locator(cartBadgeSelector).textContent() : "0";
    }

    // Navigate back to the inventory page from the cart
    public void continueShopping() {
        page.click(continueShoppingButtonSelector);
    }

    // Proceed to the checkout page
    public void checkout() {
        page.click(checkoutButtonSelector);
    }

    // Check if the checkout button is enabled
    public boolean isCheckoutButtonEnabled() {
        return !page.locator(checkoutButtonSelector).getAttribute("disabled").equals("true");
    }

    // Get the selector for the checkout button
    public String getCheckoutButtonSelector() {
        return checkoutButtonSelector;
    }
}