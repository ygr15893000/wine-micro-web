package com.test;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * Created by guangrongyang on 2017/8/3.
 */
public class AsSyncServlet extends HttpServlet {
    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext asyncContext = req.getAsyncContext();
        final DoSome runable = new DoSome(asyncContext);
        final Future<?> future = executorService.submit(runable);
        executorService.shutdown();
        try {
            final Object o = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    class DoSome implements Runnable {
        private AsyncContext asyncContext;

        public DoSome(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        @Override
        public void run() {
            final ServletRequest request = asyncContext.getRequest();
            //TODO
            PrintWriter out = null;
            try {
                out = asyncContext.getResponse().getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println("业务处理完毕的时间：");
            out.flush();
            asyncContext.complete();
        }
    }

    class DoSome2 implements Callable<Integer> {
        private AsyncContext asyncContext;

        public DoSome2(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        @Override
        public Integer call() {
            final ServletRequest request = asyncContext.getRequest();
            //TODO
            PrintWriter out = null;
            try {
                out = asyncContext.getResponse().getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println("业务处理完毕的时间：");
            out.flush();
            asyncContext.complete();
            return null;
        }
    }

}
