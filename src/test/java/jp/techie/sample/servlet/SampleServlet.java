package jp.techie.sample.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.techie.sample.util.LogUtil;

/**
 * Servlet3.0非同期サンプル Servlet
 * 
 * @author bose999
 *
 */
@WebServlet(name = "sample", urlPatterns ="/sample", asyncSupported=true)
@SuppressWarnings("serial")
public class SampleServlet extends HttpServlet {

    /**
     * ログユーティリティ
     */
    private static LogUtil logUtil = new LogUtil(SampleServlet.class);

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        long startTime = 0;
        if (logUtil.isTraceEnabled()) {
            startTime = System.currentTimeMillis();
            logUtil.trace("Start doGet Method");
        }

        AsyncContext asyncContext = request.startAsync();
        SampleAsyncListener sampleAsyncListener = new SampleAsyncListener();
        asyncContext.addListener(sampleAsyncListener);

        AsyncAction asyncAction = new AsyncAction(asyncContext);

        // 別スレッドで処理実行しこのスレッドは解放する
        // 別スレッドはSampleAsyncListenerによってイベント処理される
        asyncContext.start(asyncAction);

        if (logUtil.isTraceEnabled()) {
            long doTime = System.currentTimeMillis() - startTime;
            logUtil.trace("End doGet Method:" + doTime + "ms.");
        }
    }
}
