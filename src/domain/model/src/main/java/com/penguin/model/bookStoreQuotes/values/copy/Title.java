package com.penguin.model.bookStoreQuotes.values.copy;


import com.penguin.model.generic.ValueObject;

public class Title implements ValueObject<String>{

    private  String title;

//    public Title(String title) {
//        if(!title.isEmpty() && title.length() < 20){
//            this.title = title;
//        }else throw new IllegalArgumentException("title must be between 1 and 10");
//    }
    public Title(String title) {
        this.title = title;
    }

    public Title() {
    }

    @Override
    public String value() {
        return this.title;
    }
}
