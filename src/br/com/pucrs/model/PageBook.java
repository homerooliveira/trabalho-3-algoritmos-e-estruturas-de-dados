package br.com.pucrs.model;


import java.util.Objects;

public class PageBook {
    private String content;
    private String type;

    public PageBook(String content, String type) {
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
        PageBook that = (PageBook) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, type);
    }
}
