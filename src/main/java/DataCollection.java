import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataCollection {
	
	static Map<String,ArrayList<String>> datamap = new TreeMap<String,ArrayList<String>>();
	public static void readPerformanceData(){
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("performance-data.xlsx"));
            
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                ArrayList<String> datalist = new ArrayList<String>();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                String schoolname = cell.getStringCellValue();
                while(cellIterator.hasNext()){
                	Cell cell2 = cellIterator.next();
                	switch (cell2.getCellType()) 
                	{              	   
                	case Cell.CELL_TYPE_NUMERIC:
                		datalist.add(String.valueOf((int) cell2.getNumericCellValue()));
                		break;
                	case Cell.CELL_TYPE_STRING:
                		datalist.add(cell2.getStringCellValue());
                		break;
                	}
                }
                datamap.put(schoolname,datalist);
            }
            file.close();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
    }
	}

	static void readGeolocationData(){
		{
			try
			{
				FileInputStream file = new FileInputStream(new File("location-data.xlsx"));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) 
				{
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell = cellIterator.next();
					String schoolname = cell.getStringCellValue();
					for(String key : datamap.keySet()){
						if (key.contains(schoolname)){
							while(cellIterator.hasNext()){
								Cell cell2 = cellIterator.next();
								switch (cell2.getCellType()) 
								{              	   
								case Cell.CELL_TYPE_NUMERIC:
									datamap.get(key).add(String.valueOf((double) cell2.getNumericCellValue()));
									break;
								case Cell.CELL_TYPE_STRING:
									datamap.get(key).add(cell2.getStringCellValue());
									break;
								}
							}
						}
					}
					
				}
				file.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		Iterator<ArrayList<String>> it = datamap.values().iterator();
			while(it.hasNext()){
				if(it.next().size()<3)
					it.remove();
			}
			
			Iterator<ArrayList<String>> it2 = datamap.values().iterator();
			while(it2.hasNext()){
				it2.next().toArray(new String[0]);
			}
		
	}

	public static void main(String[] args) 
    {
		readPerformanceData();
		readGeolocationData();
		System.out.print("[");
		for(String key:datamap.keySet()){
			String s = datamap.get(key).get(0);
			//System.out.print("\"");
			System.out.print(s);
			//System.out.print("\"");
            System.out.print(", ");
		}
		
		System.out.print("]");
    }
	
    
}
