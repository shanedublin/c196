package com.wgu.rusd.c196.mentor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MentorDAO {

    @Query("select * from mentor")
    LiveData<List<Mentor>> loadAll();

    @Query("select * from mentor where mentorId =(:id)")
    Mentor findMentor(long id);


    @Insert
    void insertAll(Mentor... mentors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Mentor mentor);


    @Delete
    void delete(Mentor mentor);
}
