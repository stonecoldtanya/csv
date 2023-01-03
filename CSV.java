package readerCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;

public final class CSV{
    private Reader reader;
    private InputStream inputStream;
    private String fileSrc;
    private char delimiter;

    public CSV(Reader reader, char delimiter) {
        if (reader == null) {
            throw new IllegalArgumentException("The reader cannot be null.");
        }

        this.reader = reader;
        this.delimiter = getRecordDelimiter();
    }

    public CSV(Reader reader) {
        this(reader, ',');
    }

    public CSV(InputStream inputStream) {
        this(inputStream, ',');
    }

    public CSV(InputStream inputStream, char delimiter) {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null.");
        }

        this.inputStream = inputStream;
        this.delimiter = getRecordDelimiter();
//        this.userSettings.Delimiter = delimiter;
//        initialized = true;
//
//        isQualified = new boolean[values.length];
    }

    public CSV(String fileSrc, char delimiter) throws FileNotFoundException {
        if (fileSrc == null) {
            throw new IllegalArgumentException("File source cannot be null.");
        }
        if (!new File(fileSrc).exists()) {
            throw new FileNotFoundException("File " + fileSrc + " does not exist.");
        }
        this.fileSrc = fileSrc;
        this.delimiter = getRecordDelimiter();
    }

    public CSV(String fileSrc) throws FileNotFoundException{
        this(fileSrc, ',');
    }

    public char getRecordDelimiter(){
        return delimiter;
    }

    public void close(){

    }

}
