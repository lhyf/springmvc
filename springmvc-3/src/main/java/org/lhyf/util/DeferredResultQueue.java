package org.lhyf.util;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/****
 * @author YF
 * @date 2019-03-27 11:06
 * @desc DeferredResultQueue
 *
 **/
@Component
public class DeferredResultQueue<T> {
    private Queue<T> queue = new ConcurrentLinkedQueue();

    public T get() {
        return queue.poll();
    }

    public void save(T obj) {
        queue.add(obj);
    }
}
