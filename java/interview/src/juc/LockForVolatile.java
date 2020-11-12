package juc;


public class LockForVolatile {


  private volatile boolean lockCas = true;

  public LockForVolatile() {
  }

  public void lock() {
    while (lockCas) {
    }
  }

  public void unlock() {
    while (!lockCas) {
    }
  }



  public void newCondition(boolean lockCas) {
    this.lockCas = lockCas;
  }
}
