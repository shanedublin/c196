package com.wgu.rusd.c196.term;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class Term implements Serializable, Comparable<Term> {

    @PrimaryKey(autoGenerate = true)
    public Long termId;
    public String title;
    public LocalDate start;
    public LocalDate end;

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return Objects.equals(termId, term.termId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termId);
    }

    @Override
    public int compareTo(Term term) {
        if(term == null) {
            return 1;
        }
        return start.compareTo(term.start);
    }
}
