package top.freshgeek.springcloud.storage.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_storage")
public class Storage {
    /**
     * 产品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 总库存
     */
    private Integer total;

}