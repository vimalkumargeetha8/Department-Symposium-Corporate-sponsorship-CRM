package com.aiht.symposium.crm.dto;

public class DeliverablesUpdateRequestDto {

    private String deliverablesNotes;

    public DeliverablesUpdateRequestDto() {}

    public DeliverablesUpdateRequestDto(String deliverablesNotes) {
        this.deliverablesNotes = deliverablesNotes;
    }

    public String getDeliverablesNotes() { return deliverablesNotes; }
    public void setDeliverablesNotes(String deliverablesNotes) { this.deliverablesNotes = deliverablesNotes; }
}
