package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 15:31
 * @description 药品实例
 */
@Data
@Entity
@Table(name = "drug")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drug implements Serializable {

    private static final long serialVersionUID = -235446322829903865L;

    @ApiModelProperty("药品id")
    @Column(name = "id")
    @Id
    @Equal
    private Integer id;

    @ApiModelProperty("药品名称")
    @Column(name = "name")
    @Like
    private String name;

    @ApiModelProperty("药品单价")
    @Column(name = "price")
    @Equal
    private Double price;

    @ApiModelProperty("药品进价")
    @Column(name = "purchase_price")
    @Equal
    private Double purchasePrice;

    @ApiModelProperty("药品制造商")
    @Column(name = "manufacturer")
    @Like
    private String manufacturer;

    @ApiModelProperty("药品生产日期")
    @Column(name = "produce_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime produceTime;

    @ApiModelProperty("药品有效日期（以天为单位）")
    @Column(name = "effective_time")
    @Equal
    private Integer effectiveTime;

    @ApiModelProperty("药品编码")
    @Column(name = "code")
    @Equal
    private String code;
}
