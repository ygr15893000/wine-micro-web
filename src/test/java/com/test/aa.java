package com.test;

import java.util.concurrent.Callable;

/**
 * Created by guangrongyang on 2017/8/3.
 */
public class aa implements Callable<String> {
    private String id;

    public aa(String id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {

        return id;
    }
}
