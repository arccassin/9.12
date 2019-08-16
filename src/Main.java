import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        String url = "data/code.html";
        String out = "/out/production/images/";
        File input = new File(url);
        try {

            Document doc = Jsoup.parse(input, "UTF-8");
            Elements pictures = doc.select("[src~=(?i)\\.(png|jpe?g)]");
            HashSet<String> sUrls = new HashSet<>();
            pictures.stream().forEach(e -> sUrls.add(e.attr("src")));
            int ii = 0;
            for (String sUrl : sUrls) {

                Path path = Paths.get(System.getProperty("user.dir") + out + sUrl.substring(sUrl.lastIndexOf('/')+1));
                if(Files.exists(path)){
                    continue;
                }
                InputStream in = new URL(sUrl).openStream();
                Files.copy(in, path);
                ii++;
            }
            System.out.printf("Done! Copy %d pictures", ii);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
