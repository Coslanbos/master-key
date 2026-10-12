package packer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class store {

    private static final Path FOLDER = appFold().resolve("PROPERTIES");
    private static final String PREFIX = "account-";
    private static final String SUFFIX = ".properties";

  
    public static class Data {
        public final String title;    
        public final String url;
        public final String user;    
        public final String password;
        public final String email;

        public Data(String title, String url, String user, String password, String email) {
            this.title = title;
            this.url = url;
            this.user = user;
            this.password = password;
            this.email = email;
        }
    }

    public void saveAll(List<Data> accounts) throws IOException {
        Files.createDirectories(FOLDER);

        
        Set<String> keep = new HashSet<>();
        for (int i = 0; i < accounts.size(); i++) {
            Data d = accounts.get(i);
            Properties p = new Properties();
            p.setProperty("title", d.title);
            p.setProperty("url", d.url);
            p.setProperty("user", d.user);
            p.setProperty("password", d.password);
            p.setProperty("email",d.email);

            String fileName = String.format("%s%03d%s", PREFIX, i, SUFFIX);
            keep.add(fileName);
            try (Writer w = Files.newBufferedWriter(FOLDER.resolve(fileName), StandardCharsets.UTF_8)) {
                p.store(w, null);
            }
        }

        
        try (DirectoryStream<Path> files = Files.newDirectoryStream(FOLDER, PREFIX + "*" + SUFFIX)) {
            for (Path f : files) {
                if (!keep.contains(f.getFileName().toString())) {
                    Files.delete(f);
                }
            }
        }
    }

    public List<Data> loadAll() throws IOException {
        List<Data> result = new ArrayList<>();
        if (!Files.isDirectory(FOLDER)) {
            return result;
        }

        List<Path> paths = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(FOLDER, PREFIX + "*" + SUFFIX)) {
            for (Path f : files) {
                paths.add(f);
            }
        }
        paths.sort((a, b) -> a.getFileName().toString().compareTo(b.getFileName().toString()));

        for (Path f : paths) {
            Properties p = new Properties();
            try (Reader r = Files.newBufferedReader(f, StandardCharsets.UTF_8)) {
                p.load(r);
            }
            result.add(new Data(
                    p.getProperty("title", ""),
                    p.getProperty("url", ""),
                    p.getProperty("user", ""),
                    p.getProperty("password", ""),
                    p.getProperty("email","")));
        }
        return result;
    }
    private static Path appFold(){
        try {
            Path locat = Paths.get(store.class
            .getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isRegularFile(locat)){
                return locat.getParent();
            }
        } catch (URISyntaxException ex) {
            
        }
        return Paths.get("user.home").toAbsolutePath();
    }
}
