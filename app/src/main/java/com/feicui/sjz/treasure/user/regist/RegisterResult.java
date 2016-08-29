package com.feicui.sjz.treasure.user.regist;

/**
 * Created by Administrator on 16-7-15.
 */
public class RegisterResult {
    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public int getTokenid() {
        return tokenid;
    }

    //    "errcode": 1, //状态值
//            "errmsg": "登录成功！",//返回信息
//            "tokenid": 171//用户令牌
    private int errcode;
    private String errmsg;
    private int tokenid;

    public RegisterResult(int errcode, String errmsg, int tokenid) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.tokenid = tokenid;
    }
}
