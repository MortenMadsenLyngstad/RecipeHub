package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;

public class UserFilehandler {
  public Hashtable<String, String> userinfo = new Hashtable<String, String>();
  private String filePath = Path.of(System.getProperty("user.home")).toString() + "/userinfo.csv";

  /**
   * Writes the username and password to a file
   * @param username
   * @param password
   */
  public void writeUserinfo(String username, String password) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(username + "," + password);
      FileWriter filewriter = new FileWriter(new File(filePath), true);
      filewriter.write(sb.toString() + "\n");
      filewriter.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      System.out.println(e.getMessage());
    }
  }

    /**
    * Reads the file and splits it into an arraylist of strings, which are then split into an arraylist of arraylists of strings. 
    * This makes it easier to access the data in the file. 
    * @return an arraylist of arraylists of strings or an empty arraylist if file isn't read
    */
  public ArrayList<String> infoList() {
    ArrayList<String> listOfLines = new ArrayList<>();
    try (BufferedReader bufReader = new BufferedReader(
        new FileReader(new File(filePath)))) {
      
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

  /**
   * Gets the userinfo from the file and puts it into a hashtable
   * @return a hashtable with the userinfo
   */
  public Hashtable<String, String> getUserinfo() {
    ArrayList<String> listOfLines = infoList();
    for (int i = 0; i < listOfLines.size(); i++) {
      String[] split = listOfLines.get(i).split(",");
      userinfo.put(split[0], split[1]);
    }
    return userinfo;
  }
}
