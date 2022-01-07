package com.metzm.cartprojectmargie2.models.data;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="pages")
public class Page {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(min=2, message = "size of title too short")
    private String title;
    private String slug;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSorting() {
        return sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }
    @Size(min=5, message = "size of content too short")
    private String content;
    private int sorting;






}
