package juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java 类作用描述 写一个程序，证明AtomXXX类比synchronized更高效
 * 10个线程创建threadCountW个objectCount对象
 *
 * @author :    yueliuk 170115
 * @version :   1.0
 * @apiNote :   SyncAndCAS 类作用描述
 * @date :      2020/11/12 16:00
 * @see SyncAndCAS
 * @since :     2020/11/12 16:00
 */
public class SyncAndCAS {

  final int objectCount = 100_000;
  final int threadCount = 100;
  volatile int count = 0;
  /*volatile*/ AtomicInteger atomicIntegerCount = new AtomicInteger(0);


  void createObjectBySync() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < threadCount; i++) {
      new Thread(() -> {
        synchronized (SyncAndCAS.class) {
          while (count < objectCount) {
            new Object();
            count++;
//            System.out.println(count);
          }
        }
      }).start();
    }
    while (count < objectCount) {
    }
    long end = System.currentTimeMillis();
    System.out.println("synchronized耗时" + (end - start) + "ms");
  }

  void createObjectByAtomic() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < threadCount; i++) {
      new Thread(() -> {
        while (atomicIntegerCount.get() < objectCount) {
          new Object();
          atomicIntegerCount.incrementAndGet();
//          System.out.println(atomicIntegerCount);
        }
      }).start();
    }
    while (atomicIntegerCount.get() < objectCount) {
    }
    long end = System.currentTimeMillis();
    System.out.println("AtomicInteger耗时" + (end - start) + "ms");
  }


  public static void main(String[] args) {
    SyncAndCAS syncAndCAS = new SyncAndCAS();
    syncAndCAS.createObjectBySync();
    syncAndCAS.createObjectByAtomic();

  }

}
