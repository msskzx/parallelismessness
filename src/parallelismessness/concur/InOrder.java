package parallelismessness.concur;

import java.util.concurrent.CountDownLatch;

class InOrder {

    private CountDownLatch firstLatch;
    private CountDownLatch secondLatch;

    public InOrder() {
        this.firstLatch = new CountDownLatch(1);
        this.secondLatch = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        
        printFirst.run();
        this.firstLatch.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        this.firstLatch.await();
        printSecond.run();
        this.secondLatch.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        this.secondLatch.await();
        printThird.run();
    }
    
    public static void main(String[] args) {
		InOrder test = new InOrder();
		
		Thread thread1 = new Thread(() -> {
			System.out.println("first");
		});
		
		Thread thread2 = new Thread(() -> {
			System.out.println("second");
		});
		
		Thread thread3 = new Thread(() -> {
			System.out.println("third");
		});
		
		
		Thread main1 = new Thread(() -> {
			try {
				test.first(thread1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread main2 = new Thread(() -> {
			try {
				test.second(thread2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread main3 = new Thread(() -> {
			try {
				test.third(thread3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		main3.start();
		main2.start();
		main1.start();
		
		try {
			main3.join();
			main2.join();
			main1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
















