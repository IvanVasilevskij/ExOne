package ExOne.view;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileExplorerFx {
    FileExplorerFx(){}

    public static List<File> allFileWithFormat(List<File> filesList, String strPath, String format) {
        if (!format.startsWith(".")) format = "." + format;
        File file = new File(strPath);
        if (!file.isDirectory() && strPath.endsWith(format)) {
            filesList.add(file);
        } else if (file.isDirectory()) {
            DirectoryStream<Path> directoryStream = null;
            try {
                directoryStream = Files.newDirectoryStream(Paths.get(strPath));
            } catch (IOException ignored) {}
            if (directoryStream != null) {
                for (Path pp : directoryStream) {
                    String newPath = pp.toString();
                    allFileWithFormat(filesList, newPath, format);
                }
            }
        }
        return filesList;
    }

    public List<File> allFilesWithExpression(List<File> files, String expression, List<File> needFiles) {
        if (expression.equals("")) return needFiles;
        String line;
        for (File f : files) {
            try (
                    InputStream fis = new FileInputStream(f);
                    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                    BufferedReader br = new BufferedReader(isr)
            ) {
                while ((line = br.readLine()) != null) {
                    if (line.contains(expression)) {
                        needFiles.add(f);
                        break;
                    }
                }
            } catch (Exception ignore) {}
        }
            return needFiles;
    }

    public String readFromFileAndSetOnArea(File file) {
        try {
            StringBuilder stb = new StringBuilder();
            Path path = file.toPath();
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(stb::append);
                lines.close();
            }
            return stb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private int indexOfExp(int start, String entedString, String exspression) {
        return entedString.indexOf(exspression, start);
    }

    public List<Integer> countOfExpression(List<Integer> allIndexes, String enteredString, String expression) {
        int start = 0;
        if (enteredString.substring(start).contains(expression)){
            while (start < enteredString.length() && enteredString.substring(start).contains(expression)) {
                allIndexes.add(indexOfExp(start, enteredString, expression));
                start = indexOfExp(start, enteredString, expression) + start + expression.length();
            }
        }
        return allIndexes;
    }
}
