package readerCSV;

import java.io.*;
import java.util.*;

public class CSV implements Iterable<String[]> {
    private BufferedReader reader;
    private char delimiter;
    private List<String[]> content = new ArrayList<>();
    private String path;

    public CSV(Reader reader, char delimiter) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("The reader cannot be null.");
        }

        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.content = readAll();

    }

    public CSV(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("The reader cannot be null.");
        }

        this.reader = new BufferedReader(reader);
        this.delimiter = ',';
    }

    public CSV(InputStream inputStream) throws IOException {
        this(inputStream, ',');
    }

    public CSV(InputStream inputStream, char delimiter) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null.");
        }

        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.delimiter = delimiter;
    }

    public CSV(String fileName, char delimiter) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("File source cannot be null.");
        }
        if (!new File(fileName).exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist.");
        }
        this.reader = new BufferedReader(new FileReader(fileName));
        this.delimiter = delimiter;
    }

    public CSV(String fileName) throws IOException {
        this.reader = new BufferedReader(new FileReader(fileName));
        this.delimiter = ',';
    }

    //reads the whole file including headers
    public List<String[]> readAll() throws IOException {
        List<String[]> records = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(String.valueOf(delimiter));
            records.add(values);
        }
        return records;
    }

    //reads only the content
    public List<String[]> readContent() throws IOException {
        List<String[]> recordsContent = new ArrayList<>();

        for (int i = 1; i < content.size(); i++){
            List<String> values = new LinkedList<String>(Arrays.asList(content.get(i)));
            values.remove(0);
            recordsContent.add(values.toArray(new String[0]));
        }
        return recordsContent;
    }

    public List<String[]> readAll(int start,int end) throws IOException {
        List<String[]> records = new ArrayList<>();
        String line;
        int lineCount = 0;
        while ((line = reader.readLine()) != null && lineCount < end) {
            String[] values = line.split(String.valueOf(delimiter));
            records.add(values);
            lineCount++;
        }
        return records;
    }

    public char getRecordDelimiter(){
        return delimiter;
    }

    public void close() throws IOException {
        reader.close();
    }

    String[] getHeaderRow() throws IOException{
        return content.get(0);
    }

    public String[] getHeaderColumn() throws IOException  {
        List<String> headerColumn = new ArrayList<>();
        for (int i = 0; i < content.size(); i++){
              String[] values = content.get(i);
              headerColumn.add(values[0]);  // add the first value of each line to the list
        }
        return headerColumn.toArray(new String[0]);
    }

    int getColumnsCount() throws IOException {
        return getHeaderColumn().length - 1;
    }

    int getRecordsCount(){
        return content.size() -1;
    }

    Map<String, String> get(int i){
        return null;
    }

    @Override
    public Iterator<String[]> iterator() {
        return new CSVIterator();
    }

    private class CSVIterator implements Iterator<String[]> {

        private String nextLine;

        public CSVIterator() {
            try {
                this.nextLine = reader.readLine();  // read the first line of the file
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;  // there are more lines if nextLine is not null
        }

        @Override
        public String[] next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String[] values = nextLine.split(String.valueOf(delimiter));
            try {
                nextLine = reader.readLine();  // read the next line of the file
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return values;
        }
    }

    //Map<String, String> get(i) – Get the i-th record – key,value by the header row
    //Map<String, String> get(columnHeader) – Get the record, identified by the given header column element

    //List<String[]> myEntries = reader.read(0, 10); - read records from 0, up

    public String getPath(){
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String[]> getContent() {
        return content;
    }

    public void setContent(List<String[]> content) {
        this.content = content;
    }
}
