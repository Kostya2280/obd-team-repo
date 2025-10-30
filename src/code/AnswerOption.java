public class AnswerOption {
    private int optionId; // INT PRIMARY KEY
    private int questionId; // FOREIGN KEY
    private String optionText;

    public AnswerOption() {}
    public AnswerOption(int questionId, String optionText) {
        this.questionId = questionId;
        this.optionText = optionText;
    }

    public int getOptionId() { return optionId; }
    public void setOptionId(int optionId) { this.optionId = optionId; }
    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
}
