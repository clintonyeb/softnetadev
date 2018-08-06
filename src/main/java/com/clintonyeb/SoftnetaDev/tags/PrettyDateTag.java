package com.clintonyeb.SoftnetaDev.tags;

import org.ocpsoft.prettytime.PrettyTime;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrettyDateTag extends SimpleTagSupport {
    private String date;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static PrettyTime prettyTime = new PrettyTime();

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void doTag() throws IOException {
        String output = "now";

        if(date != null){
            try {
                output = formatDate(date);
            } catch (Exception e){
                // pass
            }
        }

        JspWriter out = getJspContext().getOut();
        out.print(output);
    }

    private static String formatDate(String dateString) throws ParseException {
        Date d = formatter.parse(dateString);
        return prettyTime.format(d);
    }
}
