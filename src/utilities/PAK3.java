package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import main.ETConsole;

public class PAK3 {
	
	private static Map<String, ArrayList<ZipEntry>> pakList = new HashMap<String, ArrayList<ZipEntry>>();
	
	private static void load(ArrayList<ZipEntry> entryList, String filePath, ETConsole console) throws IOException {
		if(filePath.contains(".") && !filePath.split("\\.")[1].equals("pk3"))
			return;
		
		ZipInputStream stream = new ZipInputStream(new FileInputStream(filePath));
		ZipEntry entry = stream.getNextEntry();
		
		Date date = new Date();
		
		console.print(DateExtender.leadingZeros(date.getHours()) + ":" + DateExtender.leadingZeros(date.getMinutes()) + ":" + DateExtender.leadingZeros(date.getSeconds()) + " - Loading " + filePath + "...");
		
		while(entry != null) {
			entryList.add(entry);
			entry = stream.getNextEntry();
		}
		
		stream.closeEntry();
		stream.close();
		
		console.println(" complete.");
		pakList.put(filePath, entryList);
	}
	
	public static void loadFiles(ETConsole console) {
		console.println("Loading PAK3 files...");

		try {
			Files.walk(Paths.get("jetmain/"))
					.forEach(
							filePath -> {
								if (!Files.isRegularFile(filePath))
									return;

								ArrayList<ZipEntry> entryList = new ArrayList<ZipEntry>();
								
								try {
									PAK3.load(entryList, filePath.toString(), console);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, ArrayList<ZipEntry>> getPakList() {
		return pakList;
	}
	
}
