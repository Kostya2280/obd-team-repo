import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();

        // 1. Створення об'єкта (DML: INSERT)
        User newUser = new User(
                "Тестовий Студент",
                "student_test_01@corp.edu",
                "Student"
        );
        // Створюємо та отримуємо згенерований ID
        int newUserId = userDAO.createAndReturnKey(newUser);

        if (newUserId > 0) {
            System.out.println("--- Тестування CRUD ---");

            // 2. Пошук існуючого об'єкта (DML: SELECT)
            User foundUser = userDAO.getById(newUserId);
            System.out.println("\n[SELECT] Знайдено користувача: " + (foundUser != null ? foundUser.getName() : "Не знайдено"));

            if (foundUser != null) {
                // 3. Оновлення об'єкта (DML: UPDATE)
                foundUser.setName("Оновлений Студент (DAO)");
                userDAO.update(foundUser);

                // 4. Перевірка оновлення (DML: SELECT повторно)
                User updatedUser = userDAO.getById(newUserId);
                System.out.println("[SELECT] Перевірка оновлення: " + updatedUser.getName());
                
            }
        }
    }
}
