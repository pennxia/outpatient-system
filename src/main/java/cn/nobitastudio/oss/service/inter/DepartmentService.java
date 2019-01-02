package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.ServiceResult;
import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/02 10:35
 * @description
 */
public interface DepartmentService {

    /**
     * 查询指定id科室信息
     *
     * @param id 指定科室id
     * @return
     */
    Department getById(Integer id);

    /**
     * 查询所有
     *
     * @param pager 分页参数
     * @return
     */
    PageImpl<Department> getAll(Department department, Pager pager);

    /**
     * 删除指定id科室
     *
     * @param id 指定科室id
     * @return
     */
    String delete(Integer id);

    /**
     * 新增或更新科室信息
     *
     * @param department 待新增或更新的科室信息
     * @return
     */
    Department save(Department department);
}
