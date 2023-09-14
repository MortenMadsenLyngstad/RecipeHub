package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

public class User_filehandler {
    public Hashtable<String, String> userinfo = new Hashtable<String, String>();
    private static Path path = Paths.get("core", "src", "main", "java", "textfiles", "userinfo.csv");


    public void writeUserinfo(String username, String password) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(username + "," + password);
      FileWriter filewriter = new FileWriter(path.toFile(), true);
      filewriter.write(sb.toString() + "\n");
      filewriter.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<String> infoList() {
    try (BufferedReader bufReader = new BufferedReader(new FileReader(path.toFile()))) {
      ArrayList<String> listOfLines = new ArrayList<>();

      String line = bufReader.readLine();
      while (line != null) {
        listOfLines.add(line);
        line = bufReader.readLine();
      }
      bufReader.close();
      for (int i = 0; i < listOfLines.size(); i++) {
        listOfLines.get(i).split(",");
      }
      return listOfLines;
      
    } catch (IOException e) {
      System.out.println("Error reading file");
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Hashtable<String, String> getUserinfo() {
    ArrayList<String> infoList = infoList();
    for (int i = 0; i < infoList.size(); i++) {
      String[] split = infoList.get(i).split(",");
      userinfo.put(split[0], split[1]);
    }
    return userinfo;
  }
}

