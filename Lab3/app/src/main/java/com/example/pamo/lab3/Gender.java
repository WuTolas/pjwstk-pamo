package com.example.pamo.lab3;

import java.util.stream.Stream;

public enum Gender {
    MALE(R.id.maleRadio),
    FEMALE(R.id.femaleRadio);

    private final Integer radioId;

    Gender(Integer radioId) {
        this.radioId = radioId;
    }

    public static Gender getGenderById(Integer id) {
        return Stream.of(Gender.values()).filter(v -> v.radioId.equals(id)).findFirst().orElse(null);
    }

    public Integer getRadioId() {
        return radioId;
    }
}
