# Parallelismessness

Providing examples about parallelism, processes, threads, concurrency, and related topics. I started with `Java`, and soon I will also add `C++` and `Rust`.

## Thread Safety and Race Conditions

A race condition occurs when multiple threads or processes try to change shared resource simultaneously which can lead to undesirable results.

### Lock

In `Counter.java` a number of threads try to increment the counter, without handling race conditions, the threads read inconsistent state of the counter, so maybe both read 10 at the same time and increment it to 11. This results in wrong final value:

```java
void incCounterWrong() {
    this.counter++;
}
```

```bash
Final Counter: 19762
```

In this example we used `lock` which a thread must gain before it can execute the code within that code **block**. If the lock is held by another thread, other threads are **blocked**. Handling of race condition using `lock`:

```java
private final Object lock;

void incCounter() {
    synchronized (lock) {
        this.counter++;
    }
}
```

```bash
Final Counter: 20000
```

### Synchronized Method

In this scenario, the whole method is synchronized. This means all synchronized methods share the same lock on the class instance level, which means that no other synchronized method is allowed to execute simultaneously:

```java
synchronized void incCounterSync() {
    this.counter++;
}
```

```bash
Final Counter: 20000
```

### Atomic Variables

`AtomicInteger` provides a wrapper around the int so that you can call the operation and the synchronization is handled inside without you handling it. It uses `Compare-and-Swap (CAS)` to ensure thread safety without using `locks`.

```java
private AtomicInteger atomicCounter;
void incAtomicCounter() {
    this.atomicCounter.getAndIncrement();
}
```

```bash
Final Counter: 20000
```

## Services Initialization

A common scenario when using multiple threads is that you need to wait for some services to initialize before proceeding. For that `CountDownLatch` could be used, to ensure a condition is met before proceeding. In `InOrder.java` we need certain order of execution when we are running multiple threads.

## Executing Threads Alternately

One approach to execute two threads in an alternating order is to use `Semaphore` where each thread acquires its own semaphore and releases the semaphore for the other thread, like in 