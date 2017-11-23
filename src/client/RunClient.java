package client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class RunClient {
	public static void main(String[] args) {
		String total ="";
		RESTClient client = new RESTClient();
		client.callInit();
		total+="--- Step 3.1 ---\r\n";
		total+=client.step1();
		
		total+="--- Step 3.2 ---\r\n";
		total+=client.step2();

		total+="--- Step 3.3 ---\r\n";
		total+=client.step3();

		total+="--- Step 3.4 ---\r\n";
		total+=client.step4();

		total+="--- Step 3.5 ---\r\n";
		total+=client.step5();

		total+="--- Step 3.6 ---\r\n";
		total+=client.step6();

		total+="--- Step 3.7 ---\r\n";
		total+=client.step7();

		total+="--- Step 3.8 ---\r\n";
		total+=client.step8();

		total+="--- Step 3.9 ---\r\n";
		total+=client.step9();

		total+="--- Step 3.10 ---\r\n";
		total+=client.step10();

		total+="--- Step 3.11 ---\r\n";
		total+=client.step11("2017-12-30-10:30","2017-12-25-08:36");
		
		try {
			PrintWriter writer = new PrintWriter("client-server.log", "UTF-8");
			String[] lines = total.split(System.getProperty("line.separator"));
			for(String line : lines) {
				line = line.replaceAll(">\n ", ">\r\n");
				writer.println(line);
				}
			writer.close();		
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
}
