package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClassSchedule {
    
    private final String CSV_FILENAME = "jsu_sp24_v1.csv";
    private final String JSON_FILENAME = "jsu_sp24_v1.json";
    
    private final String CRN_COL_HEADER = "crn";
    private final String SUBJECT_COL_HEADER = "subject";
    private final String NUM_COL_HEADER = "num";
    private final String DESCRIPTION_COL_HEADER = "description";
    private final String SECTION_COL_HEADER = "section";
    private final String TYPE_COL_HEADER = "type";
    private final String CREDITS_COL_HEADER = "credits";
    private final String START_COL_HEADER = "start";
    private final String END_COL_HEADER = "end";
    private final String DAYS_COL_HEADER = "days";
    private final String WHERE_COL_HEADER = "where";
    private final String SCHEDULE_COL_HEADER = "schedule";
    private final String INSTRUCTOR_COL_HEADER = "instructor";
    private final String SUBJECTID_COL_HEADER = "subjectid";
    
    public String convertCsvToJsonString(List<String[]> csv) {
        
       JsonArray jsonArray = new JsonArray();
       JsonArray Array= new JsonArray(); 
       
       
           Iterator<String[]> iterator = csv.iterator();
        String[] headerRow = iterator.next();

        HashMap<String, ArrayList<String>> headers = new HashMap<>();

        // Initialize ArrayLists for each header
        for (String header : headerRow) {
            headers.put(header, new ArrayList<>());
        }

        // Populate the HashMap with data from CSV
        while (iterator.hasNext()) {
            String[] record = iterator.next();

            for (int i = 0; i < headerRow.length; i++) {
                headers.get(headerRow[i]).add(record[i]);
            }
        }

        // Print the data under each column
        for (int i = 0; i < headerRow.length; i++){
            String header = headerRow[i];
            System.out.println("Data under header " + header + ": " + headers.get(header));
        }
       
       for(String[]row:csv){
            
           JsonObject jsonObject = new JsonObject();
           
           JsonObject sObject= new JsonObject();
              //sObject.put(TYPE_COL_HEADER, SCHEDULE_COL_HEADER);
           
           
           JsonObject scheduletype = new JsonObject();
            //scheduletype.put(TYPE_COL_HEADER,SCHEDULE_COL_HEADER);
           
           JsonObject subject = new JsonObject();
           
          // hash map
          HashMap<String, String> subjectpair = new HashMap<>();
          ArrayList<String>oSubject = headers.get(SUBJECTID_COL_HEADER);
          ArrayList<String> number = headers.get(NUM_COL_HEADER);
          ArrayList<String> subjectID = new ArrayList<>();
          ArrayList<String> num = new ArrayList<>();
          
          for(String str : number){
              String[] splitnum = str.split(" ");
              
              int halfLength = splitnum.length / 2;
              
              for(int i=0; i < halfLength; i++){
                  subjectID.add(splitnum[i]);
              }
              for(int i = halfLength; i < splitnum.length; i++){
                   num.add(splitnum[i]);
             }
          }
          HashMap<String, String> tempMap = new HashMap<>();
          
          for(int i = 0; i<subjectID.size(); i++){
              String key = subjectID.get(i);
              String value = (String) subject.get(i);
              
              if (!tempMap.containsKey(key)){
                  subjectpair.put(key, value);
                  
                  tempMap.put(key, value);
              
              }
          }
          for(String key : subjectpair.keySet()){
              subject.put(key, subjectpair.get(key));
          }
           
            //bject.put(SUBJECTID_COL_HEADER,SUBJECTID_COL_HEADER);
                
            //String[] numSplit = num.split("num");
            
           //String[] logo = NUM_COL_HEADER.split(" ");
           //subject.put(numberSplit[0],numberSplit[1]);
                
           JsonObject course = new JsonObject();
           
           
           
           
               //ourse.put(numberSplit[0], numberSplit[1]);
                //course.put(NUM_COL_HEADER, logo);
                //course.put(NUM_COL_HEADER, number);
                //course.put(SUBJECTID_COL_HEADER,row[2]);
                
           
           HashMap<String, HashMap<String, String>>ocourse = new HashMap<>();
           ArrayList<String> description = headers.get(DESCRIPTION_COL_HEADER);
           ArrayList<String>total_credit = headers.get(CREDITS_COL_HEADER);
           
           
               
           for(int i =0;i < description.size();i++){
               String key = NUM_COL_HEADER;
               HashMap<String, String> InsideMap = new HashMap<>();
               
               InsideMap.put(DESCRIPTION_COL_HEADER , description.get(i));
               InsideMap.put(CREDITS_COL_HEADER , total_credit.get(i));
               InsideMap.put("subjectid", subjectID.get(i));
               InsideMap.put("num",num.get(i));
               course.put(key, InsideMap);
               }
           for(String key : course.keySet()){
               course.put(key, course.get(key));
           }
           JsonObject section = new JsonObject();
                section.put(CRN_COL_HEADER, CRN_COL_HEADER);
                //section.put(NUM_COL_HEADER, logo);
          //    course.put(numberSplit[0], numberSplit[1]);
                section.put(NUM_COL_HEADER, number);
                section.put(SECTION_COL_HEADER,SECTION_COL_HEADER);
                section.put(TYPE_COL_HEADER,TYPE_COL_HEADER);
                section.put(START_COL_HEADER,START_COL_HEADER);
                section.put(END_COL_HEADER,END_COL_HEADER);
                section.put(DAYS_COL_HEADER,DAYS_COL_HEADER);
                section.put(WHERE_COL_HEADER,WHERE_COL_HEADER);
                section.put(INSTRUCTOR_COL_HEADER,INSTRUCTOR_COL_HEADER);
           
           
           
           /*
           jsonObject.put(SUBJECT_COL_HEADER,row[1]);
           subject.put(NUM_COL_HEADER,row[2]);
           jsonObject.put(SCHEDULE_COL_HEADER,row[11]);
           */
           
           
          
       
           
           jsonArray.add(jsonObject);
       }return jsonArray.toString();
    }
    public String convertJsonToCsvString(JsonObject json) {
        
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        
        // headers
        String[]headers = {CRN_COL_HEADER, SUBJECT_COL_HEADER , NUM_COL_HEADER,DESCRIPTION_COL_HEADER,
                            SECTION_COL_HEADER, TYPE_COL_HEADER, CREDITS_COL_HEADER, START_COL_HEADER, 
                            END_COL_HEADER, DAYS_COL_HEADER, WHERE_COL_HEADER, SCHEDULE_COL_HEADER, 
                            INSTRUCTOR_COL_HEADER, SUBJECTID_COL_HEADER,};
        csvWriter.writeNext(headers);
        
        for (Object key: json.keySet()){
            JsonObject sObject = (JsonObject)json.get(key);
            if(sObject != null){
                
            
            String[]row = new String[headers.length];
            
            var number = NUM_COL_HEADER;
            String[] numberSplit = NUM_COL_HEADER.split(" ");
            
            
            row[0]=(String)key;
            row[1]= (String)sObject.get(TYPE_COL_HEADER);
            row[2]= (String)sObject.get(numberSplit[0]);
            row[2]= (String)sObject.get(numberSplit[1]);
            row[3]= (String)sObject.get(DESCRIPTION_COL_HEADER);        
            row[4]= (String)sObject.get(SECTION_COL_HEADER);        
            row[5]= (String)sObject.get(TYPE_COL_HEADER);        
            row[6]= (String)sObject.get(CREDITS_COL_HEADER);
            row[7]= (String)sObject.get(START_COL_HEADER);
            row[8]= (String)sObject.get(END_COL_HEADER);
            row[9]= (String)sObject.get(DAYS_COL_HEADER);
            row[10]=(String)sObject.get(WHERE_COL_HEADER);
            row[11]=(String)sObject.get(SCHEDULE_COL_HEADER);
            row[12]=(String)sObject.get(INSTRUCTOR_COL_HEADER);
            //row[13]=sObject.get(SUBJECT_COL_HEADER).toString();
            
            csvWriter.writeNext(row);   
        }
            
            else{
                System.out.println("value-key is null");
            }
        }
            try{
                csvWriter.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
       return stringWriter.toString();
        
    }
    
    public JsonObject getJson() {
        
        JsonObject json = getJson(getInputFileData(JSON_FILENAME));
        return json;
        
    }
    
    public JsonObject getJson(String input) {
        
        JsonObject json = null;
        
        try {
            json = (JsonObject)Jsoner.deserialize(input);
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return json;
        
    }
    
    public List<String[]> getCsv() {
        
        List<String[]> csv = getCsv(getInputFileData(CSV_FILENAME));
        return csv;
        
    }
    
    public List<String[]> getCsv(String input) {
        
        List<String[]> csv = null;
        
        
        try {
            
            CSVReader reader = new CSVReaderBuilder(new StringReader(input)).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            csv = reader.readAll();
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return csv;
        
    }
    
    public String getCsvString(List<String[]> csv) {
        
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '\t', '"', '\\', "\n");
        
        csvWriter.writeAll(csv);
        
        return writer.toString();
        
    }
    
    private String getInputFileData(String filename) {
        
        StringBuilder buffer = new StringBuilder();
        String line;
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        try {
        
            BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("resources" + File.separator + filename)));

            while((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            
        }
        catch (Exception e) { e.printStackTrace(); } 
        
        return buffer.toString();
        
    }
    
}