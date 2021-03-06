/**
 * 
 */
package com.hm.csvsorter;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * @author Sumit Karna
 *
 */
public class CsvSorter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
		if (args.length != 4)
			throw new IllegalArgumentException("This needs two csv files and the column names");

		String csv1_path = args[0];
		String csv2_path = args[2];
		try {
		List<String[]> csv = readFromCsv(csv1_path);
		List<String[]> columnd = readFromCsv(csv2_path);

		int index = ArrayUtils.indexOf(csv.get(0), args[1]);
		int rows = csv.size();
		int cols = csv.get(0).length;

		HashMap<String, String> map = new HashMap<>();
		HashMap<String, String> reverseMap = new HashMap<>();
		for (int i = 1; i < columnd.size(); i++) {
			map.put(columnd.get(i)[0], String.valueOf(i));
			reverseMap.put(String.valueOf(i), columnd.get(i)[0]);
		}
		
		// use map of d on column b
		for (int i = 1; i < rows; i++) {
			csv.get(i)[index] = map.get(csv.get(i)[index]); 
		}

		ArrayList<ArrayList<String>> al = new ArrayList<>();
		for (int i = 1; i < rows; i++) {
			al.add(new ArrayList<String>(Arrays.asList(csv.get(i))));
		}

		StringArrayComparator lComparator = new StringArrayComparator(index);
		Collections.sort(al, lComparator);

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				csv.get(i)[j] = al.get(i-1).get(j);
			}
		}
		// retrieve the original value of column b
		for (int i = 1; i < rows; i++) {
			csv.get(i)[index] = reverseMap.get(csv.get(i)[index]);
		}

		for (int i = 0; i < rows; i++) {
			System.out.println(Arrays.asList(csv.get(i)));
		}
		} catch (IOException e){
			System.err.println(e.getMessage());
		}

	}

	/**
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	private static List<String[]> readFromCsv(String file) throws IOException {
		List<String[]> arrayToBeWritten = new ArrayList<>();
		try (Reader reader = Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8);
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).build();
		) {
			 arrayToBeWritten = csvReader.readAll();
		} catch (IOException e) {
			System.out.println("Please make sure both files exist");
			throw e; 
		}
		return arrayToBeWritten;
	}

	public static class StringArrayComparator implements Comparator<List<String>> {

		int index = 1;

		public StringArrayComparator(int index) {
			this.index = index;
		}
		
		@Override
		public int compare(List<String> o1, List<String> o2) {
			return o1.get(index).compareTo(o2.get(index));
		}

	}

}
