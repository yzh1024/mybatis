package dao;

import domain.Role;

import java.util.List;

public interface IRoleDao {
    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();
}
