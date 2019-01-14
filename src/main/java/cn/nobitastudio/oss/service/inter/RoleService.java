package cn.nobitastudio.oss.service.inter;

import cn.nobitastudio.common.util.Pager;
import cn.nobitastudio.oss.entity.Role;
import cn.nobitastudio.oss.entity.UserRole;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2019/01/09 11:00
 * @description
 */
public interface RoleService {

    /**
     * 查询指定id角色信息
     *
     * @param id 角色id
     * @return
     */
    Role getById(Integer id);

    /**
     * 查询所有Role,默认已经分页
     *
     * @return
     */
    PageImpl<Role> getAll(Role role, Pager pager);

    /**
     * 删除指定Role
     *
     * @param id
     * @return
     */
    @Transactional
    String deleteById(Integer id);

    /**
     * 新增或者修改指定Role
     *
     * @param role
     * @return
     */
    @Transactional
    Role save(Role role);

    /**
     * 给指定用户给予相应的角色
     * @param userRole
     * @return
     */
    @Transactional
    UserRole bindRole(UserRole userRole);

    /**
     * 给指定用户给予相应的角色
     * @param userRole
     * @return
     */
    @Transactional
    String unbindRole(UserRole userRole);
}
