
![image](https://github.com/SimonGitHub123/QSHttp-OC/blob/master/QSHttp-OC.png)

[![Build Status](https://travis-ci.org/shuzheng/zheng.svg?branch=master)](https://github.com/SimonGitHub123/QSHttp-OC)  [![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) [![language](https://img.shields.io/badge/language-java-green.svg)](1) [![](https://www.jitpack.io/v/wuqiushan/QSHttp-Java.svg)](https://www.jitpack.io/#wuqiushan/QSHttp-Java)


### 概述
为简化后期的手机客户端与服务器调试，特此对各个环境进行了封装，本仓库为Java(安卓通用)版本，iOS仓库传送门、服务器仓库传送门。


### 特点
* 采用多线程异步请求机制
* 支持请求的URL带有中文


### 进度
* [x] 完成基本的GET、POST、上传、下载、等操作
* [x] 完成请求后数据回传，通labda方法
* [x] 完成上传、下载的实时进度
* [ ] 支持无网通知


### 安装方法
##### gradle安装
* Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```ruby
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```
* Step 2. Add the dependency
```ruby
dependencies {
        implementation 'com.github.wuqiushan:QSHttp-Java:1.0.5'
}
```
##### maven安装
* Step 1. Add the JitPack repository to your build file
```ruby
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://www.jitpack.io</url>
    </repository>
</repositories>
```
* Step 2. Add the dependency
```ruby
<dependency>
    <groupId>com.github.wuqiushan</groupId>
    <artifactId>QSHttp-Java</artifactId>
    <version>1.0.5</version>
</dependency>
```


### 使用方法

GET方法示例：
```Java
public void http () {
    QSHttp qsHttp = new QSHttp();
    qsHttp.GET("http://localhost:8080/javaOne_war_exploded/天气jahttp", null, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

POST方法示例：
```Java
public void http () {
    QSHttp qsHttp = new QSHttp();
    qsHttp.POST("http://localhost:8080/javaOne_war_exploded/天气jahttp", null, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

download下载文件示例：
```Java
public void http () {
    QSHttp qsHttp = new QSHttp();
    qsHttp.download("http://localhost:8080/javaOne_war_exploded/QSDownloadServlet", null, "/Users/yyd-wlf/Desktop", (progress)-> {

        int progressInt = (int) (progress * 100);
        System.out.println("下载进度：" + progressInt + "%");

    }, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

upload上传文件示例：
```Java
public void http () {
    QSHttp qsHttp = new QSHttp();
    qsHttp.upload("http://localhost:8080/javaOne_war_exploded/QSUploadServlet", "/Users/yyd-wlf/Desktop/123.zip", (progress)-> {

        int progressInt = (int) (progress * 100);
        System.out.println("上传进度：" + progressInt + "%");

    }, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

### 许可证
所有源代码均根据MIT许可证进行许可。
