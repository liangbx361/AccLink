package com.out.accu.link.data;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2017/2/22
 */

public class ImmediateSchedulersRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(scheduler ->
                        Schedulers.trampoline());
//                RxJavaPlugins.setComputationSchedulerHandler(scheduler ->
//                        Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler ->
                        Schedulers.trampoline());

                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler ->
                        Schedulers.trampoline());

                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                }
            }
        };
    }
}
