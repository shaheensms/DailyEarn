package com.metacoders.dailyearn.models;

public class modelForBalDb {
    String earn_Bonus , equity_balance  , joining_Bonus , afflicted_Bonus ,mutual_Bonus , global_profit_share ,
            reward_Bonus ;


    public modelForBalDb() {
    }


    public modelForBalDb(String earn_Bonus, String equity_balance, String joining_Bonus,
                         String afflicted_Bonus, String mutual_Bonus,
                         String global_profit_share, String reward_Bonus) {
        this.earn_Bonus = earn_Bonus;
        this.equity_balance = equity_balance;
        this.joining_Bonus = joining_Bonus;
        this.afflicted_Bonus = afflicted_Bonus;
        this.mutual_Bonus = mutual_Bonus;
        this.global_profit_share = global_profit_share;
        this.reward_Bonus = reward_Bonus;
    }

    public String getJoining_Bonus() {
        return joining_Bonus;
    }

    public void setJoining_Bonus(String joining_Bonus) {
        this.joining_Bonus = joining_Bonus;
    }

    public String getAfflicted_Bonus() {
        return afflicted_Bonus;
    }

    public void setAfflicted_Bonus(String afflicted_Bonus) {
        this.afflicted_Bonus = afflicted_Bonus;
    }

    public String getMutual_Bonus() {
        return mutual_Bonus;
    }

    public void setMutual_Bonus(String mutual_Bonus) {
        this.mutual_Bonus = mutual_Bonus;
    }

    public String getGlobal_profit_share() {
        return global_profit_share;
    }

    public void setGlobal_profit_share(String global_profit_share) {
        this.global_profit_share = global_profit_share;
    }

    public String getReward_Bonus() {
        return reward_Bonus;
    }

    public void setReward_Bonus(String reward_Bonus) {
        this.reward_Bonus = reward_Bonus;
    }

    public String getEquity_balance() {
        return equity_balance;
    }

    public void setEquity_balance(String equity_balance) {
        this.equity_balance = equity_balance;
    }

    public String getEarn_Bonus() {
        return earn_Bonus;
    }

    public void setEarn_Bonus(String earn_Bonus) {
        this.earn_Bonus = earn_Bonus;
    }
}
