package QSHttp;

import static org.junit.Assert.assertTrue;

import QSHttp.QSHttp;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class APPTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testPOST() {

        QSHttp qsHttp = new QSHttp();
        qsHttp.POST("http://localhost:8080/javaOne_war_exploded/天气jahttp", null, (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
    }

    @Test
    public void postTest() {

        QSHttp qsHttp = new QSHttp();
        qsHttp.POST("http://120.77.224.247:18081/user/blackList/add", "{\"uid\": \"108333\"}", (okMsg)-> {

            System.out.println("成功：" + okMsg);

        }, (errorMsg)-> {

            System.out.println("失败：" + errorMsg);

        });
        System.out.println("down");
    }
}
