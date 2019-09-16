package QSHttp;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参考：https://www.cnblogs.com/caoshenglu/p/8476593.html
 * 多文件参考：https://blog.csdn.net/u010957645/article/details/86062741
 */

public class QSHttp {

    private final static String get = "get";
    private final static String post = "post";
    private final static String upload = "upload";
    private final static String download = "download";


    /***
     * GET 异步请求
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void GET(String urlStr, Object param, QSRspSuccess success, QSRspFailure failure) {
        QSHttpManage.shareInstance().threadPool.submit(()-> {
            this.GETSync(urlStr, param, success, failure);
        });
    }

    /***
     * GET 同步请求
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void GETSync(String urlStr, Object param, QSRspSuccess success, QSRspFailure failure) {
        try {
            this.requestBase(get, urlStr, "GET", param, null, null, null, success, failure);
        } catch (Exception e) {
            failure.failure(e.getMessage());
        }
    }


    /***
     * POST 异步请求
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void POST(String urlStr, Object param, QSRspSuccess success, QSRspFailure failure) {

        QSHttpManage.shareInstance().threadPool.submit(()-> {
            this.POSTSync(urlStr, param, success, failure);
        });
    }

    /***
     * POST 同步请求
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void POSTSync(String urlStr, Object param, QSRspSuccess success, QSRspFailure failure) {
        try {
            this.requestBase(post, urlStr, "POST", param, null, null, null, success, failure);
        } catch (Exception e) {
            failure.failure(e.getMessage());
        }
    }


    /**
     * 下载文件 异步下载
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param storagePath 下载需要保存的位置
     * @param progress 下载时的进度(0.0 - 1.0)
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void download(String urlStr, Object param, String storagePath, QSRspProgress progress,
                         QSRspSuccess success, QSRspFailure failure) {

        QSHttpManage.shareInstance().threadPool.submit(()-> {
            this.downloadSync(urlStr, param, storagePath, progress, success, failure);
        });
    }

    /**
     * 下载文件 同步下载
     * @param urlStr  请求Url
     * @param param   请求参数
     * @param storagePath 下载需要保存的位置
     * @param progress 下载时的进度(0.0 - 1.0)
     * @param success 请求成功响应
     * @param failure 请求失败响应
     */
    public void downloadSync(String urlStr, Object param, String storagePath, QSRspProgress progress,
                         QSRspSuccess success, QSRspFailure failure) {

        try {
            this.requestBase(download, urlStr, "GET", null, null, storagePath, progress, success, failure);
        } catch (Exception e) {
            failure.failure(e.getMessage());
        }
    }


    /**
     * 上传文件 异步上传
     * @param urlStr 请求的Url
     * @param filePath 文件路径
     * @param progress 文件上传进度回调
     * @param success 请求成功响应
     * @param failure 请求失败响应
     * @throws Exception  异常处理
     */
    public void upload(String urlStr, String filePath, QSRspProgress progress,
                       QSRspSuccess success, QSRspFailure failure) {

        QSHttpManage.shareInstance().threadPool.submit(()-> {
            this.uploadSync(urlStr, filePath, progress, success, failure);
        });
    }

    /**
     * 上传文件 同步上传
     * @param urlStr 请求的Url
     * @param filePath 文件路径
     * @param progress 文件上传进度回调
     * @param success 请求成功响应
     * @param failure 请求失败响应
     * @throws Exception  异常处理
     */
    public void uploadSync(String urlStr, String filePath, QSRspProgress progress,
                       QSRspSuccess success, QSRspFailure failure) {

        try {
            this.requestBase(upload, urlStr, "POST", null, null, filePath, progress, success, failure);
        } catch (Exception e) {
            failure.failure(e.getMessage());
        }
    }


    /**
     * 请求基类
     * @param reqType 请求类型，upload/download 因为有些参数是共用所以需要区分
     * @param urlStr 请求的Url
     * @param method 请求的方式
     * @param param  请求的参数 (上传 不使用 因为两冲突了)
     * @param fileData 文件数据 (仅上传、下载使用 暂未实现)
     * @param filePath 文件路径（优先级低）(仅上传、下载使用)
     * @param rspProgress 文件进度回调 (仅上传、下载使用)
     * @param rspSuccess 请求成功响应
     * @param rspFailure 请求失败响应
     */
    private void requestBase(String reqType, String urlStr, String method, Object param, Object fileData, String filePath,
                            QSRspProgress rspProgress, QSRspSuccess rspSuccess, QSRspFailure rspFailure) throws Exception {

        HttpURLConnection con = null;
        try {
            /**
             * 设置请求编码方式 根据传的数据结构来变化
             * 设置把自己请求工具信息给服务器
             * 设置请求的超时时间
             * 对这个请求自动跟随重定向
             * Cookies 未处理
             * 301，302表示重定向 未处理
             */
            URL url = new URL(urlEncodeChinese(urlStr));
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Content-Type", "text/plain");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            con.setInstanceFollowRedirects(true);
            con.setDoOutput(true);

            con.setDoInput(true);

            // 设置请求参数
            // 非上传 && 参数  (因为上传文件和参数会有冲突)
            if (!reqType.equals(upload) && param != null) {
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.write(objectToString(param));
                out.flush();
                out.close();
            }

            // upload && 文件存在 -> 上传文件
            if ((reqType.equals(upload)) && (filePath != null)) {
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                this.uploadFileStream(filePath, out, rspProgress);
            }

            /**
             * 读取响应状态码
             */
            if (con.getResponseCode() == 200) {

                // download类型 && 文件存在 -> 下载文件
                if (reqType.equals(download) && (filePath != null)) {
                    System.out.println(con.getHeaderFields());
                    InputStream in = con.getInputStream();
                    this.downloadFileStream(con, filePath, in, rspProgress);
                    rspSuccess.success("下载完成");
                }
                // 非文件
                else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String tmpStr = "";
                    StringBuffer rspContent = new StringBuffer();
                    while ((tmpStr = in.readLine()) != null) {
                        rspContent.append(tmpStr);
                    }
                    rspSuccess.success(rspContent.toString());
                    in.close();
                }

            } else {
                rspFailure.failure(con.getResponseMessage());
            }

        } catch (MalformedURLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {

            if (con != null) {
                con.disconnect();
            }
        }
    }

    // 统一处理参数文件上传 先读后传
    private void uploadFileStream(String fromPath, OutputStream out, QSRspProgress RspProgress) throws Exception {

        DataInputStream in = null;

        try {
            File file = new File(fromPath);
            if (file.exists()) {

                /**
                 * 文件总大小
                 * 当前上传的文件大小
                 * 因为进度不可能每次while都执行一次，应该要根据100个阶级算
                 */
                long fileSize = file.length();
                long stepLength = fileSize / 100; // 步长
                long nextLength = stepLength;     // 下一个回调进度的比较值
                long sendSize = 0;

                in = new DataInputStream(new FileInputStream(file));

                int length = 0;
                byte[] buffer = new byte[2048];
                while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);

                    // 每触发一次步长就回传一次数据
                    sendSize += length;
                    if (sendSize >= nextLength) {
                        nextLength += stepLength; // 下一个步长

                        float progressFloat = (float) sendSize / (float) fileSize;
                        RspProgress.progress(progressFloat);
                    }
                }
                out.flush();
                out.close();
            } else {
                throw new IllegalArgumentException("传入文件路径不正确");
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    // 统一处理文件下载
    private void downloadFileStream(HttpURLConnection con, String toPath, InputStream in, QSRspProgress RspProgress) throws Exception {

        OutputStream out = null;

        /**
         * 获取需要下载的文件名 拼接到目标目录后面，拼接后如果已经存在就不下载了
         * 从Content-Disposition属性里拿：结构 raw = "attachment; filename=abc.zip"
         */
        String raw = con.getHeaderField("Content-Disposition");
        if(raw != null && raw.indexOf("=") != -1) {
            String fileName = raw.split("=")[1]; //获取'='后面部分
            toPath += "/"; // + new Date().getTime();
            toPath += fileName == null ? "" : fileName;
            System.out.println(toPath);
        }

        try {
            File file = new File(toPath);
            if (!file.exists()) {

                /**
                 * 文件总大小
                 * 当前上传的文件大小
                 * 因为进度不可能每次while都执行一次，应该要根据100个阶级算
                 */
                long fileSize = con.getContentLength();
                long stepLength = fileSize / 100; // 步长
                long nextLength = stepLength;     // 下一个回调进度的比较值
                long sendSize = 0;

                out = new FileOutputStream(file);

                int length = 0;
                byte[] buffer = new byte[2048];
                while ((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);

                    // 每触发一次步长就回传一次数据
                    sendSize += length;
                    if (sendSize >= nextLength) {
                        nextLength += stepLength; // 下一个步长

                        float progressFloat = (float)sendSize / (float)fileSize;
                        RspProgress.progress(progressFloat);
                    }
                }
                in.close();
            }
            else {
                throw new IllegalArgumentException("文件已存在，不重复下载");
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /***
     * 将 Object数据转化为 String
     * @param params 传入Map参数
     * @return 拼接后的字符串
     */
    private static byte[] objectToString(Object params) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();

        // 如果是Map
        if (params instanceof Map) {
            for (Map.Entry<String, Object> entry : ((Map<String, Object>)params).entrySet()) {
                result.append(((String) params));
                result.append("=");
                // 递归
                result.append(((String) params));
                result.append("&");
            }
        }
        // 如果是List
        else if (params instanceof List) {
            for (Object element : (List)params) {

            }
        }
        // 如果是String
        else if (params instanceof String){
            result.append(((String) params));
        }

        String resultStr = result.toString();
        if (resultStr == null) {
            resultStr = "";
        }
        else if ((resultStr.length() > 0) && resultStr.endsWith("&")) {
            resultStr = resultStr.substring(0, (resultStr.length() - 1));
        }

        return resultStr.getBytes("utf-8");
    }

    /**
     * 只把URL中的中文转换成URL编码
     * @param url 需要转的URL
     * @return 转后返回
     */
    private static String urlEncodeChinese(String url) {
        try {
            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
            String tmp = "";
            while (matcher.find()) {
                tmp = matcher.group();
                url = url.replaceAll(tmp, URLEncoder.encode(tmp, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
