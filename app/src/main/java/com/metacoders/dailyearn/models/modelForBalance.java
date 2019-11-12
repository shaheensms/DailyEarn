package com.metacoders.dailyearn.models;

public class modelForBalance {
    String afflicted_Bonus , earn_Bonus , global_profit_share ,joining_Bonus ,mutual_Bonus ,reward_Bonus  ,equity_balance ;

    public modelForBalance() {
    }

    public modelForBalance(String afflicted_Bonus, String earn_Bonus, String global_profit_share,
                           String joining_Bonus, String mutual_Bonus, String reward_Bonus, String equity_balance) {
        this.afflicted_Bonus = afflicted_Bonus;
        this.earn_Bonus = earn_Bonus;
        this.global_profit_share = global_profit_share;
        this.joining_Bonus = joining_Bonus;
        this.mutual_Bonus = mutual_Bonus;
        this.reward_Bonus = reward_Bonus;
        this.equity_balance = equity_balance;
    }

    public String getEquity_balance() {
        return equity_balance;
    }

    public void setEquity_balance(String equity_balance) {
        this.equity_balance = equity_balance;
    }

    public String getAfflicted_Bonus() {
        return afflicted_Bonus;
    }

    public void setAfflicted_Bonus(String afflicted_Bonus) {
        this.afflicted_Bonus = afflicted_Bonus;
    }

    public String getEarn_Bonus() {
        return earn_Bonus;
    }

    public void setEarn_Bonus(String earn_Bonus) {
        this.earn_Bonus = earn_Bonus;
    }

    public String getGlobal_profit_share() {
        return global_profit_share;
    }

    public void setGlobal_profit_share(String global_profit_share) {
        this.global_profit_share = global_profit_share;
    }

    public String getJoining_Bonus() {
        return joining_Bonus;
    }

    public void setJoining_Bonus(String joining_Bonus) {
        this.joining_Bonus = joining_Bonus;
    }

    public String getMutual_Bonus() {
        return mutual_Bonus;
    }

    public void setMutual_Bonus(String mutual_Bonus) {
        this.mutual_Bonus = mutual_Bonus;
    }

    public String getReward_Bonus() {
        return reward_Bonus;
    }

    public void setReward_Bonus(String reward_Bonus) {
        this.reward_Bonus = reward_Bonus;
    }
}
