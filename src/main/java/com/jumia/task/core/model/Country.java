package com.jumia.task.core.model;

import javax.persistence.*;

@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String regex;

    public Country() { }

    public Country(String name, String code, String regex) {
        this.name = name;
        this.code = code;
        this.regex = regex;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String validRegex) {
        this.regex = validRegex;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", validRegex='" + regex + '\'' +
                '}';
    }
}
