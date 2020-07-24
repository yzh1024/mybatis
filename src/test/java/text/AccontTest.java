package text;

import dao.IAccountDao;
import dao.IUserDao;
import domain.Accont;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AccontTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

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
        SqlSessionFactory factory = builder.build(in);//读取SqlMapConfig.xml中连接数据库和mapper映射信息，用来生产能够真正操作数据库的SqlSession对象
        //3、使用工厂生产一个SqlSession对象
//        sqlSession = factory.openSession(true);//这里有参数的话，就实现了自动提交commit，后面就不用再写了
        sqlSession = factory.openSession();
        //4、使用SqlSession创建Dao接口的代理对象
        accountDao = sqlSession.getMapper(IAccountDao.class);
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
    public void testFindAll(){
        List<Accont> acconts = accountDao.findAll();
        for (Accont accont : acconts) {
            System.out.println(accont);
        }
    }
}
