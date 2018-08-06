package com.clintonyeb.SoftnetaDev.helpers;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utility {
    public static Reader makeHTTPRequest(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        // request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response;
        try {
            response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();

            BufferedReader rd;

            if (status == HttpStatus.SC_OK) {
                rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                return rd;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }
}
