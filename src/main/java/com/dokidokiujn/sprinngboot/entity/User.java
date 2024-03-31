package com.dokidokiujn.sprinngboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@TableName("userinfo")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String mail;
    private String major;

    public User(String username, String password, String mail, String major) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.major = major;
    }
    public User() {
    }
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + mail + '\'' +
                '}';
    }
}
