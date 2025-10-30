import java.sql.*;
import java.sql.Statement;

public class UserDAOImpl implements GenericDAO<User, Integer> {

    // Спеціальний метод create для отримання згенерованого ключа
    public int createAndReturnKey(User user) {
        String sql = "INSERT INTO User (Name, Email, Role) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        user.setUserId(generatedId);
                        System.out.println("[CREATE] User " + user.getEmail() + " created with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void create(User user) {
        // Використовуємо спеціалізований метод, але імплементуємо інтерфейс
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
            System.out.println("[UPDATE] User " + user.getUserId() + " updated.");

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
            System.out.println("[DELETE] User " + id + " deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public java.util.List<User> getAll() { return null; }
}
