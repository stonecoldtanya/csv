package readerCSV;

import java.io.*;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Read {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader( new FileReader("src/readerCSV/roads.csv"));

//        InputStream inputStream = new ;
//        CSV reader = new CSV(inputStream);
//        List<String[]> records = reader.readAll();


        CSV csvReader = new CSV(reader, ',');
        csvReader.setPath("src/readerCSV/roads.csv");
        List<String[]> records = csvReader.getContent();
        List<String[]> contentR = csvReader.readContent();
//        csvReader.setPath("src/readerCSV/roads.csv");
        //reader.printCSV("src/readerCSV/roads.csv");
//        reader.printCSV();

        records.forEach(array -> System.out.println(Arrays.toString(array)));
        String[] colHead = csvReader.getHeaderColumn();
        String[] header = csvReader.getHeaderRow();

        System.out.println(Arrays.toString(colHead));
        System.out.println(csvReader.getColumnsCount());
        System.out.println(csvReader.getRecordDelimiter());

    }
}
