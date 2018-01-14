package com.gateway.queue;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@SuppressWarnings("InfiniteLoopStatement")
@Component
public class Queue implements Runnable {

    private LinkedBlockingQueue<Supplier<Boolean>> queue = new LinkedBlockingQueue<>();


    public void run() {
        while (true) {
            try {
                Supplier<Boolean> f = queue.take();
                if (!f.get()) {
                    queue.put(f);
                }
                Thread.sleep(400);
            }
            catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void addTask(Supplier<Boolean> fun) {
        try {
            queue.put(fun);
        }
        catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }


    }
}
