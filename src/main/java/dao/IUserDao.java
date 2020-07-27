package dao;

import domain.QueryVo;
import domain.User;

import java.util.List;

//用户的持久层接口
public interface IUserDao {
    /**
     * 查询所有用户，同时获取到用户下所有的账户信息
     * @return
     */
    List<User> findAll();

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 修改用户信息（更新）
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     * @param userId
     */
    void deleteUser(Integer userId);

    /**
     * 根据id查询单个用户
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 根据名称模糊查询用户信息
     * @param username
     * @return
     */
    List<User> findByName(String username);

    /**
     * 查询总用户数（聚合查询）
     * @return
     */
    int findTotal();

    /**
     * 根据QueryVo查询中的条件查询用户
     * Vo:value Object
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入的参数条件查询
     * * @param vo
     * @return user查询的条件：有可能有用户名，有可能有性别，也有可能有地址，还有可能是都有
     */
    List<User> findUserByCondition(User user);

    /**
     * 根据QueryVo中提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);
}
