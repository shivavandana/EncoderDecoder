/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.io.Reader;

/**
 *
 * @author shivavandana
 */
public class Decoder {
        //intialise the variables used in the program
	private static short bitLength;
	private static String fileName = null;
	private static Reader reader = null;
	private static PrintWriter pwriter = null;
	private static int tableMaxLength = 0;
        private static Map<Integer, String> mapping = null;

	public static void main(String[] args) throws IOException {
		// map the ASCII values that are only between 0 and 255
		mapping = new HashMap<Integer, String>();
		for (int ascii = 0; ascii <= 255; ascii++) {
			mapping.put((Integer) ascii, (char) ascii + "");
		}
		try {
		        //the input is given along with the command line java Decoder.java input.lzw 12
                        //this input .lzw is taken as the fileName
                        fileName = args[0];
                        //the second argument is taken as the bitlength. In this case, it is 12.
			bitLength = Short.parseShort(args[1]);
                        //the table length is taken as the 2 power bitlength
			tableMaxLength = (int) Math.pow(2, bitLength);
		} catch (Exception e) {
			System.out.println("Pass the correct input to decode");
			System.exit(0);
		}
		try {
		        //initialise the printWriter object to write the utf 8 format to the output file 
                        //In this example input1_decoded.txt is created the decoded binary format is written into it
			pwriter = new PrintWriter(fileName.substring(0, fileName.lastIndexOf('.')) + "_decoded.txt", "UTF-8");
                        //read the input from the .lzw file which is the binary format
			reader = new InputStreamReader(new FileInputStream(fileName), "UTF-16BE");
			int code = reader.read();
			// Initialization of str and current_str variables
			String str = mapping.get(code);
			String current_str = null;
			System.out.println(str);
			// write to the output file
			pwriter.write(str);
			int size = 0;
			// Code to decode the binary format to the human readable format
			while ((code = reader.read()) != -1) {
				if (mapping.get(code) == null) {
					current_str = str + str.charAt(0);
				} else {
					current_str = mapping.get(code);
				}
				System.out.println(current_str);
				// printwriter to write string to the output file
				pwriter.write(current_str);
				if ((size = mapping.size()) < tableMaxLength) {
					mapping.put(size, str + current_str.charAt(0));
				}
				str = current_str;
			}
			pwriter.close();
		} catch (Exception e) {
			System.out.println("The input file should be in UTF-8 format");
		}
	}
    
}
