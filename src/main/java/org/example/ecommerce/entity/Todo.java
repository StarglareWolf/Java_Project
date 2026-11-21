package org.example.ecommerce.entity;

public class Todo {
    private Integer id;
    private Integer user_id;
    private String title;
    private String content;
    private Boolean is_done;

    public Integer getId() { return id;}
    public void setId(Integer id) { this.id = id;}
    public Integer getUserId() { return user_id;}
    public void setUserId(Integer userId) { this.user_id = userId;}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Boolean getIs_done() { return is_done; }
    public void setIs_done(Boolean is_done) { this.is_done = is_done; }
}
