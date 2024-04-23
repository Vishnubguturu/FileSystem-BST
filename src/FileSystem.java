import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    public FileSystem() {
    	nameTree = new BST<String, FileData>();
    	dateTree = new BST<String, ArrayList<FileData>>();
    }

    public FileSystem(String inputFile) {
    	new FileSystem();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                add(data[0], data[1], data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    /*
     * creates a new file with the given arguments and adds it to the filesystem.
     * @param name
     * @param dir
     * @param date
     * @return void
     */
    public void add(String name, String dir, String date) {
    	if(name == null || date == null) {
    		return;
    	}
    	FileData fileData = new FileData(name, dir, date);
    	if(nameTree.containsKey(name)) {
    		if(nameTree.get(name).lastModifiedDate.equals(date)) {
    			return;
    		}
    		else {
    			String lastDate = nameTree.get(name).lastModifiedDate;
    			if(lastDate.compareTo(date) < 0) {
    				nameTree.replace(name, fileData);
    				ArrayList<FileData> dList = dateTree.get(lastDate);
        			for(FileData file: dList) {
        				if(file.name.equals(name)) {
        					dList.remove(file);
        				}
        			}
        			if(dateTree.containsKey(date)) {
        				dateTree.get(date).add(fileData);
        			}
        			else {
        				dateTree.set(date, new ArrayList<FileData>(Arrays.asList(fileData)));
        			}
    			}
    			else {
    				return;
    			}
    		}
    	}
    	else {
    		nameTree.set(name, fileData);
    		if(dateTree.containsKey(date)) {
    			dateTree.get(date).add(fileData);
    		}
    		else {
    			dateTree.set(date, new ArrayList<FileData>(Arrays.asList(fileData)));
    		}
    	}
    }


    /*
     * finds all the file names corresponding to the given date.
     * @param date
     * @return a list of all the names.
     */
    public ArrayList<String> findFileNamesByDate(String date) {
    	if(date == null) {
    		return null;
    	}
    	ArrayList<FileData> dList = dateTree.get(date);
    	ArrayList<String> namesList = new ArrayList<>();
    	for(FileData file: dList) {
    		namesList.add(file.name);
    	}
    	return namesList;
    }

    /*
     * filters all the files within the given dates.
     * @param startDate
     * @param endDate
     * @return a filesystem with all the files in the given range.
     */
    public FileSystem filter(String startDate, String endDate) {
    	FileSystem system = new FileSystem();
    	List<String> datesList = dateTree.keys();
    	List<String> datesInRange = new ArrayList<>();
    	for(String date: datesList) {
    		if(date.compareTo(startDate) > 0 && date.compareTo(endDate) < 0) {
    			datesInRange.add(date);
    		}
    	}
    	for(String date: datesInRange) {
    		system.dateTree.set(date, dateTree.get(date));
    		for(FileData file: dateTree.get(date)) {
    			system.nameTree.set(file.name, file);
    		}
    	}
    	return system;
    }
    
    /*
     * filters all the files that contain the given string.
     * @param wildCard
     * @return a filesystem with all the files that contain the given string.
     */
    public FileSystem filter(String wildCard) {
    	FileSystem system = new FileSystem();
    	List<String> namesList = nameTree.keys();
    	List<String> namesInRange = new ArrayList<>();
    	for(String name: namesList) {
    		if(name.contains(wildCard)) {
    			namesInRange.add(name);
    		}
    	}
    	for(String name: namesInRange) {
    		system.nameTree.set(name, nameTree.get(name));
    		ArrayList<FileData> files = dateTree.get(nameTree.get(name).lastModifiedDate);
    		for(FileData file: files) {
    			if(file.name.equals(name)) {
    				if(system.dateTree.containsKey(file.lastModifiedDate)) {
    					ArrayList<FileData> datesList = system.dateTree.get(file.lastModifiedDate);
    					datesList.add(file);
    				}
    				else {
    					ArrayList<FileData> datesList = new ArrayList<>();
    					datesList.add(file);
    					system.dateTree.set(file.lastModifiedDate, datesList);
    				}
    			}
    		}
    	}
    	return system;
    }
    
    /*
     * @returns a list of all the files from nameTree in alphabetical order in the form of a string.
     */
    public List<String> outputNameTree(){
    	List<String> list = new ArrayList<>();
    	List<String> namesList = nameTree.keys();
    	for(String name: namesList) {
    		String output = name + ": " + nameTree.get(name).toString();
    		list.add(output);
    	}
    	return list;
    }
    
    /*
     * @returns a list of all the files from dateTree in ascending order in the form of a string.
     */
    public List<String> outputDateTree(){
    	List<String> list = new ArrayList<>();
    	List<String> datesList = dateTree.keys();
    	for(int i = datesList.size() - 1; i >= 0; i--) {
    		String date = datesList.get(i);
    		for(int j = dateTree.get(date).size() - 1; j >= 0; j--) {
    			String output = date + ": " + dateTree.get(date).get(j).toString();
    			list.add(output);
    		}
    	}
    	return list;
    }
}
