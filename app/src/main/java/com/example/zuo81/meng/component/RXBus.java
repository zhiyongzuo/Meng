package com.example.zuo81.meng.component;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by zuo81 on 2017/10/30.
 */

public class RXBus {
    private FlowableProcessor<Object> bus;
    private RXBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RXBus getInstance() {
        return RXHolder.bus;
    }

    private static class RXHolder {
        private static RXBus bus = new RXBus();
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Flowable<T> toFlowable(Class<T> type) {
        return bus.ofType(type);
    }

}
