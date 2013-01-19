/*

Written by: Abishek K, AECOM
Program to change mode numbers in a Trnbuild transit line file  ( for TBRPM 7 Model)
Algorithmically it is just a search, replace program

*/

import java.io.*;
import java.util.*;
import java.util.regex.*; 

public class TrnBuild2PT_v4 {
 
	static String controlfile; 
	static String filenamein, filenameout, formatfile;
	int linecount=0;
	String [][] libConvert;
	//String [][] libConvert = new String[12][2]; // Library of the search & replace  items to convert the mode numbers to the new system
	
 
	public static void main(String[] args) throws Exception {
        controlfile = args[0];
		TrnBuild2PT_v4 app = new TrnBuild2PT_v4();
        app.readCtlFile();
		app.sizeLib();
		app.buildLib();
		app.convertInputFile();
	}
 
 
	 public void readCtlFile() throws IOException {
	 
		String filenamectl = controlfile;
		BufferedReader br0 = new BufferedReader(new FileReader(new File(filenamectl)));
		int tokenCount;
		String line0 = ""; 							// variable to store text from file
		
		while ( (line0 = br0.readLine()) != null ){
			StringTokenizer st = new StringTokenizer(line0,":",false);
			tokenCount = st.countTokens();			//System.out.println("Number of tokens = " + tokenCount);
			String temp="";
			while (st.hasMoreTokens()){ 			// make sure there is stuff to get
				temp = st.nextToken();
				if(temp.equals("Trn2PT_InputFile")){
					filenamein = st.nextToken();
				}
				if(temp.equals("Trn2PT_OutFile")){
					filenameout = st.nextToken();
				}
				if(temp.equals("FormatFile")){
					formatfile = st.nextToken();
				}
			}
		}
	 }

	 
	 
		public void sizeLib() throws IOException {
	 
			BufferedReader br1 = new BufferedReader(new FileReader(new File(formatfile)));
			String line1 = ""; 							
						
			while ((line1 = br1.readLine()) != null) {
				linecount++;
				//System.out.println(line1);
			}
			libConvert = new String[linecount+1][2]; // Library of the search & replace  items to convert the mode numbers to the new system
 		}
	 
	 
	 
	  public void buildLib() throws IOException {
	 
		BufferedReader br2 = new BufferedReader(new FileReader(new File(formatfile)));
		int tokenCount;
		String line2 = ""; 							// variable to store text from file
		int j=0;
		
		while ( (line2 = br2.readLine()) != null ){
			StringTokenizer st2 = new StringTokenizer(line2,":",false);
			tokenCount = st2.countTokens();			//System.out.println("Number of tokens = " + tokenCount);
			String temp="";
			
			while (st2.hasMoreTokens()){ 			// make sure there is stuff to get
				if(j<libConvert.length){
					//System.out.println("J: "+j);
					//System.out.println("liblength: "+libConvert.length);
					
					libConvert[j][0] = st2.nextToken();
					//System.out.println(libConvert[j][0]);
					libConvert[j][1] = st2.nextToken();
					//System.out.println(libConvert[j][1]);
					
				}
			}
			j++;
		}
		libConvert[libConvert.length-1][0] = "MODEE";
		libConvert[libConvert.length-1][1] = "MODE";
	 } 
	 
	 
	 public void convertInputFile() throws IOException {
		
		String outputStr = "";
		BufferedReader br = new BufferedReader(new FileReader(new File(filenamein)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(filenameout)));
		String line = ""; 							// variable to store text from file
		Pattern pattern;
		Matcher matcher;
	
			while ( (line = br.readLine()) != null ){
					outputStr=line;
					for(int i=0;i<libConvert.length;i++){
						// Compile regular expression
						//System.out.println(libConvert[i][0]);
						pattern = Pattern.compile(libConvert[i][0]);
						// Replace all occurrences of pattern in input
						//System.out.println(libConvert[i][1]);
				        matcher = pattern.matcher(outputStr);
				        outputStr = matcher.replaceAll(libConvert[i][1]);
					}
					pw.printf(outputStr);
					pw.println();
			}
		pw.flush();
		pw.close();
	}
}

