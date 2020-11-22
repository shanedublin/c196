package com.wgu.rusd.c196.assessment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Assessment assessment);

    @Transaction
    @Delete
    void delete(Assessment assessment);

    @Query("select * from assessment")
    LiveData<List<Assessment>> loadAll();

}
