package com.four.scentLyt.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.four.scentLyt.entity.User.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor

public class Order {
	@Column(name = "order_id")
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String orderId;
	
	@Column(name = "order_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date orderTime;
	
	@Column(name = "total_price", nullable = false)
	private double totalPrice;
	
	@Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
	private State state;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<OrderItem> orderItems;
	
	public enum State{
		PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED;
	}
	
}
