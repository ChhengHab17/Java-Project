create database db;
use db;

CREATE TABLE IF NOT EXISTS newusers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_expenses DECIMAL(10,2) NOT NULL,
    pdf_file LONGBLOB NOT null,
    FOREIGN KEY (user_id) REFERENCES newusers(id) ON DELETE CASCADE
);

create table expenses (
  id int auto_increment primary key,
  user_id INT NOT NULL,
  category varchar(50),
  description varchar(256),
  amount decimal(10,2) not null,
  date date not null,
  currency enum("USD", "KHR") not null,
  FOREIGN KEY (user_id) REFERENCES newusers(id) ON DELETE CASCADE
);
CREATE TABLE Budget (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    weeklyBudget DOUBLE,
    monthlyBudget DOUBLE,
    setDate DATE NOT NULL DEFAULT CURRENT_DATE,
    editDate DATE NOT NULL DEFAULT CURRENT_DATE,
    convertedWeeklyBudget DOUBLE,
    convertedMonthlyBudget DOUBLE,
    currency VARCHAR(3)
);

select 
  sum(case when e.currency = "USD" then amount else amount / 4100 end) total_in_usd,
  sum(case when e.currency = "KHR" then amount else amount * 4100 end) total_in_khr
from expenses e;
