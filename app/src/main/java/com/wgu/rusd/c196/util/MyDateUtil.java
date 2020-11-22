package com.wgu.rusd.c196.util;

import java.time.format.DateTimeFormatter;

public final class MyDateUtil {

    private MyDateUtil(){}
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");
    public static final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("LLL-dd-YYYY");
}
