package juc;


/**
 * Java 类作用描述 两个线程交替打印1A2B...
 *
 * @author :    yueliuk 170115
 * @version :   1.0
 * @apiNote :   SycnWaitNotify 类作用描述
 * @date :      2020/11/12 12:29
 * @see SycnWaitNotify
 * @since :     2020/11/12 12:29
 */
public class SycnWaitNotify {


  final static Object lock = new Object();

  static char[] data1 = "12345678".toCharArray();
  static char[] data2 = "ABCDEFGH".toCharArray();

  public static void main(String[] args) {

    printCASNumBefore();
    printCASLetterBefore();
//    printSync();
  }


  public static void printSync() {

    new Thread(() -> {
      synchronized (lock) {
        try {
          for (char t : data1) {
            System.out.print(t);
            lock.notify();
            lock.wait();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        lock.notify();
      }

    }).start();
    new Thread(() -> {
      synchronized (lock) {
        try {
          for (char t : data2) {
            System.out.print(t);
            lock.notify();
            lock.wait();

          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        lock.notify();
      }
    }).start();

  }


  public static void printCASNumBefore() {
    LockForVolatile lockForVolatile = new LockForVolatile();
    new Thread(() -> {

      for (char t : data1) {
        System.out.print(t);
        lockForVolatile.newCondition(false);
        lockForVolatile.unlock();
      }
    }).start();
    new Thread(() -> {
      for (char t : data2) {
        lockForVolatile.lock();
        System.out.print(t);
        lockForVolatile.newCondition(true);
      }
      lockForVolatile.newCondition(true);
    }).start();

  }

  public static void printCASLetterBefore() {
    LockForVolatile lockForVolatile = new LockForVolatile();
    new Thread(() -> {

      for (char t : data1) {
        lockForVolatile.lock();
        System.out.print(t);
        lockForVolatile.newCondition(true);
      }
      lockForVolatile.newCondition(true);
    }).start();
    new Thread(() -> {
      for (char t : data2) {
        System.out.print(t);
        lockForVolatile.newCondition(false);
        lockForVolatile.unlock();
      }
    }).start();

  }

}


