package com.joaquin;

import java.math.BigDecimal;

public class BankResponse {
    private BigDecimal quota;
    private BigDecimal rate;
    private Integer term;

    private BigDecimal quotaWithAccount;
    private BigDecimal rateWithAccount;
    private Integer termWithAccount;

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRateWithAccount() {
        return rateWithAccount;
    }

    public void setRateWithAccount(BigDecimal rateWithAccount) {
        this.rateWithAccount = rateWithAccount;
    }

    public BigDecimal getQuotaWithAccount() {
        return quotaWithAccount;
    }

    public void setQuotaWithAccount(BigDecimal quotaWithAccount) {
        this.quotaWithAccount = quotaWithAccount;
    }

    public Integer getTermWithAccount() {
        return termWithAccount;
    }

    public void setTermWithAccount(Integer termWithAccount) {
        this.termWithAccount = termWithAccount;
    }
}
