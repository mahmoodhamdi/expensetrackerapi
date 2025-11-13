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



-- Add these tables to your expensetracker database

USE expensetracker;

-- Create Languages Table
CREATE TABLE IF NOT EXISTS tbl_languages (
                                             code VARCHAR(10) PRIMARY KEY,
                                             name VARCHAR(100) NOT NULL,
                                             name_local VARCHAR(100) NOT NULL,
                                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Create Dictionary Table
CREATE TABLE IF NOT EXISTS tbl_dictionary (
                                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                              language_code VARCHAR(10) NOT NULL,
                                              word VARCHAR(100) NOT NULL,
                                              translation VARCHAR(255) NOT NULL,
                                              created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                              updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                              FOREIGN KEY (language_code) REFERENCES tbl_languages(code) ON DELETE CASCADE,
                                              UNIQUE KEY unique_word_per_language (language_code, word)
);

-- Create Themes Table
CREATE TABLE IF NOT EXISTS tbl_themes (
                                          type_theme VARCHAR(50) PRIMARY KEY,
                                          color VARCHAR(20) NOT NULL,
                                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Insert seed data for languages
INSERT INTO tbl_languages (code, name, name_local) VALUES
                                                       ('en', 'English', 'English'),
                                                       ('ar', 'Arabic', 'العربية')
ON DUPLICATE KEY UPDATE code=code;

-- Insert seed data for dictionary (English)
INSERT INTO tbl_dictionary (language_code, word, translation) VALUES
                                                                  ('en', 'hello', 'Hello'),
                                                                  ('en', 'world', 'World'),
                                                                  ('en', 'welcome', 'Welcome'),
                                                                  ('en', 'goodbye', 'Goodbye')
ON DUPLICATE KEY UPDATE word=word;

-- Insert seed data for dictionary (Arabic)
INSERT INTO tbl_dictionary (language_code, word, translation) VALUES
                                                                  ('ar', 'hello', 'مرحبا'),
                                                                  ('ar', 'world', 'العالم'),
                                                                  ('ar', 'welcome', 'أهلا وسهلا'),
                                                                  ('ar', 'goodbye', 'وداعا')
ON DUPLICATE KEY UPDATE word=word;

-- Insert seed data for themes
INSERT INTO tbl_themes (type_theme, color) VALUES
                                               ('light', '#FFFFFF'),
                                               ('dark', '#121212'),
                                               ('blue', '#0066CC'),
                                               ('green', '#00CC66')
ON DUPLICATE KEY UPDATE type_theme=type_theme;