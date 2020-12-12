package com.LZNeko.perpusktg;	

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.net.*;

public class KoneksiActivity {

   //mendapatkan nilai bit yg diperlukan
   public String call(String url){
       int BUFFER_SIZE = 2000;
       InputStream in = null;
       try{
           in = openHttpConnection(url);
       } catch (IOException e) {
           e.printStackTrace();
           return "";
       }
       //membaca nilai bit menjadi nilai karakter
       InputStreamReader inputStreamReader = new InputStreamReader(in);
       int charRead;
       String string = "";
       char[] inputBuffer = new char[BUFFER_SIZE];
       try{
           while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
               String readString = String.copyValueOf(inputBuffer,0,charRead);
               string += readString;
               inputBuffer = new char[BUFFER_SIZE];
           }
           in.close();
       } catch (IOException e) {
           e.printStackTrace();
           return "";
       }
       return string;
}
//membuka URL dan meminta respon dari input streamreader
private InputStream openHttpConnection(String url) throws IOException {
       InputStream in = null;
       int response = -1;
       URL url1 = new URL(url);
       URLConnection conn = url1.openConnection();
       if(!(conn instanceof HttpURLConnection)) throw new IOException("Not An Http Connection");
       try{
               HttpURLConnection httpurlconnection = (HttpURLConnection) conn;
               httpurlconnection.setAllowUserInteraction(false);
               httpurlconnection.setInstanceFollowRedirects(true);
               httpurlconnection.setRequestMethod("GET");
               httpurlconnection.connect();
               response = httpurlconnection.getResponseCode();
               if(response == HttpURLConnection.HTTP_OK) {
                   in = httpurlconnection.getInputStream();
               }
       } catch (Exception e) {
                   // TODO: handle exception
                   throw new IOException("Error Connecting");
               }
               return in;
       }
}