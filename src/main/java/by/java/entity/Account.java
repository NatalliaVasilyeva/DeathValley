package by.java.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Account {
    private Integer accountId;
    private Integer account;
    private Integer userId;

    private Account() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Integer getAccount() {
        return account;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account1 = (Account) o;
        return Objects.equals(accountId, account1.accountId) &&
                Objects.equals(account, account1.account) &&
                Objects.equals(userId, account1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, account, userId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("accountId=" + accountId)
                .add("account=" + account)
                .add("userId=" + userId)
                .toString();
    }

    public static class AccountBuilder {
        private Account account;

        public AccountBuilder() {
            account = new Account();
        }

        public AccountBuilder withId(Integer accountId){
            account.accountId = accountId;
            return this;
        }

        public AccountBuilder withAccount(Integer userAccount){
            account.account = userAccount;
            return this;
        }

        public AccountBuilder withUser(Integer userId){
            account.userId = userId;
            return this;
        }

        public Account build(){
            return account;
        }

    }
}
