package com.bequalified.tests;

import com.bequalified.pages.InventoryPage;
import com.bequalified.pages.CartPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.bequalified.utils.TestUtil;

public class CartTest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void setupTest() {
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com/");
        TestUtil.login(page, "standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
    }

    @AfterEach
    void teardownTest() {
        page.close();
    }

    @AfterAll
    static void teardownClass() {
        browser.close();
        playwright.close();
    }

    @Test
    public void testAddAndRemoveItemFromCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        Assertions.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Backpack"), "Item should be in the cart");

        cartPage.removeItemFromCart("Sauce Labs Backpack");
        Assertions.assertFalse(cartPage.getCartItemNames().contains("Sauce Labs Backpack"), "Item should be removed from the cart");
    }

    public void testCheckoutButtonEnabledWithoutSelection() {
        // Navigate to cart without selecting any products
        inventoryPage.goToCart();
        
        // Attempt to click checkout without any products selected
        boolean isCheckoutButtonEnabled = cartPage.isCheckoutButtonEnabled();
        
        // Check system behavior and expectation
        if (isCheckoutButtonEnabled) {
            page.click(cartPage.getCheckoutButtonSelector());
            Assertions.assertTrue(page.url().contains("checkout-step-one.html"), "System incorrectly allows navigation to checkout-step-one despite no cart items.");
        } else {
            Assertions.fail("Expected checkout button to be enabled according to spotted issue.");
        }
    }

}