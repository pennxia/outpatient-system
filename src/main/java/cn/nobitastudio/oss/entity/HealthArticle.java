package cn.nobitastudio.oss.entity;

import cn.nobitastudio.common.criteria.Equal;
import cn.nobitastudio.common.criteria.Like;
import cn.nobitastudio.oss.model.enumeration.HealthArticleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 17:00
 * @description 健康资讯实例
 */
@Data
@Entity
@Table(name = "health_article")
@Getter
@Setter
public class HealthArticle implements Serializable {

    private static final long serialVersionUID = -7258563717193340205L;

    @ApiModelProperty("健康咨询id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Equal
    private Integer id;

    @ApiModelProperty("健康咨询图标id")
    @Column(name = "icon_id")
    @Equal
    private String iconId;

    @ApiModelProperty("健康咨询标题")
    @Column(name = "title")
    @Like
    private String title;

    @ApiModelProperty("健康咨询类别")
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @Equal
    private HealthArticleType type;

    @ApiModelProperty("发布日期")
    @Column(name = "publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Equal
    private LocalDateTime publishTime;

    @ApiModelProperty("发布日期")
    @Column(name = "publish_man")
    @Like
    private String publishMan;

    @ApiModelProperty("标签（传染科...）")
    @Column(name = "label")
    @Like
    private String label;

    @ApiModelProperty("详细的url")
    @Column(name = "url")
    @Equal
    private String url;


}
