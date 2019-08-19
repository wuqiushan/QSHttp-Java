package QSHttp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主要是把请求，使用多线程
 */
public class QSHttpManage {

    // 并发线程数量
    private final static int THREAD_POOL_COUNT = 4;

    // 线程池
    public ExecutorService threadPool;

    // 单例
    private volatile static QSHttpManage share = null;

    private QSHttpManage() {
    }

    public static QSHttpManage shareInstance() {
        if (share == null) {
            synchronized (QSHttpManage.class) {
                share = new QSHttpManage();
                share.threadPool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
            }
        }
        return share;
    }
}