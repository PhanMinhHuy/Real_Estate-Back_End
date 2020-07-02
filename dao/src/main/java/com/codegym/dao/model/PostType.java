package com.codegym.dao.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "post_type")
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String name;

    @Column(columnDefinition = "BIT(1) default 1")
    private boolean status;

    @OneToMany(mappedBy = "postType")
    private Set<Post> posts;

    public PostType() {
    }

    public PostType(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
