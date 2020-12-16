package top.freshgeek.springcloud.payment.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/4/28 21:05
 * @description
 */
@Data
@Entity
@Table(name = "t_payment")
public class Payment implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serializeId;

}
