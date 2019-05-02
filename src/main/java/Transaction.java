public class Transaction {
    private String hash;
    private long block;
    private String from;
    private String to;
    private Long timeStamp;
    private Boolean status;
    private String type;

    public Transaction(String hash, long block, String from, String to, Long timeStamp, Boolean status, String type) {
        this.hash = hash;
        this.block = block;
        this.from = from;
        this.to = to;
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
}
