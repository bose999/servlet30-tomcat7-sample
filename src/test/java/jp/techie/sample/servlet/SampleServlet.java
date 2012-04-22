/*
 * Copyright 2012 bose999.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
