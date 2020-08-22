package com.upic.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* *
 *类名：HttpProtocolHandler
 *功能：HttpClient方式访问
 *详细：获取远程HTTP数据
 */

public class HttpProtocolHandler {
    protected static final Logger LOG = LoggerFactory.getLogger(HttpProtocolHandler.class);
    private static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 连接超时时间，由bean factory设置，缺省为8秒钟
     */
    private int defaultConnectionTimeout = 30 * 1000;

    /**
     * 回应超时时间, 由bean factory设置，缺省为30秒钟
     */
    private int defaultSoTimeout = 30 * 1000;

    /**
     * 闲置连接超时时间, 由bean factory设置，缺省为60秒钟
     */
    private int defaultIdleConnTimeout = 60000;

    private int defaultMaxConnPerHost = 30;

    private int defaultMaxTotalConn = 80;

    /**
     * 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒
     */
    private static final long defaultHttpConnectionManagerTimeout = 30 * 1000;

    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     */
    private HttpConnectionManager connectionManager;

    private static HttpProtocolHandler httpProtocolHandler;

    /**
     * 工厂方法
     *
     * @return
     */
    public synchronized static HttpProtocolHandler getInstance() {
        if (httpProtocolHandler == null) {
            httpProtocolHandler = new HttpProtocolHandler();
        }
        return httpProtocolHandler;
    }

    /**
     * 私有的构造方法
     */
    private HttpProtocolHandler() {
        // 创建一个线程安全的HTTP连接池
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
        connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);

        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(connectionManager);
        ict.setConnectionTimeout(defaultIdleConnTimeout);

        ict.start();
    }

    public String executeGET(String url) throws Exception {
        LOG.debug("executeGET访问微信服务器URL为：" + url);
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setMethod(HttpRequest.METHOD_GET);
        request.setUrl(url);
        HttpResponse response = execute(request, null, null, null);
        String result = response.getStringResult();
        LOG.debug("HttpProtocolHandler-->executeGET 返回结果：" + result);
        return result;
    }

    public String executePOST(String url, String entity) throws Exception {
        LOG.debug("executePOST访问微信服务器URL为：" + url + "entity=" + entity);
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setUrl(url);
        HttpResponse response = execute(request, entity, "", "");
        String result = response.getStringResult();
        LOG.debug("HttpProtocolHandler-->executePOST 返回结果：" + result);
        return result;
    }

    public String executeUpload(String url, String strFilePath) throws Exception {
        LOG.debug("executeUpload访问微信服务器URL为：" + url + " strFilePath=" + strFilePath);
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setUrl(url);
        HttpResponse response = execute(request, "", "", strFilePath);
        String result = response.getStringResult();
        LOG.debug("HttpProtocolHandler-->executeUpload 返回结果：" + result);
        return result;
    }

    /**
     * 执行Http请求
     *
     * @param request         请求数据
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath     文件路径
     * @return
     * @throws HttpException , IOException
     */
    public HttpResponse execute(HttpRequest request, String entity, String strParaFileName, String strFilePath) throws HttpException, IOException {
        HttpMethod method = null;
        HttpResponse response = null;
        try {
            HttpClient httpclient = new HttpClient(connectionManager);

            // 设置连接超时
            int connectionTimeout = defaultConnectionTimeout;
            if (request.getConnectionTimeout() > 0) {
                connectionTimeout = request.getConnectionTimeout();
            }
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);

            // 设置回应超时
            int soTimeout = defaultSoTimeout;
            if (request.getTimeout() > 0) {
                soTimeout = request.getTimeout();
            }
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

            // 设置等待ConnectionManager释放connection的时间
            httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

            String charset = request.getCharset();
            charset = charset == null ? DEFAULT_CHARSET : charset;


            // get模式且不带上传文件
            if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
                method = new GetMethod(request.getUrl());
                method.getParams().setCredentialCharset(charset);

                // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
                // method.setQueryString(request.getQueryString());
            } else if (entity != null && !"".equals(entity)) {
                method = new PostMethod(request.getUrl());
                method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
                // 设置请求体
                ((PostMethod) method).setRequestEntity(new StringRequestEntity(entity, "text/html", charset));

            } else if (strParaFileName.equals("") && strFilePath.equals("")) {
                // post模式且不带上传文件
                method = new PostMethod(request.getUrl());
                ((PostMethod) method).addParameters(request.getParameters());
                method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);

            } else {
                // post模式且带上传文件
                method = new PostMethod(request.getUrl());
                List<Part> parts = new ArrayList<Part>();
                for (int i = 0; request.getParameters() != null && i < request.getParameters().length; i++) {
                    parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), charset));
                }
                // 增加文件参数，strParaFileName是参数名，使用本地文件
                parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));

                // 设置请求体
                ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpMethodParams()));
            }

            // 设置Http Header中的User-Agent属性
            method.addRequestHeader("User-Agent", "Mozilla/4.0");
            response = new HttpResponse();

            httpclient.executeMethod(method);
            if (request.getResultType().equals(HttpResultType.STRING)) {
                response.setStringResult(method.getResponseBodyAsString());
            } else if (request.getResultType().equals(HttpResultType.BYTES)) {
                response.setByteResult(method.getResponseBody());
            }
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("HttpProtocolHandler-->execute 发生错误", ex);
            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     * 将NameValuePairs数组转变为字符串
     *
     * @param nameValues
     * @return
     */
    protected String toString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];
            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }
}
