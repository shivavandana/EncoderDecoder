/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;


/**
 *
 * @author shivavandana
 */


public class Encoder {

    /**
     * @param args the command line arguments
     */
   private static Map<String, Integer> mapping = null;
	private static short bitLength;
	private static String fileName = null;
	private static Reader reader = null;
	private static int tableMaxLength = 0;

	public static void main(String[] args) {
		int ch;
		Writer writer = null;
		String str = "";
		String symbol = "";

		try {
			//the input is given along with the command line java Encoder.java input.txt 12
                        //this input .txt is taken as the fileName
			fileName = args[0];
                        //In this case, the argument 12 in the above command line is taken as the bitLength
			bitLength = Short.parseShort(args[1]);
                        //The table length is initialized using the input parameter.In this example it is 2 power 12.
			tableMaxLength = (int) Math.pow(2, bitLength);
		} catch (Exception e) {
			System.out.println("Pass the correct input file and the bitlength as the arguments");
			System.exit(0);
		}
		// map the ASCII values that are only between 0 and 255
		mapping = new HashMap<String, Integer>();
		for (int ascii = 0; ascii <= 255; ascii++) {
			mapping.put((char) ascii + "", (Integer) ascii);
		}
		try {
			// Output stream Writer to write binary format to file (.lzw extension)
			writer = new OutputStreamWriter(
					new FileOutputStream(fileName.substring(0, fileName.lastIndexOf('.')) + ".lzw"), "UTF-16BE");
                        //read the values from the input file which is in the format UTF-8
			reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8");

			// Code to encode
                        //this code is used to get the symbol from the input file and try to validate it with the ASCII values mapping
			while ((ch = reader.read()) != -1) {
				symbol = (char) ch + "";
				if (mapping.containsKey(str + symbol)) {
					str += symbol;
				} else {
					System.out.println((Integer) mapping.get(str));
					writer.write((Integer) mapping.get(str));
					if (mapping.size() < Math.pow(2, bitLength)) {
						mapping.put(str + symbol, mapping.size());
					}
					str = symbol;
				}
			}
                        //get the value that matches with the input character in the table to the corresponding ASCII values
			System.out.println((Integer) mapping.get(str));
                        //after fething the corresponding ASCII value, write it into the output .lzw file
			writer.write((Integer) mapping.get(str));
			writer.close();
		} catch (Exception e) {
			System.out.println("The input should be in UTF-8 format");
		}
	}
    
}

