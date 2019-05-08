public class Transaction {
    private String hash;
    private long block;
    private String from;
    private String to;
    private String assetName;
    private long amount;
    private Long timeStamp;
    private Boolean status;
    private String type;
    private long assetAmount;

    public Transaction(String hash, long block, String from, String to, String assetName, long amount, Long timeStamp, Boolean status, String type) {
        this.hash = hash;
        this.block = block;
        this.from = from;
        this.to = to;
        this.assetName = assetName;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.status = status;
        this.type = type;
    }

    public Transaction(){

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getBlock() {
        return block;
    }

    public void setBlock(long block) {
        this.block = block;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(long assetAmount) {
        this.assetAmount = assetAmount;
    }
}
