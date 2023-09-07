package com.lcwd.todo.Helper;

import org.springframework.http.converter.json.GsonBuilderUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Helper {

    public static Date parseDate(LocalDateTime localDateTime){
        //System.out.println("Time" + localDateTime);
        //Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

//    public static Date parseDate(String dateStr) throws ParseException {
//        SimpleDateFormat simpleDateFormat =new SimpleDateFormat();
//        Date formatedDate = simpleDateFormat.parse(dateStr);
//        return formatedDate;
//    }
    public void testHello(){
        System.out.println("Hello, I am also Here.");
    }
}

