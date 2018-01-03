/**
 * Created by amitd92 on 4/12/17.
 */
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseWeb implements Serializable{

    BufferedWriter bw = null;
    FileWriter fw = null;
    public static String filterNonEnglish(String s){
        List<String> myList = new ArrayList<String>(Arrays.asList(s.split(" ")));
        String ret ="";
        for(String temp:myList){
            Pattern p= Pattern.compile("[^\\u0000-\\u007F]+");
            Matcher m = p.matcher(temp);
            if (!m.matches()){
                ret = ret +" "+ temp;
            }
        }

        //boolean b = ;
        //System.out.println(s.matches("\\w+"));
        return ret;
    }

    public static String ParseBody_Jsoup(File file) {
        //File file = new File("/home/amitd92/Downloads/NYTimesData/NYTimesDownloadData/0a1a4dfd-c645-4118-a29d-45c38028b984.html");
        Document doc = null;
        boolean flag = false;
        String ret = "";
        try {
            doc = Jsoup.parse(file, "UTF-8", file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements body = doc.select("body");
        Elements titles = doc.select("title");
        LanguageIdentifier object=new LanguageIdentifier(body.text());
        if (object.getLanguage().toString().equals("en")){
            ret=filterNonEnglish(body.text())+" "+ret;
            flag= true;
        }


        if (flag) {
            for (Element link : titles) {
                //  System.out.println(imports.text());
                ret = link.text() + " " + ret;
            }
            flag=false;
        }
        return ret;
    }

    static void writetoFile(String writeme) throws IOException {
        File file = new File("big.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        // true = append file
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(writeme.toString());
        bw.newLine();
        bw.flush();
        fw.close();
    }

    public static void main(final String[] args) throws IOException, SAXException, TikaException {

        File dir = new File("/home/amitd92/solr/NYTimesData/NYTimesDownloadData/");
        File[] directoryListing = dir.listFiles();

        int fileWrite = 0;
        int remaining = directoryListing.length;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String write = ParseBody_Jsoup(child);
                if (!write.equals("") ){
                    writetoFile(write);
                }

            }
        }
    }
}
