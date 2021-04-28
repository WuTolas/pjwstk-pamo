package com.example.pamo.lab4.quiz;

import java.io.Serializable;

public class Answer implements Serializable {

    private static final long serialVersionUID = 8458442933258502701L;

    private String name;
    private boolean correct;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
