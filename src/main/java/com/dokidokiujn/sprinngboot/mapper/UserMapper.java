package com.dokidokiujn.sprinngboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dokidokiujn.sprinngboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //查询所有用户
    @Select("select * from student")
    List<User> queryUserList();

    //根据username查询用户
    @Select("SELECT EXISTS(SELECT 1 FROM student WHERE username = #{username})")
    boolean userExists(String username);

    //根据username查询用户
    @Select("select * from student where username = #{username}")
    User queryUserByName(String username);

    //根据id查询用户
    @Select("select * from student where id = #{id}")
    User queryUserById(int id);

    //添加一个用户 返回值是插入成功了几条用户
    @Insert("insert into student (username,password,mail,major) values (#{username},#{password},#{mail},#{major})")
    int addUser(User user);

    //修改一个用户
    @Update("update student set username=#{username},password=#{password},mail=#{mail},major=#{major} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from student where id = #{id}")
        //删除一个用户
    int deleteUser(int id);
}
