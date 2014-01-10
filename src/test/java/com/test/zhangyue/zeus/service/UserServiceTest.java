package com.test.zhangyue.zeus.service;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zhangyue.zeus.entity.UserEntity;
import com.zhangyue.zeus.exception.UserServiceException;
import com.zhangyue.zeus.service.IUserService;

/**
 * user服务测试类
 * 
 * @date 2013-12-27
 * @author rongneng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:service-context.xml")
public class UserServiceTest {
    
    /*@Autowired
    private IUserService  userService;
    
   
    @Test
    public void findUserListTest() throws UserServiceException{
        List<UserEntity>  list = userService.findUserList();
        Assert.assertEquals(5,list.size());
    }
    
    @Test
    public void addUserTest() {
        UserEntity  vo =  new UserEntity("weirongneng", "123456", 2);
        userService.setUserEntity(vo);
        try {
           int m =  userService.addUser();
           System.out.println(vo.getId());
           Assert.assertEquals(1, m);
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }
   @Test
    public void queryUserByUserNameAndPasswdTest() {
        UserEntity  vo = new UserEntity("weirongneng","123456", 2);
        userService.setUserEntity(vo);
        try {
            UserEntity user = userService.queryUser();
            Assert.assertEquals("weirongneng", user.getUserName());
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        
        
    }
    @Test
    public void queryUserByLevelTest() {
        UserEntity  vo = new UserEntity();
        vo.setLevel(2);
        userService.setUserEntity(vo);
        try {
            List<UserEntity> list = userService.queryUserByLevel();
            Assert.assertEquals(5, list.size());
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }*/
}
