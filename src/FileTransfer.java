import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class FileTransfer {

	public static void main(String[] args ) {
		
		//set origin file
		
		File fromFolder = new File("src/FolderA/");
		String originFolder = fromFolder.toString();
		Path origin = fromFolder.toPath();
		File toFolder = new File("src/FolderB/");
		String destinationFolder = toFolder.toString();
		Path destination = toFolder.toPath();
		
		//determine the current unix time
		long currentTime = Instant.now().getEpochSecond();
		//determine what 24 hours prior to the current time is in unix
		long twentyFour = currentTime - 86400;
		
		//create an array for all the files in the originFolder and print out the names of the files and the last time they were modified
		File[] originFiles = fromFolder.listFiles();
		for (File file: originFiles) {
			String fileName = file.getName();
			//continues on with process ignoring hidden files
			if (fileName.charAt(0) == '.') {
				continue;
			}
			String originPath = originFolder + "/" + fileName;
			String destinationPath = destinationFolder + "/" + fileName;
			Path originalFiles = Paths.get(originPath);
			Path destinationFiles = Paths.get(destinationPath);
			Long fileTime = file.lastModified();
			Long unixTimestamp = fileTime/1000L;
			//determine what files are eligible for transfer
			if (unixTimestamp > twentyFour) {
				System.out.print(fileName + " was modified within the last 24 hours");
				try {
					Files.copy(originalFiles, destinationFiles);
					System.out.println(" and copied to folder B");
				} catch (IOException e) {
					System.out.println("An I/O error occurred");
					e.printStackTrace();
				}	
			}
			else {
				System.out.println(fileName + " was not modified within the last 24 hours.");
			}
		}
	}
}
