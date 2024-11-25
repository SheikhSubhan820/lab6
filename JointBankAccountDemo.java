package task1;

class JointBankAccount {
    private int balance = 50000;

    // Synchronized method to handle withdrawal
    public synchronized void withdraw(String accountHolder, int amount) {
        System.out.println(accountHolder + " is trying to withdraw: " + amount);

        if (balance >= amount) {
            System.out.println(accountHolder + " is allowed to withdraw.");
            try {
                // Simulate time delay for withdrawal
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -= amount;
            System.out.println(accountHolder + " successfully withdrew " + amount + ". Remaining balance: " + balance);
        } else {
            System.out.println("Insufficient balance for " + accountHolder + ". Remaining balance: " + balance);
        }
    }

    public int getBalance() {
        return balance;
    }
}

class AccountHolder extends Thread {
    private JointBankAccount account;
    private String accountHolderName;
    private int withdrawalAmount;

    public AccountHolder(JointBankAccount account, String accountHolderName, int withdrawalAmount) {
        this.account = account;
        this.accountHolderName = accountHolderName;
        this.withdrawalAmount = withdrawalAmount;
    }

    @Override
    public void run() {
        account.withdraw(accountHolderName, withdrawalAmount);
    }
}

public class JointBankAccountDemo {
    public static void main(String[] args) {
        JointBankAccount account = new JointBankAccount();

        // Create two account holders
        AccountHolder userA = new AccountHolder(account, "User A", 45000);
        AccountHolder userB = new AccountHolder(account, "User B", 20000);

        // Start both threads
        userA.start();
        userB.start();
    }
}
