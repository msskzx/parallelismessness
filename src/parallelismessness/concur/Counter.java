package parallelismessness.concur;

import java.util.concurrent.atomic.AtomicInteger;

class Counter{

	private int counter;
    private AtomicInteger atomicCounter;
	private final Object lock;
	
	Counter() {
		this.counter = 0;
        this.atomicCounter = new AtomicInteger(0);
		this.lock = new Object();
	}
	
	void incCounter() {
		synchronized (lock) {
			this.counter++;
		}
	}

    synchronized void incCounterSync() {
        this.counter++;
    }

    void incAtomicCounter() {
        this.atomicCounter.getAndIncrement();
    }

    void incCounterWrong() {
        this.counter++;
    }
	
	int getCounter() {
		return this.counter;
	}

    int getAtomicCounter() {
		return this.atomicCounter.get();
	}
	
	public static void main(String[] args) {
		Counter counter = new Counter();
        System.out.printf("Initial Counter: %d\n", counter.getAtomicCounter());
		
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				counter.incAtomicCounter();
			}
		});
		
		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				counter.incAtomicCounter();
			}
		});
		
		thread2.start();
		thread1.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Final Counter: %d\n", counter.getAtomicCounter());
	}

}
