package text;

import dao.IUserDao;
import domain.QueryVo;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MybatiesTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private SqlSessionFactory factory;

    /**
     * 初始化操作
     *
     * @throws IOException
     */
    @Before//用于在测试方法前执行
    public void init() throws IOException {
        //1、读取配置文件，商城字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2、创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(in);//读取SqlMapConfig.xml中连接数据库和mapper映射信息，用来生产能够真正操作数据库的SqlSession对象
        //3、使用工厂生产一个SqlSession对象
//        sqlSession = factory.openSession(true);//这里有参数的话，就实现了自动提交commit，后面就不用再写了
        sqlSession = factory.openSession();
        //4、使用SqlSession创建Dao接口的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    /**
     * 释放资源
     *
     * @throws IOException
     */
    @After//用于在测试方法执行之后执行
    public void destory() throws IOException {
        //提交事务
        sqlSession.commit();
        //6、释放资源
        sqlSession.close();
        in.close();
    }


    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        //5、使用代理对象执行方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试增加用户
     */
    @Test
    public void textSave() {
        User user = new User();
        user.setUsername("水熊虫");
        user.setSex("男");
        user.setAddress("成都");
        user.setBirthday(new Date());
        //5、使用代理对象执行保存方法
        System.out.println(user);//保存前
        userDao.saveUser(user);
        System.out.println(user);//保存后
    }

    /**
     * 测试更新用户
     */
    @Test
    public void textUpdate() {
        User user = new User();
        user.setId(53);
        user.setUsername("程咬金");
        user.setSex("男");
        user.setAddress("湖北");
        user.setBirthday(new Date());
        //5、使用代理对象执行保存方法
        userDao.updateUser(user);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDelete() {
        //5、使用代理对象执行删除方法
        userDao.deleteUser(56);
    }

    /**
     * 测试查询单个用户
     */
    @Test
    public void testFindOne() {
        //5、使用代理对象执行查询一个方法
        User user = userDao.findById(48);
        System.out.println(user);
    }

    /**
     * 测试模糊查询操作
     */
    @Test
    public void testFindByName() {
        List<User> users= userDao.findByName("%妲%");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试查询总记录条数
     */
    @Test
    public void testFindTotal() {
        int total = userDao.findTotal();

        System.out.println(total);
    }


    /**
     * 测试使用queryVo作为查询条件
     */
    @Test
    public void testFindByVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%妲%");
        vo.setUser(user);
        List<User> users= userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }


    /**
     * 根据传入的参数条件查询
     */
    @Test
    public void testFindByCondition() {
        User u = new User();
        u.setUsername("妲妹妹");
        u.setSex("男");
        //5、使用代理对象执行方法
        List<User> users = userDao.findUserByCondition(u);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 测试根据QueryVo中提供的id集合，查询用户信息
     * SQL：select * from user where id in(41,43,45,47,49);
     */
    @Test
    public void testFindByInIds() {
        QueryVo vo = new QueryVo();
        List<Integer> list = new ArrayList<Integer>();
        list.add(41);
        list.add(43);
        list.add(45);
        list.add(47);
        list.add(49);
        vo.setIds(list);
        List<User> users= userDao.findUserInIds(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }


    /**
     * 测试一级缓存
     */
    @Test
    public void testFirstLeverCache() {
        //5、使用代理对象执行查询一个方法
        User user1 = userDao.findById(48);
       /* sqlSession.close();
        //再次获取sqlSession对象
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);*/

       sqlSession.clearCache();//此方法也可以清空缓存
        User user2 = userDao.findById(48);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user1==user2);
    }





















}
