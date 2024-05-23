package com.penguin;


public interface JSONMapper {
    String writeToJson(Object obj);
    Object readFromJson(String json, Class<?> clazz);
}
