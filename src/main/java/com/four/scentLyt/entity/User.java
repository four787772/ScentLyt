package com.four.scentLyt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor

public class User implements Serializable {
    private static final long serialVersionUID = 1l;
    @Column(name = "user_id")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String userId;

    @Column(name = "userName", length = 50, nullable = false, unique = true)
    private String userName;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column (name = "firt_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Formula("concat(firstName, ' ', lastName)")
    private String fullName;

    @Column(name = "role", nullable= false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "phone_number", length = 50, nullable= false, unique = true)
    private String phoneNumber;

    @Column(name = "address", length = 250, nullable=false)
    private String address;

    @Column(name = "img")
    private String img;

    @Column(name = "wallet")
    private String wallet;
    
    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updateAt;
    
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cart> carts;
    
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Order> oders;

    public enum Role{
        ADMIN, GUESS, MEMBER;
    }
}
