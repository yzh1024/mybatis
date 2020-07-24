package dao;

import domain.Accont;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户
     * @return
     */
    List<Accont> findAll();

//    List<> findAllAccount();
}
