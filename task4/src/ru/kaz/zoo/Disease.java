package ru.kaz.zoo;

import java.time.LocalDate;


public class Disease {
    private String diseaseTitle;

    private LocalDate diseaseStart;
    private LocalDate diseaseEnd;

    public String getDiseaseTitle() {
        return diseaseTitle;
    }

    public LocalDate getDiseaseStart() {
        return diseaseStart;
    }

    public LocalDate getDiseaseEnd() {
        return diseaseEnd;
    }

    public void setDiseaseEnd(LocalDate diseaseEnd) {
        this.diseaseEnd = diseaseEnd;
    }

    public Disease(String diseaseTitle, LocalDate date) {
        this.diseaseTitle = diseaseTitle;
        this.diseaseStart = date;
    }
}
