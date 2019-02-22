package com.android.byc.myhousecoins.db;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 13:28
 * @description
 */
@Entity(nameInDb = "CurrencyTaskRecords")
public class CurrencyTaskRecordsEntity {
    /***/
    @Id
    public Long id;
    /***/
    @Property(nameInDb = "PKUser")
    @Convert(converter = UUID2BytesConverter.class, columnType = byte[].class)
    public UUID pkUser;
    /***/
    @Property(nameInDb = "CurrencyTaskID")
    public Long currencyTaskId;
    /** 任务次数，如邀请N个人开通手机梵讯 */
    @Property(nameInDb = "Count")
    public int count;
    @Generated(hash = 475303973)
    public CurrencyTaskRecordsEntity(Long id, UUID pkUser, Long currencyTaskId,
                                     int count) {
        this.id = id;
        this.pkUser = pkUser;
        this.currencyTaskId = currencyTaskId;
        this.count = count;
    }
    //@Generated(hash = 750224161)
    public CurrencyTaskRecordsEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UUID getPkUser() {
        return this.pkUser;
    }
    public void setPkUser(UUID pkUser) {
        this.pkUser = pkUser;
    }
    public Long getCurrencyTaskId() {
        return this.currencyTaskId;
    }
    public void setCurrencyTaskId(Long currencyTaskId) {
        this.currencyTaskId = currencyTaskId;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}
