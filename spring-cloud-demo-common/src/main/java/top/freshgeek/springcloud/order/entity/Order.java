package top.freshgeek.springcloud.order.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author chen.chao
 */
@Data
@Entity
@Table(name = "t_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private Long productId;

	private Integer count;

	private BigDecimal money;

	/***
	 *   订单状态：0：创建中；1：已完结
	 **/
	private Integer status;

}