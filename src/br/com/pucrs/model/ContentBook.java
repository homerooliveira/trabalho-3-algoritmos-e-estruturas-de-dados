package br.com.pucrs.model;


import java.util.Objects;

public class ContentBook {
    private String content;
    private String type;

    public ContentBook(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentBook that = (ContentBook) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, type);
    }

    @Override
    public String toString() {
        return "ContentBook{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
