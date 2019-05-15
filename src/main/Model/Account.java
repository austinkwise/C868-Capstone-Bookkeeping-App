package main.Model;

public class Account {
    private int accountId;
    private String account;
    private String accountType;
    private String accountName;
    private String accountDescription;
    private int archiveAccount;


    public Account(int accountId, String account, String accountType, String accountName, String accountDescription, int archiveAccount) {
        this.accountId = accountId;
        this.account = account;
        this.accountType = accountType;
        this.accountName = accountName;
        this.accountDescription = accountDescription;
        this.archiveAccount = archiveAccount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountDescription() {
        return accountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public int getArchiveAccount() {
        return archiveAccount;
    }

    public void setArchiveAccount(int archiveAccount) {
        this.archiveAccount = archiveAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
