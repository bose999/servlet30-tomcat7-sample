package jp.techie.sample.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import jp.techie.sample.util.LogUtil;

/**
 * Servlet3.0非同期サンプル AsyncListener
 * 
 * @author bose999
 *
 */
public class SampleAsyncListener implements AsyncListener {
    
    /**
     * ログユーティリティ
     */
    private static LogUtil logUtil = new LogUtil(SampleAsyncListener.class);

    /**
     * 非同期処理 終了時処理
     * 
     * @param asyncEvent AsyncEvent
     */
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        logUtil.trace("onComplete");
    }

    /**
     * 非同期処理 エラー時処理
     * 
     * @param asyncEvent AsyncEvent
     */
    public void onError(AsyncEvent asyncEvent) throws IOException {
        // エラーが起きた時の処理としてタイムアウトもこの処理を通る
        try {
            logUtil.fatal("AchicocoActionListener onError");

            AsyncContext asyncContext = asyncEvent.getAsyncContext();
            ServletRequest request = asyncContext.getRequest();
          
            // エラーが起きたのでエラーページへ遷移
            RequestDispatcher dispatch = request.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
            ServletResponse response = asyncContext.getResponse();
            dispatch.forward(request, response);

            logUtil.fatal("dispatch errorPage");
        } catch (Exception e) {
            try {
                logUtil.fatal("AchicocoActionListener.onError() Exception", e);
                e.printStackTrace();
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 非同期処理 開始時処理
     * 
     * @param asyncEvent AsyncEvent
     */
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        logUtil.trace("onStartAsync");
    }

    /**
     * 非同期処理 タイムアウト時処理
     * 
     * @param asyncEvent AsyncEvent
     */
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        logUtil.fatal("Service Timeout");
    }
}
