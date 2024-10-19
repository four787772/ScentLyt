package com.four.scentLyt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor

public class Categories {
    @Column(name = "category_id")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String categoryId;

    @Column(name = "name",length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;
    
    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updateAt;

    @OneToMany(mappedBy = "category")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Product> products;

   


}
