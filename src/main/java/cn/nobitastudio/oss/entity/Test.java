package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/10 22:46
 * @description
 */
@Data
@Entity
@Table(name = "test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @ApiModelProperty("Id")
    @Column(name = "id")
    @Id
    @Equal
    private String id;
}
