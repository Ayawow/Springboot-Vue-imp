import instance from "../utils/request";

//注册
export const registerService = (registerData) => {
    var params = new URLSearchParams();
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    return instance.post('/user/register', params);
}

//登录
export const loginService = (loginData) => {
    var params = new URLSearchParams();
    for (let key in loginData) {
        params.append(key, loginData[key]);
    }
    return instance.post('/user/login', params);
}

//获取个人信息
export const userInfoGetService = ()=>{
    return instance.get('/user/userInfo');
}

//修改个人信息
export const userInfoUpdateService = (userInfo) => {
    return instance.put('/user/update', userInfo);
}


//修改头像
export const userAvatarUpdateService=(avatarUrl)=>{
    let params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl)
    return instance.patch('/user/updateAvatar',params)
}

//修改密码

export const userUpdatePasswordService = ({ old_pwd, new_pwd, re_pwd }) => {
  return instance.patch('/user/updatePwd', {
    old_pwd,
    new_pwd,
    re_pwd
  })
}