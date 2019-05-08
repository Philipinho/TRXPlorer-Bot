import java.util.Map;

public class Account {
    private String name;
    private String address;
    private long balance;
    private long frozenBalance;
    private String created;
    private Map<String, Long> assets;

    public Account(String name, String address, long balance, long frozenBalance, String created, Map<String, Long> assets) {
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.frozenBalance = frozenBalance;
        this.created = created;
        this.assets = assets;
    }

    public Account(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Map<String, Long> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, Long> assets) {
        this.assets = assets;
    }
}
