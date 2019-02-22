package com.android.byc.myhousecoins.db;

import com.android.byc.myhousecoins.utility.Utility;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 15:04
 * @description
 */
public class UUID2BytesConverter implements PropertyConverter<UUID, byte[]> {
    @Override
    public UUID convertToEntityProperty(byte[] databaseValue) {
        return Utility.ConvertBytesToUUID(databaseValue);
    }

    @Override
    public byte[] convertToDatabaseValue(UUID entityProperty) {
        String str = Utility.ConverterUUIDToHexString(entityProperty);
        str = str.replace("X","").replace("'","");
        byte[] bytes = new byte[16];
        for(int i = 0; i<str.length();i=i+2){
            bytes[i/2] = ConvertHexStringToByte(str.substring(i,i+2));
        }
        return bytes;
    }
    byte ConvertHexStringToByte(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword[0];
    }

}
