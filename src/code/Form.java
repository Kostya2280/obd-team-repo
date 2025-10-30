import java.sql.Timestamp;

public class Form {
    private int formId; // INT PRIMARY KEY
    private String title;
    private String description;
    private Timestamp createdAt;
    private int userId; // FOREIGN KEY

    public Form() {}
    public Form(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public int getFormId() { return formId; }
    public void setFormId(int formId) { this.formId = formId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
