package dev.mvc.spice;

import java.io.File;

public class Spice {
  /** 페이지당 출력할 레코드 갯수 */
  public static int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public static int page_per_blocK = 10;

  public static synchronized String getUploadDir() {
    String path = "";
    if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
        // path = "C:/kd/deploy/team3_v2sbm3c/brereply/storage/";
        path="C:\\kd\\deploy\\team3_v2sbm3c\\spice\\storage\\";
        // System.out.println("Windows 10: " + path);
        
    } else { // Linux, AWS, 서비스용 배치 폴더 
        // System.out.println("Linux");
        path = "/home/ubuntu/deploy/team3_v2sbm3c/spice/storage/";
    }
    
    return path;
  }
}
