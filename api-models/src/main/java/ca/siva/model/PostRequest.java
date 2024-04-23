package ca.siva.model;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "post_requests")
public class PostRequest {

    @Id
    @Column(length = 50)
    private String id;

    @Column(name = "created", nullable = false, updatable = false, insertable = false)
    private Timestamp created;

    @Lob
    @Column(name = "document", nullable = false)
    private String document;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
