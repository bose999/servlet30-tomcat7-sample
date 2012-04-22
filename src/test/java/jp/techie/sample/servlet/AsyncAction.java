package jp.techie.sample.servlet;

import javax.servlet.AsyncContext;

import jp.techie.sample.util.LogUtil;

/**
 * Servlet3.0非同期サンプル 非同期処理
 * 
 * @author bose999
 *
 */
public class AsyncAction implements Runnable {

    /**
     * ログユーティリティ
     */
    private static LogUtil logUtil = new LogUtil(AsyncAction.class);

    /**
     * AsyncContext
     */
    public AsyncContext asyncContext;

    /**
     * コンストラクタ
     * 
     * @param asyncContext AsyncContext
     */
    public AsyncAction(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        long startTime = 0;

        if (logUtil.isTraceEnabled()) {
            // 処理開始ログ出力
            startTime = System.currentTimeMillis();
            logUtil.trace("Start AsyncAction");
        }

        String dispatchUrl = "dipatchUrl:/WEB-INF/jsp/result.jsp";

        // 処理終了後 遷移先を設定
        logUtil.trace(dispatchUrl);

        // dispatch呼び出すとcompleteは自動で行われる
        asyncContext.dispatch(dispatchUrl);

        if (logUtil.isTraceEnabled()) {
            // 処理時間ログ出力
            long doTime = System.currentTimeMillis() - startTime;
            logUtil.trace("End AsyncAction:" + doTime + "ms.");
        }
    }
}
