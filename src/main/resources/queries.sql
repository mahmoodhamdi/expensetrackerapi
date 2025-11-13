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

-- ============================================
-- Languages and Dictionary Tables
-- ============================================

-- Create Languages Table
CREATE TABLE IF NOT EXISTS tbl_languages (
                                             code VARCHAR(10) PRIMARY KEY,
                                             name VARCHAR(100) NOT NULL,
                                             name_local VARCHAR(100) NOT NULL,
                                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                             INDEX idx_code (code)
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
                                              UNIQUE KEY unique_word_per_language (language_code, word),
                                              INDEX idx_language_code (language_code),
                                              INDEX idx_word (word)
);

-- ============================================
-- Seed Data for Languages
-- ============================================

-- Insert English Language
INSERT INTO tbl_languages (code, name, name_local)
VALUES ('en', 'English', 'English')
ON DUPLICATE KEY UPDATE
                     name = VALUES(name),
                     name_local = VALUES(name_local);

-- Insert Arabic Language
INSERT INTO tbl_languages (code, name, name_local)
VALUES ('ar', 'Arabic', 'العربية')
ON DUPLICATE KEY UPDATE
                     name = VALUES(name),
                     name_local = VALUES(name_local);

-- ============================================
-- Seed Data for English Dictionary
-- ============================================

INSERT INTO tbl_dictionary (language_code, word, translation) VALUES
                                                                   ('en', 'edit', 'Edit'),
                                                                  ('en', 'add', 'Add'),
                                                                  ('en', 'search', 'Search'),
                                                                  ('en', 'filter', 'Filter'),
                                                                  ('en', 'settings', 'Settings'),
                                                                  ('en', 'profile', 'Profile'),
                                                                  ('en', 'logout', 'Logout'),
                                                                  ('en', 'login', 'Login'),
                                                                  ('en', 'register', 'Register'),
                                                                  ('en', 'home', 'Home'),
                                                                  ('en', 'dashboard', 'Dashboard'),
                                                                  ('en', 'reports', 'Reports')
ON DUPLICATE KEY UPDATE
    translation = VALUES(translation);

-- ============================================
-- Seed Data for Arabic Dictionary
-- ============================================

INSERT INTO tbl_dictionary (language_code, word, translation) VALUES
                                                                    ('ar', 'edit', 'تعديل'),
                                                                  ('ar', 'add', 'إضافة'),
                                                                  ('ar', 'search', 'بحث'),
                                                                  ('ar', 'filter', 'تصفية'),
                                                                  ('ar', 'settings', 'الإعدادات'),
                                                                  ('ar', 'profile', 'الملف الشخصي'),
                                                                  ('ar', 'logout', 'تسجيل الخروج'),
                                                                  ('ar', 'login', 'تسجيل الدخول'),
                                                                  ('ar', 'register', 'التسجيل'),
                                                                  ('ar', 'home', 'الرئيسية'),
                                                                  ('ar', 'dashboard', 'لوحة التحكم'),
                                                                  ('ar', 'reports', 'التقارير')
ON DUPLICATE KEY UPDATE
    translation = VALUES(translation);

-- ============================================
-- Themes Table
-- ============================================

CREATE TABLE IF NOT EXISTS tbl_themes (
                                          type_theme VARCHAR(50) PRIMARY KEY,
                                          color VARCHAR(20) NOT NULL,
                                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Insert seed data for themes
INSERT INTO tbl_themes (type_theme, color) VALUES
                                               ('light', '#FFFFFF'),
                                               ('dark', '#121212'),
                                               ('blue', '#0066CC'),
                                               ('green', '#00CC66'),
                                               ('purple', '#9C27B0'),
                                               ('orange', '#FF9800')
ON DUPLICATE KEY UPDATE
    color = VALUES(color);

-- ============================================
-- App Info Table
-- ============================================

CREATE TABLE IF NOT EXISTS tbl_app_info (
                                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                            language_code VARCHAR(10) NOT NULL,
                                            about_app TEXT NOT NULL,
                                            privacy_text TEXT NOT NULL,
                                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                            FOREIGN KEY (language_code) REFERENCES tbl_languages(code) ON DELETE CASCADE,
                                            UNIQUE KEY unique_app_info_language (language_code)
);

-- Insert seed data for App Info (English)
INSERT INTO tbl_app_info (language_code, about_app, privacy_text)
VALUES
    ('en',
     'Expense Tracker is your personal finance companion designed to help you manage your daily spending, track your income, and visualize your budget with ease. Stay organized and in control of your finances anytime, anywhere.',
     'We value your privacy. Expense Tracker does not share or sell your personal data. All information stored in the app is kept securely and used only to enhance your experience. For more details, refer to our privacy policy.')
ON DUPLICATE KEY UPDATE
                     about_app = VALUES(about_app),
                     privacy_text = VALUES(privacy_text);

-- Insert seed data for App Info (Arabic)
INSERT INTO tbl_app_info (language_code, about_app, privacy_text)
VALUES
    ('ar',
     'تطبيق تتبع المصاريف هو رفيقك المالي الشخصي الذي يساعدك على إدارة نفقاتك اليومية وتتبع دخلك وتنظيم ميزانيتك بسهولة. كن دائمًا على اطلاع على وضعك المالي في أي وقت وأي مكان.',
     'نحن نحترم خصوصيتك. تطبيق تتبع المصاريف لا يشارك أو يبيع بياناتك الشخصية. جميع المعلومات التي يتم حفظها في التطبيق آمنة وتُستخدم فقط لتحسين تجربتك. لمزيد من التفاصيل، راجع سياسة الخصوصية.')
ON DUPLICATE KEY UPDATE
                     about_app = VALUES(about_app),
                     privacy_text = VALUES(privacy_text);

-- ============================================
-- Useful Queries for Testing
-- ============================================

-- View all languages with their dictionaries
SELECT
    l.code,
    l.name,
    l.name_local,
    COUNT(d.id) as word_count
FROM tbl_languages l
         LEFT JOIN tbl_dictionary d ON l.code = d.language_code
GROUP BY l.code, l.name, l.name_local;

-- View dictionary for a specific language (English)
SELECT
    word,
    translation
FROM tbl_dictionary
WHERE language_code = 'en'
ORDER BY word;

-- View dictionary for a specific language (Arabic)
SELECT
    word,
    translation
FROM tbl_dictionary
WHERE language_code = 'ar'
ORDER BY word;

-- Search for a specific word across all languages
SELECT
    l.name as language,
    d.word,
    d.translation
FROM tbl_dictionary d
         JOIN tbl_languages l ON d.language_code = l.code
WHERE d.word = 'hello';

-- Get complete language data with dictionary (similar to API response)
SELECT
    l.code,
    l.name,
    l.name_local,
    JSON_OBJECTAGG(d.word, d.translation) as dictionary
FROM tbl_languages l
         LEFT JOIN tbl_dictionary d ON l.code = d.language_code
GROUP BY l.code, l.name, l.name_local;