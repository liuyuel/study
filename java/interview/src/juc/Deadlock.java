package juc;


/**
 * Java 类作用描述
 * 进程:操作系统资源调度的基本单位
 * 线程:程序执行的最小单位
 * 写一个程序模拟死锁(两个线程相互持有对方想访问的资源,等待对方释放资源,产生死锁)
 *
 * @author :    yueliuk 170115
 * @version :   1.0
 * @apiNote :   SycnWaitNotify 类作用描述
 * @date :      2020/11/12 11:25
 * @see SycnWaitNotify
 * @since :     2020/11/12 11:25
 */
public class Deadlock {



  //  资源1
  static Object recourse1 = new Object();
  //  资源2
  static Object recourse2 = new Object();


  public static void main(String[] args) {

    new Thread(() -> {
      synchronized (recourse1) {

        try {
          System.out.println(Thread.currentThread().getName() + "获取recourse1");
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (recourse2) {
          System.out.println(Thread.currentThread().getName() + "获取recourse2");
        }
      }

    }, "线程1").start();

    new Thread(() -> {
      synchronized (recourse2) {

        try {
          System.out.println(Thread.currentThread().getName() + "获取recourse2");
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (recourse1) {
          System.out.println(Thread.currentThread().getName() + "获取recourse1");
        }
      }


    }, "线程2").start();

  }
}
