package QSHttp.QSHttpMain;

import QSHttp.QSHttp;

import java.util.HashMap;

public class QSMain {

    public static void main( String[] args )
    {
//        getTest();
        //postTest();
        //downloadTest();
        //uploadTest();
//        postTest1();
        postTest2();
    }


    public static void getTest() {

        QSHttp qsHttp = new QSHttp();
        // 测试GET中含有中文
        qsHttp.GET("http://www.eechot.ga/server/QSHttp/GET/天气", null, (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
    }

    public static void postTest1() {

        QSHttp qsHttp = new QSHttp();
        qsHttp.POST("http://120.77.224.247:18081/user/blackList/add", "{\"uid\": \"101385\"}", (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
        System.out.println("down");
    }

    public static void postTest2() {

        QSHttp qsHttp = new QSHttp();
        HashMap<String, String> map = new HashMap<>();
        map.put("ke", "12");
        qsHttp.POST("http://172.16.1.194:8080/robot-mp-admin/api/web/add/device/fault/info", map, (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
        System.out.println("down");
    }

    public static void postTest() {

        QSHttp qsHttp = new QSHttp();
        qsHttp.POST("http://www.eechot.ga/server/QSHttp/POST", null, (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
    }

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
}
