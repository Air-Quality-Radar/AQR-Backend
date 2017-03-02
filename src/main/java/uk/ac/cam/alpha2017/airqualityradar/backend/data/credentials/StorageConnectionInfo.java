package uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials;

public class StorageConnectionInfo {
    private String accountName;
    private String accountKey;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getStorageConnectionString() {
        return String.format("DefaultEndpointsProtocol=http;AccountName=%s;AccountKey=%s", accountName, accountKey);
    }
}
