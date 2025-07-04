# 🍸 Cocktail Order Management System

---

Welcome to the Cocktail Order Management System! This application streamlines the process of ordering cocktails for customers and managing orders for barmakers.

## ✨ Features

### For Customers:
* **Browse the Menu:** Explore a diverse range of cocktails organized by categories.
* **Build Your Cart:** Add your favorite cocktails to your shopping cart.
* **View Cart:** Easily review the contents of your cart before ordering.
* **Place Orders:** Submit your cocktail orders with a simple click.
* **Track Order Status:** Monitor the real-time progress of your order (**Ordered**, **In Preparation**, **Completed**).

### For Barmakers:
* **Menu Configuration:** Create and manage your cocktail menu, including categories and individual cocktails.
* **Cocktail Customization:** Define cocktails with ingredients, sizes (S, M, L), and corresponding prices for each size.
* **Order Management:** View a comprehensive list of pending orders to be processed.
* **Detailed Order View:** Select an order to see the list of cocktails requested by the customer.
* **Step-by-Step Preparation Tracking:** Advance individual cocktails through their preparation stages: **Ingredient Preparation**, **Assembly**, **Garnishing**, and **Completed**.
* **Automated Order Completion:** Once all cocktails within an order are marked as "Completed," the entire order status automatically updates to "Completed."

## 🚀 Technologies Used

This project is built with a robust and modern technology stack:

<p align="center">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring Boot" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/vuejs/vuejs-original.svg" alt="Vue.js" width="60" height="60"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/typescript/typescript-original.svg" alt="TypeScript" width="60" height="60"/>
</p>

* **Java**: The core backend logic is powered by Java, providing a strong and scalable foundation.
* **Spring Boot**: Simplifies the development of production-ready Spring applications, enabling rapid API development.
* **PostgreSQL**: A powerful, open-source relational database used for storing all application data, including menu items, orders, and user information.
* **Vue.js**: A progressive JavaScript framework used for building the interactive and responsive user interfaces for both customers and barmakers.
* **TypeScript**: Enhances JavaScript development by adding static typing, leading to more robust and maintainable frontend code.

## ⚙️ How to Run

To get this project up and running on your local machine, follow these steps:

1.  **Backend Setup (Spring Boot)**
    * Navigate to the backend project directory (where your `pom.xml` is located).
    * Clean and build the project:
        ```bash
        mvn clean install
        ```
    * Run the Spring Boot application:
        ```bash
        mvn spring-boot:run
        ```

2.  **Frontend Setup (Vue.js)**
    * Navigate to the frontend project directory.
    * Install the necessary dependencies:
        ```bash
        npm install
        ```
    * Start the development server:
        ```bash
        npm run dev
        ```

---

Feel free to reach out if you have any questions or need further assistance!