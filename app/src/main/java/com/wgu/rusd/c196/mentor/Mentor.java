package com.wgu.rusd.c196.mentor;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Mentor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long mentorId;
    public String phoneNumber;
    public String emailAddress;
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
