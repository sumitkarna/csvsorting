package com.hm.csvsorter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.opencsv.CSVWriter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class CsvSortTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
	final static String CSV_1 = "src/test/resources/csv1.csv";
	final static String CSV_2= "src/test/resources/csv2.csv";
	

    public void testSorting(){
   
    	prepareData();
    	String[] files = {CSV_1,CSV_2};
    	CsvSorter.main(files);
    }
    
    
	private static void prepareData() {
		try (Writer writer = Files.newBufferedWriter(Paths.get(CSV_1), StandardCharsets.UTF_8,
				StandardOpenOption.WRITE);

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

				Writer writer1 = Files.newBufferedWriter(Paths.get(CSV_2), StandardCharsets.UTF_8,
						StandardOpenOption.WRITE);

				CSVWriter csvWriter1 = new CSVWriter(writer1, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

		) {
			String[] headerRecord = { "a", "b", "c" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "2", "3" });
			csvWriter.writeNext(new String[] { "2", "3", "2" });
			csvWriter.writeNext(new String[] { "3", "4", "1" });
			String[] columnd = { "d" };
			csvWriter1.writeNext(columnd);

			csvWriter1.writeNext(new String[] { "6" });
			csvWriter1.writeNext(new String[] { "4" });
			csvWriter1.writeNext(new String[] { "3" });
			csvWriter1.writeNext(new String[] { "2" });
			csvWriter1.writeNext(new String[] { "1" });
			csvWriter1.writeNext(new String[] { "5" });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
