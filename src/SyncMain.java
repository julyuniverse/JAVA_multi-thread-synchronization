class Bank {
    private int money = 10000;

    // 동기화가 필요한 메서드에 synchronized 적용
    public synchronized void saveMoney(int save) {
        int m = getMoney();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setMoney(m + save);
    }

    public synchronized void minusMoney(int minus) {
        int m = getMoney();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setMoney(m - minus);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

// Park 쓰레드
class Park extends Thread {
    public void run() {
        System.out.println("start save");
        SyncMain.bank.saveMoney(3000);
        System.out.println("saveMoney(3000): " + SyncMain.bank.getMoney());
    }
}

// ParkWife 쓰레드
class ParkWife extends Thread {
    public void run() {
        System.out.println("start minus");
        SyncMain.bank.minusMoney(1000);
        System.out.println("minusMoney(1000): " + SyncMain.bank.getMoney());
    }
}

public class SyncMain {
    public static Bank bank = new Bank();

    public static void main(String[] args) {
        Park p = new Park();
        p.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ParkWife pw = new ParkWife();
        pw.start();

        // 결과
        // start save
        // start minus
        // saveMoney(3000): 13000
        // minusMoney(1000): 12000
    }
}
