package com.siran;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 启动quartz任务
 *
 */
public class AppStarter
{
    public static void main(String[] args) throws Exception {
        // Creating scheduler factory and scheduler
        SchedulerFactory factory = new StdSchedulerFactory(
                "quartz.properties");
        Scheduler scheduler = factory.getScheduler();
        // Start scheduler
        scheduler.start();
    }
}
