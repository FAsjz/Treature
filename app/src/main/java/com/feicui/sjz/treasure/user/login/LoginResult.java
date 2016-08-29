package com.feicui.sjz.treasure.user.login;

/**
 * Created by Administrator on 16-7-14.
 */
public class LoginResult {
        //            "errcode": 1, //状态值
    //            "errmsg": "登录成功！",//返回信息
    //            "headpic": "add.jpg",          //头像地址
    //            "tokenid": 171//用户令牌
    private int errcode;
    private String errmsg;
    private String headpic;
    private int tokenid;

    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getHeadpic() {
        return headpic;
    }

    public int getTokenid() {
        return tokenid;
    }
}
