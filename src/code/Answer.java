public class Answer {
    private int answerId; // INT PRIMARY KEY
    private int responseId; // FOREIGN KEY
    private int questionId; // FOREIGN KEY
    private Integer optionId; // FOREIGN KEY (NULLABLE)
    private String textAnswer; // TEXT (NULLABLE)

    public Answer() {}
    public Answer(int responseId, int questionId, Integer optionId, String textAnswer) {
        this.responseId = responseId;
        this.questionId = questionId;
        this.optionId = optionId;
        this.textAnswer = textAnswer;
    }

    public int getAnswerId() { return answerId; }
    public void setAnswerId(int answerId) { this.answerId = answerId; }
    public int getResponseId() { return responseId; }
    public void setResponseId(int responseId) { this.responseId = responseId; }
    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public Integer getOptionId() { return optionId; }
    public void setOptionId(Integer optionId) { this.optionId = optionId; }
    public String getTextAnswer() { return textAnswer; }
    public void setTextAnswer(String textAnswer) { this.textAnswer = textAnswer; }
}
