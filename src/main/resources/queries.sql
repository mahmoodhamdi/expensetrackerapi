-- إنشاء قاعدة البيانات
CREATE DATABASE IF NOT EXISTS expensetracker;
USE expensetracker;

-- إنشاء الجدول مع الأعمدة الجديدة (timestamps)
CREATE TABLE IF NOT EXISTS tbl_expenses (
                                            id INT PRIMARY KEY AUTO_INCREMENT,
                                            expense_name VARCHAR(255) NOT NULL,
                                            description VARCHAR(255) NOT NULL,
                                            expense_amount DOUBLE(10, 2) NOT NULL,
                                            category VARCHAR(255) NOT NULL,
                                            date DATE NOT NULL,
                                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- إدخال بيانات تجريبية (seed data)
INSERT INTO tbl_expenses (expense_name, description, expense_amount, category, date)
VALUES
    ("Water bill", "water bill", 600.00, "Bills", "2021-10-14"),
    ("Electricity bill", "electricity bill", 900.00, "Bills", "2021-10-13"),
    ("Internet subscription", "Monthly internet payment", 350.00, "Bills", "2021-10-10"),
    ("Groceries", "Weekly supermarket shopping", 1200.50, "Food", "2021-10-15"),
    ("Restaurant dinner", "Dinner with family", 450.00, "Food", "2021-10-16"),
    ("Fuel", "Car fuel refill", 700.00, "Transport", "2021-10-12"),
    ("Taxi ride", "Ride to airport", 200.00, "Transport", "2021-10-11"),
    ("Netflix subscription", "Monthly entertainment", 150.00, "Entertainment", "2021-10-09"),
    ("Movie tickets", "Cinema night", 180.00, "Entertainment", "2021-10-17"),
    ("Gym membership", "Monthly gym fees", 400.00, "Health", "2021-10-08"),
    ("Pharmacy purchase", "Medicines and supplements", 250.00, "Health", "2021-10-07"),
    ("Mobile recharge", "Prepaid mobile top-up", 100.00, "Bills", "2021-10-06"),
    ("Clothes shopping", "Bought new shirts and jeans", 950.00, "Shopping", "2021-10-05"),
    ("Gift", "Birthday gift for friend", 300.00, "Miscellaneous", "2021-10-04"),
    ("Book purchase", "Programming book from Amazon", 180.00, "Education", "2021-10-03"),
    ("Online course", "Spring Boot Udemy course", 500.00, "Education", "2021-10-02"),
    ("Car maintenance", "Oil change and inspection", 650.00, "Transport", "2021-10-01"),
    ("House rent", "Monthly apartment rent", 4500.00, "Housing", "2021-10-01"),
    ("Coffee shop", "Coffee with friends", 120.00, "Food", "2021-10-13"),
    ("Donation", "Charity donation", 200.00, "Charity", "2021-10-18");
