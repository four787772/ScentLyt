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
@Table(name = "cart")
@Data
@NoArgsConstructor

public class Cart {
	@Column(name = "cart_id")
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String cartId;
	
	@Column(name = "total_price", nullable = false)
	private double totalPrice;
	
    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updateAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private List<CartItem> cartItems;
	
}
