package ru.alishev.springcourse.models;

public enum Mood {
    HAPPY, SAD, ANGRY, WORRIED, CALM
    //При EnumType.ORDINAL в БД колонка будет ЦЕЛОЕ ЧИСЛО
    //Если перечисления НИКОГДА не поменяются, то храним ЦЕЛОЕ число
    //Если перечисления могут измениться - СТРОКУ EnumType.STRING
}
