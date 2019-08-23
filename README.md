
![image](https://github.com/wuqiushan/QSHttp-Java/blob/master/QSHttp-Java.png)

[![Build Status](https://travis-ci.org/shuzheng/zheng.svg?branch=master)](https://github.com/SimonGitHub123/QSHttp-OC)  [![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE) [![language](https://img.shields.io/badge/language-java-green.svg)](1) [![](https://www.jitpack.io/v/wuqiushan/QSHttp-Java.svg)](https://www.jitpack.io/#wuqiushan/QSHttp-Java)


### 概述
为简化后期的手机客户端与服务器调试，特此对各个环境进行了封装，本仓库为Java(安卓通用)版本，iOS版本、服务器版本。
* [x] 支持iOS，[传送门](https://github.com/wuqiushan/QSHttp-OC)
* [x] 支持android(java)，[传送门](https://github.com/wuqiushan/QSHttp-Java)
* [x] 支持服务器端，[传送门](https://github.com/wuqiushan/QSHttp-Server)

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
        implementation 'com.github.wuqiushan:QSHttp-Java:1.1.1'
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
    <version>1.1.1</version>
</dependency>
```


### 使用方法

GET方法示例：
```Java
public static void getTest() {

    QSHttp qsHttp = new QSHttp();
    // 测试GET中含有中文
    qsHttp.GET("http://www.eechot.ga/server/QSHttp/GET/天气", null, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

POST方法示例：
```Java
public static void postTest() {

    QSHttp qsHttp = new QSHttp();
    qsHttp.POST("http://www.eechot.ga/server/QSHttp/POST", null, (okMsg)-> {

        System.out.println("成功：" + okMsg);

    }, (errorMsg)-> {

        System.out.println("失败：" + errorMsg);

    });
}
```

download下载文件示例：
```Java
public static void downloadTest() {

    QSHttp qsHttp = new QSHttp();
    // 下载文件，并存入桌面(这里你可以更改成你自己想的路径)
    qsHttp.download("http://www.eechot.ga/server/QSHttp/Download", null, "/Users/yyd-wlf/Desktop/QSHttpFile", (progress)-> {

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
public static void uploadTest() {

    QSHttp qsHttp = new QSHttp();
    // 上传文件 (如果需要本demo测试，请上传小于2M的zip文件，因为服务器能力有限)
    qsHttp.upload("http://www.eechot.ga/server/QSHttp/Upload", "/Users/yyd-wlf/Desktop/QSHttpFile/nginx-1.16.0.tar.gz", (progress)-> {

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
