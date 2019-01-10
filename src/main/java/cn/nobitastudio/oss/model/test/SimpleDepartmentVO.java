package cn.nobitastudio.oss.model.test;

import cn.nobitastudio.oss.model.enumeration.Area;
import lombok.*;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/07 14:45
 * @description
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDepartmentVO {

    private Integer id;
    private Area area;
    private String introduction;
    private String name;

    public SimpleDepartmentVO(Integer id, String area, String introduction, String name) {
        this.id = id;
        if (area.equals("A")) {
            this.area = Area.A;
        } else if (area.equals("B")) {
            this.area = Area.B;
        } else if (area.equals("C")) {
            this.area = Area.C;
        } else if (area.equals("D")) {
            this.area = Area.D;
        }
        this.introduction = introduction;
        this.name = name;
    }
}
