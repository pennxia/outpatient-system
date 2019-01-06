package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/03 14:36
 * @description 用户收藏医生. 收藏关系
 */
@Data
@Entity
@Table(name = "collect_doctor")
@Getter
@Setter
public class CollectDoctor {

    @ApiModelProperty("收藏物理id,无实力逻辑含义")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("用户id")
    @Column(name = "user_id")
    @Equal
    private Integer userId;

    @ApiModelProperty("收藏的医生的id")
    @Column(name = "doctor_id")
    @Equal
    private Integer doctorId;

    @ApiModelProperty("收藏关系创建时间")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime createTime;
}
