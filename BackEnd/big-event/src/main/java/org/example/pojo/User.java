package org.example.pojo;



import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore //转成Json时忽略
    private String password;//密码
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称
    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
