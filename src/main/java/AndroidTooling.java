import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class AndroidTooling {
    String project1srcDir;
    String project2srcDir;
    private class Replacement {
        public String searched;
        public String replacement;

        public String getSearched() {
            return searched;
        }

        public void setSearched(String searched) {
            this.searched = searched;
        }

        public String getReplacement() {
            return replacement;
        }

        public void setReplacement(String replacement) {
            this.replacement = replacement;
        }
    };
    private List<Replacement> replacements = new ArrayList<>();
    public void copy(File pathProject1, File pathProject2) throws URISyntaxException, IOException {

        //Path copy = Files.copy(Path.of(pathProject1.toURI()), Path.of(pathProject2.toURI()));
    }
    List<String> stack = new ArrayList<>();
    public void copyRecursive(File src1) {
        if(src1.isDirectory()) {
            stack.add(src1.getName());
        for (File f : Objects.requireNonNull(src1.listFiles())) {
            copyRecursive(f);
        }
        stack.remove(src1.getName());
    } else if (src1.isFile() && src1.getName().endsWith("java")) {
        filtered(src1, destFile(project2srcDir, src1.getName()));
    } else if (src1.isFile() && !src1.getName().endsWith("java")) {
            unfiltered(src1, destFile(project2srcDir, src1.getName()));
        }
    }

    public void filtered(File javaSource, File destFile) {
        if(destFile.exists())
            return;
        try {
            FileInputStream fileInputStream = new FileInputStream(javaSource);
            byte[] bytes = fileInputStream.readAllBytes();
            final String[] file = {Arrays.toString(bytes)};
            replacements.forEach(new Consumer<Replacement>() {
                                     @Override
                                     public void accept(Replacement replacement) {
                                         file[0] = file[0].replace(replacement.getSearched(), replacement.getReplacement());

                                     }
                                 });
            PrintWriter printWriter = new PrintWriter(destFile);
            printWriter.println(file[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void unfiltered(File src1, File destFile) {

    }

    private File destFile(String baseDir, String name) {
        String ret = baseDir+File.separator;
        Object[] objects = stack.toArray();
        for (int i = 0; i < objects.length; i++) {
            ret += objects[i]+File.separator;
        }
        return new File(ret+name);
    }

    public static void  main(String [ ] args ) {
        args[0] = "c:\\users\\manue\\IdeaProjects\\empty3-library-generic\\src\\main\\java";
        args[1] = "c:\\users\\manue\\AndroidStudiosProject\\empty3-library-generic-android\\src\\main\\java";
        File file = new File(args[0]);
        File file1 = new File(args[1]);
        file.mkdirs();
        file.mkdirs();
        AndroidTooling androidTooling = new AndroidTooling();
        try {
            androidTooling.copy(file, file1);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
