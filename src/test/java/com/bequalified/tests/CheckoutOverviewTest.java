package com.bequalified.tests;

import com.bequalified.utils.TestUtil;
import com.bequalified.pages.InventoryPage;
import com.bequalified.pages.CartPage;
import com.bequalified.pages.CheckoutStepOnePage;
import com.bequalified.pages.CheckoutStepTwoPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class CheckoutOverviewTest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setupTest() {
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com/");
        TestUtil.login(page, "standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
        checkoutStepTwoPage = new CheckoutStepTwoPage(page);
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
    public void testVerifyCheckoutOverviewAndFinish() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage.checkout();

        checkoutStepOnePage.fillCheckoutInformation("John", "Doe", "90210");
        checkoutStepOnePage.clickContinue();

        Assertions.assertTrue(checkoutStepTwoPage.getCartItemName().contains("Sauce Labs Backpack"), "The cart should contain the item: Sauce Labs Backpack");

        String subtotal = checkoutStepTwoPage.getSubtotal();
        String tax = checkoutStepTwoPage.getTax();
        String total = checkoutStepTwoPage.getTotal();

        // Assuming we already know prices for assertion
        Assertions.assertEquals("Item total: $29.99", subtotal, "Subtotal should match item price");
        Assertions.assertEquals("Tax: $2.40", tax, "Tax should be accurate for the item");
        Assertions.assertEquals("Total: $32.39", total, "Total should be the sum of subtotal and tax");

        checkoutStepTwoPage.finishCheckout();
        Assertions.assertTrue(page.url().contains("checkout-complete.html"), "Successful checkout should redirect to checkout complete page");

    }

    
}