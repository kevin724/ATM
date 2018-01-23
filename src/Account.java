public class Account {
    private int id;
    private String password;
    private double balance;

    public Account(int id, String password, double balance) {
        if (password == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.password = password;
        this.balance = balance;
    }

    public int getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public double getBalance() {
        return this.balance;
    }

    public void withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Amount is larger than balance. No changes made");
        } else {
            this.balance -= amount;
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean isMatching(int id, String password) {
        return (this.id == id) && this.password.equals(password);
    }

}
