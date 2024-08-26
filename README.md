# 🎭 Playwright Java Testing Suite for SauceDemo

This project provides an automated testing suite for the SauceDemo web application using Java, Playwright, and JUnit. It covers key scenarios such as login functionality, cart operations, checkout processes, and various validations.

![Java](https://www.oracle.com/java/technologies/downloads/) ![Maven](https://img.shields.io/badge/Maven-%3E%3D3.6.0-brightgreen) ![JUnit](https://img.shields.io/badge/JUnit-5.8-green)

## 📑 Table of Contents

- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Known Issues](#known-issues)

## 📁 Project Structure

```plaintext
src
├── main
│   └── java
│       └── com
│           └── bequalified
│               ├── pages
│               │   ├── CartPage.java
│               │   ├── CheckoutCompletePage.java
│               │   ├── CheckoutStepOnePage.java
│               │   ├── CheckoutStepTwoPage.java
│               │   ├── InventoryPage.java
│               │   └── LoginPage.java
│               └── utils
│                   └── TestUtil.java
└── test
    └── java
        └── com
            └── bequalified
                └── tests
                    ├── CartTest.java
                    ├── CheckoutCompleteTest.java
                    ├── CheckoutOverviewTest.java
                    ├── CheckoutTest.java
                    ├── InventoryTest.java
                    ├── LoginTest.java


## ⚙️ Setup Instructions

Prerequisites
Java 20 or higher
Maven 3.6.0 or higher
Node.js and Playwright

Installation

Clone the Repository:

git clone https://github.com/gadzoalena/bequalified-e2e-playwright-java.git
cd bequalified-e2e-playwright-java


Install Dependencies:
mvn clean install

Set Up Playwright Browsers:
npx playwright install

🚀 Running Tests
Single Test Execution
Run an individual test class:
mvn test -Dtest=LoginTest



Full Test Suite Execution
Execute all tests:
mvn clean test



Report Generation
Create and view the test report:
mvn surefire-report:report
Access the report under target/site/surefire-report.html.


🚩 Known Issues
Postal Code Validation: Current implementation does not correctly restrict input to numeric values.
Visual Bugs: visual_user experiences incorrect image displays
Checkout Button Behavior: A proper check for cart items is missing before enabling the checkout button.
Multiple validation messages are not shown, but it is only showing validation error for the first error.