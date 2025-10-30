# 🔬 Лабораторна робота №6. Реалізація об’єктно-реляційного відображення

## 1. 📖 Призначення шаблону DAO

**Data Access Object (DAO)** – це об'єкт, що надає абстрактний інтерфейс до бази даних.

**Призначення:** Реалізація CRUD-операцій без розкриття деталей СУБД.  
**Функція:** Ізоляція бізнес-логіки від доступу до даних.  
**Персистентність:** Здатність стану існувати довше, ніж процес, що створив його.

---

## 2. 🗃️ Створення бази даних у MySQL

```sql
CREATE DATABASE IF NOT EXISTS KPI_Forms;
USE KPI_Forms;

CREATE TABLE User (
  UserID INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(100) NOT NULL,
  Email VARCHAR(255) UNIQUE NOT NULL,
  CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  Role VARCHAR(50)
);

CREATE TABLE Form (
  FormID INT AUTO_INCREMENT PRIMARY KEY,
  Title VARCHAR(255) NOT NULL,
  Description TEXT,
  CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  UserID INT NOT NULL,
  FOREIGN KEY (UserID) REFERENCES User(UserID)
);

CREATE TABLE Question (
  QuestionID INT AUTO_INCREMENT PRIMARY KEY,
  FormID INT NOT NULL,
  Text TEXT NOT NULL,
  Type VARCHAR(50) NOT NULL CHECK (Type IN ('text', 'multiple_choice', 'checkbox', 'scale')),
  IsRequired BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (FormID) REFERENCES Form(FormID)
);

CREATE TABLE AnswerOption (
  OptionID INT AUTO_INCREMENT PRIMARY KEY,
  QuestionID INT NOT NULL,
  OptionText VARCHAR(255) NOT NULL,
  FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID)
);

CREATE TABLE Response (
  ResponseID INT AUTO_INCREMENT PRIMARY KEY,
  FormID INT NOT NULL,
  SubmittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (FormID) REFERENCES Form(FormID)
);

CREATE TABLE Answer (
  AnswerID INT AUTO_INCREMENT PRIMARY KEY,
  ResponseID INT NOT NULL,
  QuestionID INT NOT NULL,
  OptionID INT,
  TextAnswer TEXT,
  FOREIGN KEY (ResponseID) REFERENCES Response(ResponseID),
  FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID),
  FOREIGN KEY (OptionID) REFERENCES AnswerOption(OptionID)
);
```

---

## 3. 🧩 Створення Bean-класів (Об'єкти предметної області)

### Клас `User`

```java
import java.sql.Timestamp;

public class User {
    private int userId;
    private String name;
    private String email;
    private Timestamp createdAt;
    private String role;

    public User() {}
    public User(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
```

### Клас `Form`

```java
import java.sql.Timestamp;

public class Form {
    private int formId;
    private String title;
    private String description;
    private Timestamp createdAt;
    private int userId;

    public Form() {}
    public Form(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    // Getters and setters
}
```

### Клас `Question`

```java
public class Question {
    private int questionId;
    private int formId;
    private String text;
    private String type;
    private boolean isRequired;

    public Question() {}
    public Question(int formId, String text, String type, boolean isRequired) {
        this.formId = formId;
        this.text = text;
        this.type = type;
        this.isRequired = isRequired;
    }

    // Getters and setters
}
```

### Клас `AnswerOption`

```java
public class AnswerOption {
    private int optionId;
    private int questionId;
    private String optionText;

    public AnswerOption() {}
    public AnswerOption(int questionId, String optionText) {
        this.questionId = questionId;
        this.optionText = optionText;
    }

    // Getters and setters
}
```

### Клас `Response`

```java
import java.sql.Timestamp;

public class Response {
    private int responseId;
    private int formId;
    private Timestamp submittedAt;

    public Response() {}
    public Response(int formId) {
        this.formId = formId;
    }

    // Getters and setters
}
```

### Клас `Answer`

```java
public class Answer {
    private int answerId;
    private int responseId;
    private int questionId;
    private Integer optionId;
    private String textAnswer;

    public Answer() {}
    public Answer(int responseId, int questionId, Integer optionId, String textAnswer) {
        this.responseId = responseId;
        this.questionId = questionId;
        this.optionId = optionId;
        this.textAnswer = textAnswer;
    }

    // Getters and setters
}
```

---

## 4. 🛠️ Розробка DAO-інфраструктури

### Клас `DBConnector`

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/KPI_Forms";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}
```

### Інтерфейс `GenericDAO`

```java
import java.util.List;

public interface GenericDAO<T, K> {
    void create(T entity);
    T getById(K id);
    List<T> getAll();
    void update(T entity);
    void delete(K id);
}
```

### Клас `UserDAOImpl`

```java
import java.sql.*;
import java.util.List;

public class UserDAOImpl implements GenericDAO<User, Integer> {

    public int createAndReturnKey(User user) {
        String sql = "INSERT INTO User (Name, Email, Role) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    user.setUserId(generatedId);
                    System.out.println("[CREATE] User created with ID: " + generatedId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void create(User user) {
        createAndReturnKey(user);
    }

    @Override
    public User getById(Integer id) {
        String sql = "SELECT * FROM User WHERE UserID = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getString("Role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE User SET Name = ?, Email = ?, Role = ? WHERE UserID = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
            System.out.println("[UPDATE] User updated: " + user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[DELETE] User deleted: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() { return null; }
}
```

---

## 5. 🧪 Тестування

```java
public class Main {
    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();

        User newUser = new User("Тестовий Студент", "student_test_01@corp.edu", "Student");
        int newUserId = userDAO.createAndReturnKey(newUser);

        if (newUserId > 0) {
            System.out.println("--- Тестування CRUD ---");

            User foundUser = userDAO.getById(newUserId);
            System.out.println("[SELECT] Знайдено користувача: " + (foundUser != null ? foundUser.getName() : "Не знайдено"));

            if (foundUser != null) {
                foundUser.setName("Оновлений Студент (DAO)");
                userDAO.update(foundUser);

                User updatedUser = userDAO.getById(newUserId);
                System.out.println("[SELECT] Перевірка оновлення: " + updatedUser.getName());
            }
        }
    }
}
```

---

## 6. 🔍 Перевірка результатів у MySQL

Після запуску скрипта обліковий запис успішно створюється та додається до бази даних.

![test_photo](.\images\test_photo.jpg)