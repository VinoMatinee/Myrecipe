package dev.mvc.brereply;

import java.io.File;

public class Brereply {
  public static synchronized String getUploadDir() {
    String path = "";
    if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
        // path = "C:/kd/deploy/team3_v2sbm3c/brereply/storage/";
        path="C:\\kd\\deploy\\team3_v2sbm3c\\brereply\\storage\\";
        // System.out.println("Windows 10: " + path);
        
    } else { // Linux, AWS, 서비스용 배치 폴더 
        // System.out.println("Linux");
        path = "/home/ubuntu/deploy/team3_v2sbm3c/brereply/storage/";
    }
    
    return path;
  }
}
