package org.acme.DTO.FaqDTOs;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class FaqUpdateRequestDTO {
    @Schema(example = "Your question here")
    private String question;

    @Schema(example = "Your answer here")
    private String answer;

    @Schema(example = "drafted")
    private String status;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
