package com.test;

import com.google.common.util.concurrent.*;
import javafx.concurrent.Task;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by guangrongyang on 2017/8/3.
 */
public class TestFutureCahe<K, V> {
    private final ConcurrentHashMap<K, Future<V>> cacheMap = new ConcurrentHashMap<K, Future<V>>();

    public Object getCache(K keyValue, String ThreadName) {
        Future<V> value = null;
        try {
            System.out.println("ThreadName getCache==============" + ThreadName);
            //从缓存获取数据
            value = cacheMap.get(keyValue);
            //如果没有的话，把数据放到缓存
            if (value == null) {
                value = putCache(keyValue, ThreadName);
                return value.get();
            }
            return value.get();

        } catch (Exception e) {
        }
        return null;
    }


    public Future<V> putCache(K keyValue, final String ThreadName) {
//		//把数据放到缓存
        Future<V> value = null;
        Callable<V> callable = new Callable<V>() {
            @SuppressWarnings("unchecked")
            @Override
            public V call() throws Exception {
                //可以根据业务从数据库获取等取得数据,这边就模拟已经获取数据了
                System.out.println("ThreadName 执行业务数据并返回处理结果的数据（访问数据库等）==============" + ThreadName);
                return (V) "dataValue";
            }
        };
        FutureTask<V> futureTask = new FutureTask<V>(callable);
        value = cacheMap.putIfAbsent(keyValue, futureTask);
        if (value == null) {
            value = futureTask;
            futureTask.run();
        }
        return value;
    }

    private  String testFuture() throws Exception {
        int id;
        id = new Random().nextInt();
        Future<String> future = null;
        Callable<String> callable = new CallTask(String.valueOf(id));

        Callable<String> callable2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "aaa";
            }

        };
        FutureTask futureTask = new Task() {
            @Override
            protected Object call() throws Exception {
                return null;
            }
        };


        //1 guava 异步
        final ListeningExecutorService listeningExecutorService = MoreExecutors.newDirectExecutorService();
        final AsyncCallable<String> asyncCallable = Callables.asAsyncCallable(callable, listeningExecutorService);
        final ListenableFuture<String> future1 = asyncCallable.call();
        final String s = future1.get();


        //2 java 自带 异步
        FutureTask<String> futureTask1 = new FutureTask(callable);
        futureTask1.run();
        try {
            final String o = futureTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //3 guava 线程池submit
        final ListeningExecutorService listeningExecutorService1 = MoreExecutors.newDirectExecutorService();
        final ListenableFuture<String> listenableFuture = listeningExecutorService1.submit(callable);
        listeningExecutorService1.shutdown();
        final String s1 = listenableFuture.get();


        return null;
    }



    class CallTask implements Callable<String>{
        private String id;

        public CallTask(String id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {

            return id;
        }
    }


    public static void main(String[] args) throws Exception {
        TestFutureCahe testFutureCahe = new TestFutureCahe();
//        testFutureCahe.testFuture();
         ListeningExecutorService listeningExecutorService1 = MoreExecutors.newDirectExecutorService();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String id;
                id = String.valueOf(new Random().nextInt());
                final ListenableFuture<String> submit = listeningExecutorService1.submit(new aa(id));
                listeningExecutorService1.shutdown();
                final String s1;
                try {
                    s1 = submit.get();
                    System.out.println(s1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        t1.start();

        addCallback();


    }

    private static void addCallback() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture explosion = service.submit(new Callable() {
            public Object call() {
                return new Object();
            }
        });
        Futures.addCallback(explosion, new FutureCallback() {
            // we want this handler to run immediately after we push the big red button!

            @Override
            public void onSuccess(Object o) {

            }

            public void onFailure(Throwable thrown) {
//                battleArchNemesis(); // escaped the explosion!
            }
        });
    }

}