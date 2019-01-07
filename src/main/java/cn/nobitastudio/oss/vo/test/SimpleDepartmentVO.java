package cn.nobitastudio.oss.vo.test;

import cn.nobitastudio.oss.vo.enumeration.DepartmentArea;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    private DepartmentArea area;
    private String introduction;
    private String name;

    public SimpleDepartmentVO(Integer id, String area, String introduction, String name) {
        this.id = id;
        if (area.equals("A")) {
            this.area = DepartmentArea.A;
        } else if (area.equals("B")) {
            this.area = DepartmentArea.B;
        } else if (area.equals("C")) {
            this.area = DepartmentArea.C;
        } else if (area.equals("D")) {
            this.area = DepartmentArea.D;
        }
        this.introduction = introduction;
        this.name = name;
    }
}
