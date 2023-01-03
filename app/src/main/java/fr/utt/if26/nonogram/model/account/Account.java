package fr.utt.if26.nonogram.model.account;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Entity(tableName = "account")
public class Account implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private final int id;
    private final String username;
    private final String passHash;

    public Account(int id, String username, String passHash) {
        this.id = id;
        this.username = username;
        this.passHash = passHash;
    }

    public static String getSHA256(String string) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static Account withPassword(int id, String username, String password) {
        return new Account(id, username, getSHA256(password));
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassHash() {
        return passHash;
    }
}
