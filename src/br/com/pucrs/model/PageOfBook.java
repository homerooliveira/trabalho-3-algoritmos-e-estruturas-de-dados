package br.com.pucrs.model;


public class PageOfBook  {
    private String content;
    private String type;

    public PageOfBook(String content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

}
