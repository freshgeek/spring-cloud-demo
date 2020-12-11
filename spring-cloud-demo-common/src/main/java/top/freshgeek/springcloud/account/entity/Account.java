package top.freshgeek.springcloud.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_account")
public class Account {
	/**
	 * 用户id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 总额度
	 */
	private BigDecimal total;

	/**
	 * 已用额度
	 */
	private BigDecimal used;

	/**
	 * 剩余额度
	 */
	private BigDecimal residue;
}
