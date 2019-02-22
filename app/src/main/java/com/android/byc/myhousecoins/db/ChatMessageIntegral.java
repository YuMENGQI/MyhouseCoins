package com.android.byc.myhousecoins.db;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 17:52
 * @description
 */
@Entity(nameInDb = "ChatCurrencies")
public class ChatMessageIntegral {
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_EXPENSE = 2;

    @Property(nameInDb = "PKChatCurrency")
    @Convert(converter = UUID2BytesConverter.class, columnType = byte[].class)
    public UUID PKChatCurrency;

    @Property(nameInDb = "Amount")
    public int Amount;

    @Property(nameInDb = "Type")
    public int Type;

    @Property(nameInDb = "Description")
    public String Description;
    @Property(nameInDb = "CreateTime")
    public String CreateTime;

    @Keep

    public ChatMessageIntegral(UUID PKChatCurrency, int amount, int type, String description, String createTime) {
        this.PKChatCurrency = PKChatCurrency;
        this.Amount = amount;
        this.Type = type;
        this.Description = description;
        this.CreateTime = createTime;
    }

    //@Generated (hash = 339223182)
    public ChatMessageIntegral() {
    }

    public static int getTypeIncome() {
        return TYPE_INCOME;
    }

    public static int getTypeExpense() {
        return TYPE_EXPENSE;
    }

    public UUID getPKChatCurrency() {
        return PKChatCurrency;
    }

    public void setPKChatCurrency(UUID PKChatCurrency) {
        this.PKChatCurrency = PKChatCurrency;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
