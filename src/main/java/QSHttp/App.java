package QSHttp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        QSHttp qsHttp = new QSHttp();

        // GET
        qsHttp.GET("http://localhost:8080/javaOne_war_exploded/天气jahttp", null, (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });

//        qsHttp.POST("http://localhost:8080/javaOne_war_exploded/天气jahttp", null, (okMsg)-> {
//
//            System.out.println("成功：" + okMsg);
//
//        }, (errorMsg)-> {
//
//            System.out.println("失败：" + errorMsg);
//
//        });


        // 下载文件
//        qsHttp.download("http://localhost:8080/javaOne_war_exploded/QSDownloadServlet", null, "/Users/yyd-wlf/Desktop", (progress)-> {
//
//            int progressInt = (int) (progress * 100);
//            System.out.println("下载进度：" + progressInt + "%");
//
//        }, (okMsg)-> {
//
//            System.out.println("成功：" + okMsg);
//
//        }, (errorMsg)-> {
//
//            System.out.println("失败：" + errorMsg);
//
//        });


        // 上传文件
//        qsHttp.upload("http://localhost:8080/javaOne_war_exploded/QSUploadServlet", "/Users/yyd-wlf/Desktop/123.zip", (progress)-> {
//
//            int progressInt = (int) (progress * 100);
//            System.out.println("上传进度：" + progressInt + "%");
//
//        }, (okMsg)-> {
//
//            System.out.println("成功：" + okMsg);
//
//        }, (errorMsg)-> {
//
//            System.out.println("失败：" + errorMsg);
//
//        });
    }

}

/*
        QSHttp qsHttp = new QSHttp();
        // 匿名函数
        qsHttp.httpRequest("http://localhost:8080/javaOne_war_exploded/jahttp", "GET", null, new QSRspSuccess() {
            @Override
            public void success(String okMsg) {
                System.out.println(okMsg);
            }
        }, new QSRspFailure() {
            @Override
            public void failure(String errorMsg) {
                System.out.println(errorMsg);
            }
        }); */
