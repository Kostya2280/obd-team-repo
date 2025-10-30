import java.sql.Timestamp;

public class Response {
    private int responseId; // INT PRIMARY KEY
    private int formId; // FOREIGN KEY
    private Timestamp submittedAt;

    public Response() {}
    public Response(int formId) {
        this.formId = formId;
    }

    public int getResponseId() { return responseId; }
    public void setResponseId(int responseId) { this.responseId = responseId; }
    public int getFormId() { return formId; }
    public void setFormId(int formId) { this.formId = formId; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
}
