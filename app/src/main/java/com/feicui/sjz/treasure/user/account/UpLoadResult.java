package com.feicui.sjz.treasure.user.account;

/**
 * Created by Administrator on 16-7-18.
 */
public class UpLoadResult {
//    errcode: '文件系统上传成功！',//返回信息
//    urlcount: 1,                                   //返回值
//    imgUrl:
//            '/UpLoad/HeadPic/f683f88dc9d14b648ad5fcba6c6bc840_0.png',
//    smallImgUrl:
//            '/UpLoad/HeadPic/f683f88dc9d14b648ad5fcba6c6bc840_0_1.png'//头像地址
    private String errcode;
    private int urlcount;
    private String imgUrl;
    private String smallImgUrl;

    public String getErrcode() {
        return errcode;
    }

    public int getUrlcount() {
        return urlcount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    @Override
    public String toString() {
        return "UpLoadResult{" +
                "errcode='" + errcode + '\'' +
                ", urlcount=" + urlcount +
                ", imgUrl='" + imgUrl + '\'' +
                ", smallImgUrl='" + smallImgUrl + '\'' +
                '}';
    }
}
