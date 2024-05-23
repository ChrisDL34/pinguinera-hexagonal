package com.penguin.model.bookStoreQuotes.values.BookStoreQuotes;

import com.penguin.model.generic.ValueObject;

public class Seniority implements ValueObject<SeniorityEnum> {

    private  SeniorityEnum value;

    public Seniority(SeniorityEnum value) {
        if (value!= null && validateEnum(value)) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Product type cannot be null");
        }
    }

    public Seniority() {
    }

    public static Seniority of(SeniorityEnum value) {
        return new Seniority(value);
    }

    @Override
    public SeniorityEnum value() {
        return value;
    }

    private boolean validateEnum(SeniorityEnum seniority){
        return seniority.equals(
                SeniorityEnum.LESS_THAN_A_YEAR)||
                seniority.equals(SeniorityEnum.BETWEEN_ONE_YEAR_AND_TWO_YEARS) ||
                seniority.equals(SeniorityEnum.MORE_THAN_TWO_YEARS);

    }
}
