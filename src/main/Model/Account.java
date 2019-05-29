package main.Model;

public class Account {
    private String accountId;
    private String account;
    private String accountType;
    private String accountName;
    private String accountDescription;
    private String archiveAccount;
    private String userId;

    public Account(String accountId, String account, String accountType, String accountName, String accountDescription, String archiveAccount, String userId) {
        this.accountId = accountId;
        this.account = account;
        this.accountType = accountType;
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.archiveAccount = archiveAccount;
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public String getArchiveAccount() {
        return archiveAccount;
    }

    public void setArchiveAccount(String archiveAccount) {
        this.archiveAccount = archiveAccount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
