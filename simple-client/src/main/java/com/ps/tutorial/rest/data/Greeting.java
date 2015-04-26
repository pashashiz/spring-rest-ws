package com.ps.tutorial.rest.data;

public class Greeting {

    private String text;
    private String name;

    public Greeting() {}

    public Greeting(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "text='" + text + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
