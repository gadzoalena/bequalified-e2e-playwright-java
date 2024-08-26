package com.bequalified.pages;

import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private Page page;

    private String inventoryItemSelector = ".inventory_item";
    private String addToCartButtonSelector = "[data-test='add-to-cart-%s']";
    private String cartLinkSelector = ".shopping_cart_link";
    private String itemNameSelector = ".inventory_item_name";
    private String itemPriceSelector = ".inventory_item_price";

    public InventoryPage(Page page) {
        this.page = page;
    }

    // Method to get the list of all inventory item names
    public List<String> getInventoryItemNames() {
        return page.locator(itemNameSelector).allTextContents();
    }

    public List<String> getImageFromInventoryByName(String itemName) {
        return page.locator(itemNameSelector).all().stream().map(e -> e.getAttribute("src")).collect(Collectors.toList());
    }
      

    // Method to add an item to the cart by its name
    public void addItemToCart(String itemName) {
        String addToCartButton = String.format(addToCartButtonSelector, itemName.toLowerCase().replace(" ", "-").replace("()", "").replace(".", "-"));
        page.locator(addToCartButton).click();
    }

    // Method to get the price of an inventory item by its name
    public String getItemPrice(String itemName) {
        return page.locator(itemPriceSelector).first().textContent(); 
    }

    // Method to navigate to the cart
    public void goToCart() {
        page.click(cartLinkSelector);
    }

    public String getSauceLabsBackpackImageUrl() {
        return page.locator("[data-test='inventory-item-sauce-labs-backpack-img']").getAttribute("src");
    }
}