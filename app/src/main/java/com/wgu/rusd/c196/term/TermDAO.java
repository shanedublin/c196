package com.wgu.rusd.c196.term;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface TermDAO {
    public static final String TAG = TermDAO.class.getName();


    @Query("select * from Term")
    LiveData<List<Term>> loadAll();


    @Transaction
    @Query("select * from term")
    LiveData<List<TermWithCourses>> termWithAssessments();


    @Insert
    void insertAll(Term... term);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Term term);


    @Transaction
    @Delete
    void delete(Term term);
}
