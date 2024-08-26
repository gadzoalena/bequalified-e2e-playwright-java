package com.bequalified.tests;
import com.bequalified.pages.*;
import com.bequalified.utils.TestUtil;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;

public class CheckoutCompleteTest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private CheckoutCompletePage checkoutCompletePage;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    void setupTest() {
        page = browser.newPage();
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
        checkoutStepTwoPage = new CheckoutStepTwoPage(page);
        checkoutCompletePage = new CheckoutCompletePage(page);
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

    @ParameterizedTest
    @CsvSource({
        "standard_user, secret_sauce",
        "problem_user, secret_sauce"
    })
    public void testCompleteCheckoutProcess(String username, String password) {
        page.navigate("https://www.saucedemo.com/");
        TestUtil.login(page, username, password);
        Assertions.assertTrue(page.url().contains("inventory.html"), "Successful login should redirect to inventory");

        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage.checkout();

        checkoutStepOnePage.fillCheckoutInformation("John", "Doe", "90210");
        checkoutStepOnePage.clickContinue();

        String errorMessageSelector = ".error-message-container";

        if ("problem_user".equals(username) && page.isVisible(errorMessageSelector)) {
            String errorMessage = page.textContent(errorMessageSelector);
            System.out.println("Known Issue: Problem user encounters checkout error. Message: " + errorMessage);
            Assertions.assertEquals("Error: Last Name is required", errorMessage, "Expected error message for problem user.");
            return;
        }

        Assertions.assertTrue(checkoutStepTwoPage.getCartItemName().contains("Sauce Labs Backpack"), "The cart should contain the item: Sauce Labs Backpack");

        checkoutStepTwoPage.finishCheckout();

        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getCompleteHeader(), "Completion header does not match");
        Assertions.assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!", checkoutCompletePage.getCompleteText(), "Completion message does not match");

        checkoutCompletePage.goBackToProducts();
        Assertions.assertTrue(page.url().contains("inventory.html"), "Should be redirected back to the products page");
    }

    @Test
    public void testCheckoutWithoutUserInput() {
        page.navigate("https://www.saucedemo.com/");
        TestUtil.login(page, "standard_user", "secret_sauce");
        Assertions.assertTrue(page.url().contains("inventory.html"), "Successful login should redirect to inventory");
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage.checkout();

        // Attempt to continue checkout without filling in information
        checkoutStepOnePage.clickContinue();
        
        // Check the error message
        String actualErrorMessage = checkoutStepOnePage.getErrorMessage();
        
        // Expected vs Actual result handling
        String expectedErrorMessage = "Error: First Name is required";
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage, "Expected the error message about missing user information.");
    }

}