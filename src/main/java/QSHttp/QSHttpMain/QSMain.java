package QSHttp.QSHttpMain;

import QSHttp.QSHttp;

public class QSMain {

    public static void main( String[] args )
    {
        getTest();
        //postTest();
        //downloadTest();
        //uploadTest();
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
