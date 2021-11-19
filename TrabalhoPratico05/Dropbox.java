public class Dropbox {

  private int number;
  private boolean evenNumber = false;
  private boolean isReady = false;

  public synchronized int take(final boolean even) {
    while (this.isReady == false) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }

    if (even == this.evenNumber) {
      this.isReady = false;
      System.out.format(
        "%s CONSUMIDOR obtem %d.%n",
        even ? "PAR" : "IMPAR",
        this.number
      );
    }
    notifyAll();
    return this.number;
  }

  public synchronized void put(int number) {
    while (this.isReady == true) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }
    this.number = number;
    this.evenNumber = number % 2 == 0;
    this.isReady = true;
    System.out.format("PRODUTOR gera %d.%n", number);
    notifyAll();
  }
}
