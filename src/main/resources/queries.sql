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
-- Updated Themes Table Structure
-- ============================================

USE expensetracker;

-- Drop old table if exists (be careful in production!)
DROP TABLE IF EXISTS tbl_themes;

-- Create new Themes Table with ID as primary key
CREATE TABLE IF NOT EXISTS tbl_themes (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          type_theme VARCHAR(10) NOT NULL CHECK (type_theme IN ('light', 'dark')),
                                          color VARCHAR(20) NOT NULL,
                                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                          UNIQUE KEY unique_type_color (type_theme, color),
                                          INDEX idx_type_theme (type_theme)
);

-- ============================================
-- Seed Data for Themes
-- ============================================

-- Light Theme Variations
INSERT INTO tbl_themes (type_theme, color) VALUES
                                               ('light', '#FFFFFF'),  -- Pure White
                                               ('light', '#F5F5F5'),  -- WhiteSmoke
                                               ('light', '#FAFAFA'),  -- Very Light Gray
                                               ('light', '#E8F4F8'),  -- Light Blue Tint
                                               ('light', '#FFF8E1'),  -- Light Yellow Tint
                                               ('light', '#F1F8E9'),  -- Light Green Tint
                                               ('light', '#FCE4EC'),  -- Light Pink Tint
                                               ('light', '#F3E5F5')   -- Light Purple Tint
ON DUPLICATE KEY UPDATE
    color = VALUES(color);

-- Dark Theme Variations
INSERT INTO tbl_themes (type_theme, color) VALUES
                                               ('dark', '#121212'),   -- Material Dark
                                               ('dark', '#1E1E1E'),   -- VS Code Dark
                                               ('dark', '#0D1117'),   -- GitHub Dark
                                               ('dark', '#ebd2be'),   -- Your Custom Dark 1
                                               ('dark', '#e5a4cb'),   -- Your Custom Dark 2
                                               ('dark', '#aef6c7'),   -- Your Custom Dark 3
                                               ('dark', '#2C3E50'),   -- Midnight Blue
                                               ('dark', '#1A1A2E'),   -- Deep Purple Dark
                                               ('dark', '#16213E'),   -- Navy Dark
                                               ('dark', '#212121')    -- Almost Black
ON DUPLICATE KEY UPDATE
    color = VALUES(color);

-- ============================================
-- Useful Queries for Testing Themes
-- ============================================

-- View all themes grouped by type
SELECT
    type_theme,
    COUNT(*) as theme_count,
    GROUP_CONCAT(color) as colors
FROM tbl_themes
GROUP BY type_theme;

-- View all light themes
SELECT
    id,
    type_theme,
    color,
    created_at
FROM tbl_themes
WHERE type_theme = 'light'
ORDER BY id;

-- View all dark themes
SELECT
    id,
    type_theme,
    color,
    created_at
FROM tbl_themes
WHERE type_theme = 'dark'
ORDER BY id;

-- Count themes by type
SELECT
    type_theme,
    COUNT(*) as total
FROM tbl_themes
GROUP BY type_theme;

-- Find a specific theme by type and color
SELECT * FROM tbl_themes
WHERE type_theme = 'dark' AND color = '#ebd2be';

-- Get the most recently added themes
SELECT * FROM tbl_themes
ORDER BY created_at DESC
LIMIT 5;



-- ============================================
-- Separated About App and Privacy Tables
-- ============================================

USE expensetracker;

-- Drop old combined table if exists
DROP TABLE IF EXISTS tbl_app_info;

-- ============================================
-- About App Table
-- ============================================

CREATE TABLE IF NOT EXISTS tbl_about_app (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             language_code VARCHAR(10) NOT NULL,
                                             content TEXT NOT NULL,
                                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                             FOREIGN KEY (language_code) REFERENCES tbl_languages(code) ON DELETE CASCADE,
                                             UNIQUE KEY unique_about_language (language_code),
                                             INDEX idx_language_code (language_code)
);

-- ============================================
-- Privacy Policy Table
-- ============================================

CREATE TABLE IF NOT EXISTS tbl_privacy (
                                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                           language_code VARCHAR(10) NOT NULL,
                                           content TEXT NOT NULL,
                                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                           FOREIGN KEY (language_code) REFERENCES tbl_languages(code) ON DELETE CASCADE,
                                           UNIQUE KEY unique_privacy_language (language_code),
                                           INDEX idx_language_code (language_code)
);

-- ============================================
-- Seed Data for About App
-- ============================================

-- English About App
INSERT INTO tbl_about_app (language_code, content)
VALUES
    ('en',
     'Expense Tracker is your personal finance companion designed to help you manage your daily spending, track your income, and visualize your budget with ease. Stay organized and in control of your finances anytime, anywhere. Whether you''re saving for a goal, managing household expenses, or simply want better financial awareness, our intuitive interface makes money management simple and effective.')
ON DUPLICATE KEY UPDATE
    content = VALUES(content);

-- Arabic About App
INSERT INTO tbl_about_app (language_code, content)
VALUES
    ('ar',
     'تطبيق تتبع المصاريف هو رفيقك المالي الشخصي الذي يساعدك على إدارة نفقاتك اليومية وتتبع دخلك وتنظيم ميزانيتك بسهولة. كن دائمًا على اطلاع على وضعك المالي في أي وقت وأي مكان. سواء كنت تدخر لهدف معين، أو تدير نفقات المنزل، أو ترغب ببساطة في وعي مالي أفضل، فإن واجهتنا البديهية تجعل إدارة الأموال بسيطة وفعالة.')
ON DUPLICATE KEY UPDATE
    content = VALUES(content);

-- ============================================
-- Seed Data for Privacy Policy
-- ============================================

-- English Privacy Policy
INSERT INTO tbl_privacy (language_code, content)
VALUES
    ('en',
     'We value your privacy and are committed to protecting your personal data. Expense Tracker does not share, sell, or distribute your personal information to third parties. All data stored in the app is kept securely on your device and our encrypted servers. We use industry-standard security measures to protect your financial information. Your data is used solely to provide and improve the app experience. You have full control over your data and can delete your account at any time. For more details about how we handle your information, please contact our support team.')
ON DUPLICATE KEY UPDATE
    content = VALUES(content);

-- Arabic Privacy Policy
INSERT INTO tbl_privacy (language_code, content)
VALUES
    ('ar',
     'نحن نحترم خصوصيتك ونلتزم بحماية بياناتك الشخصية. تطبيق تتبع المصاريف لا يشارك أو يبيع أو يوزع معلوماتك الشخصية لأطراف ثالثة. جميع البيانات المخزنة في التطبيق محفوظة بأمان على جهازك وخوادمنا المشفرة. نستخدم إجراءات أمنية متوافقة مع معايير الصناعة لحماية معلوماتك المالية. تُستخدم بياناتك فقط لتوفير وتحسين تجربة التطبيق. لديك السيطرة الكاملة على بياناتك ويمكنك حذف حسابك في أي وقت. لمزيد من التفاصيل حول كيفية تعاملنا مع معلوماتك، يرجى الاتصال بفريق الدعم لدينا.')
ON DUPLICATE KEY UPDATE
    content = VALUES(content);
