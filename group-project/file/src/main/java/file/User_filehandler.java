package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;

public class User_filehandler {
  public Hashtable<String, String> userinfo = new Hashtable<String, String>();
  private String filePath = Path.of(System.getProperty("user.home")).toString();

  public void writeUserinfo(String username, String password, String file) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(username + "," + password);
      FileWriter filewriter = new FileWriter(new File(filePath + file), true);
      filewriter.write(sb.toString() + "\n");
      filewriter.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<String> infoList(String file) {
    ArrayList<String> listOfLines = new ArrayList<>();
    try (BufferedReader bufReader = new BufferedReader(
        new FileReader(new File(filePath + file)))) {
      
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
    return listOfLines;
  }

  public Hashtable<String, String> getUserinfo(String file) {
    ArrayList<String> listOfLines = infoList(file);
    for (int i = 0; i < listOfLines.size(); i++) {
      String[] split = listOfLines.get(i).split(",");
      userinfo.put(split[0], split[1]);
    }
    return userinfo;
  }
}
