import java.util.Random;
import java.util.Scanner;

class DingdingShop {
    private static final int MAX_DUMPLINGS = 9000;
    private static final int MAX_CUSTOMERS = 900;
    private static final int WAIT_TIME = 3000;
    
    private int pigDumplings;
    private int beefDumplings;
    private int veggieDumplings;
    
    public DingdingShop() {
        pigDumplings = 5000;
        beefDumplings = 3000;
        veggieDumplings = 1000;
    }
    
    public synchronized boolean sellDumplings(String type, int quantity) {
        if (type.equals("豬肉水餃")) {
            if (quantity <= pigDumplings) {
                pigDumplings -= quantity;
                return true;
            }
        } else if (type.equals("牛肉水餃")) {
            if (quantity <= beefDumplings) {
                beefDumplings -= quantity;
                return true;
            }
        } else if (type.equals("蔬菜水餃")) {
            if (quantity <= veggieDumplings) {
                veggieDumplings -= quantity;
                return true;
            }
        }
        return false;
    }
}

class Customer implements Runnable {
    private static final int MIN_QUANTITY = 10;
    private static final int MAX_QUANTITY = 50;
    private static final int WAIT_TIME = 3000;
    
    private static final String[] DUMPLING_TYPES = {
        "豬肉水餃", "牛肉水餃", "蔬菜水餃"
    };
    
    private DingdingShop shop;
    private Random random;

    
    public Customer(DingdingShop shop) {
        this.shop = shop;
        this.random = new Random();
    }
    
    @Override
    public void run() {
        String dumplingType = getRandomDumplingType();
        int dumplingQuantity = getRandomQuantity();
        
        boolean sold = shop.sellDumplings(dumplingType, dumplingQuantity);
        
        if (sold) {
            System.out.println(Thread.currentThread().getName() + " 購買了 " +
                               dumplingQuantity + " 顆 " + dumplingType);
        } else {
            System.out.println(Thread.currentThread().getName() + " 水餃售完");
        }
        
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private String getRandomDumplingType() {
        int index = random.nextInt(DUMPLING_TYPES.length);
        return DUMPLING_TYPES[index];
    }
    
    private int getRandomQuantity() {
        return random.nextInt(MAX_QUANTITY - MIN_QUANTITY + 1) + MIN_QUANTITY;
    }
}

public class A1091267_0512 {
    public static void main(String[] args) {
        DingdingShop dingdingshop = new DingdingShop();
        int numCustomers = getNumCustomersFromUser();
        
        for (int i = 0; i < numCustomers; i++) {
            Customer customer = new Customer(dingdingshop);
            Thread thread = new Thread(customer, "顧客 " + i);
            thread.start();
        }
    }
    
    private static int getNumCustomersFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入顧客數目：");
        int numCustomers = scanner.nextInt();
        scanner.close();
        return numCustomers;
    }
}