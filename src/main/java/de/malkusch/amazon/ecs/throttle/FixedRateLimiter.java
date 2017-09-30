package de.malkusch.amazon.ecs.throttle;

import java.util.concurrent.TimeUnit;

public class FixedRateLimiter {

    private final long throttle;
    private long lastInvocationTime;

    public FixedRateLimiter(long throttle) {
        this.throttle = TimeUnit.MILLISECONDS.toNanos(throttle);
    }

    public void acquire() {
        long timeFromLastInvocation = System.nanoTime() - lastInvocationTime;
        long sleep = throttle - timeFromLastInvocation;
        if (sleep > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lastInvocationTime = System.nanoTime();
    }
}
