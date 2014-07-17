/** Switch Log paser
 * @author Jacksieen
 * Date Jul 2 17:20 CST 2014
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;

public class logger{
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(new File("remotelog"));
        /* String text = in.nextLine(); */
        logger log = new logger();
        while (in.hasNextLine()){
            String text=in.nextLine();
            parsing(text);
        }
    }
    static void parsing(String log){
        /* regex to split messages */
        String reg = "-==-";
        Pattern pat = Pattern.compile(reg);
        String[] ss = pat.split(log);
        if (ss[0].equals("[JP]")){
            JP j = new JP(ss);
        }
        else if (ss[0].equals("[H3C]")){

        }
        else if (ss[0].equals("[CS]")){

        }
    }

}

class logentry{
    public String tag;
    public Date receiveTime = new Date();
    public Date deviceTime;
    public String host;
    public String facility;
    public String content;

    logentry(String[] logs){       //constructor
        SimpleDateFormat sdf = new SimpleDateFormat("MMM  dd HH:mm:ss");
        try{
            deviceTime = sdf.parse(logs[1]);
        }catch (ParseException pe){
            deviceTime = new Date();
        }
        deviceTime.setYear(new Date().getYear());
        host = logs[2];
        facility = logs[3];
        content = logs[4];
        
    }
}


class JP extends logentry{
    public String module;
    public String user;
    public String fromHost;
    public String[] cmds;
    String mailContent;
    JP(String[] logs){
        super(logs);
        String reg = "%\\S+:";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(content);
        if (mat.find()){
            module = mat.group();
            content = mat.replaceFirst("");
        }
        if (facility.contains("login") || facility.contains("mgd")){
            parse();
        }
    }

    void parse(){
        if (module.contains("AUTH-5") && content.contains("attempt")){
            Pattern pat = Pattern.compile("user \\w+");
            Matcher mat = pat.matcher(content);
            if (mat.find()){
                user = mat.group().replace("user ","");
            }
            pat = Pattern.compile("host \\S+");
            mat = pat.matcher(content);
            if (mat.find()){
                fromHost = mat.group().replace("host ","");
            }
            mailContent = String.format("User %s attempt to login %s from %s, at %s", user, host, fromHost, receiveTime);
            System.out.println(mailContent);
        }
    }
}

/* class H3C extends logentry{ */
    /*  */
/* } */
