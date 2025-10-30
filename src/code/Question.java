public class Question {
    private int questionId; // INT PRIMARY KEY
    private int formId; // FOREIGN KEY
    private String text;
    private String type; // ENUM
    private boolean isRequired;

    public Question() {}
    public Question(int formId, String text, String type, boolean isRequired) {
        this.formId = formId;
        this.text = text;
        this.type = type;
        this.isRequired = isRequired;
    }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public int getFormId() { return formId; }
    public void setFormId(int formId) { this.formId = formId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { isRequired = required; }
}
