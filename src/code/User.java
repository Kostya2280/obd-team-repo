import java.sql.Timestamp;

public class User {
    private int userId; // INT PRIMARY KEY
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
