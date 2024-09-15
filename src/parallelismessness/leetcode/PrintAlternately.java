package parallelismessness.leetcode;

// https://leetcode.com/problems/print-foobar-alternately/

import java.util.concurrent.Semaphore;

class PrintAlternately {
    private int n;
    private Semaphore foo;
    private Semaphore bar;

    public PrintAlternately(int n) {
        this.foo = new Semaphore(1);
        this.bar = new Semaphore(0);
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            this.foo.acquire();
        	printFoo.run();
            this.bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            this.bar.acquire();
        	printBar.run();
            this.foo.release();
        }
    }
}