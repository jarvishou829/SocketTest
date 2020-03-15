package test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static String regisinfo;
    public static int regiscode;
    public static void main(String args, String args2) {
        try {
            String usrname = args;
            String passwrd = args2;
            //1.创建客户端Socket，指定服务器地址和端口
            Socket so=new Socket("localhost", 8880);//端口号要和服务器端相同
            //2.获取输出流，向服务器端发送登录的信息
            OutputStream os=so.getOutputStream();//字节输出流
            PrintWriter pw=new PrintWriter(os);//字符输出流
            BufferedWriter bw=new BufferedWriter(pw);//加上缓冲流
            bw.write(usrname+" "+passwrd);
            bw.flush();
            so.shutdownOutput();//关闭输出流
            //3.获取输入流，得到服务端的响应信息
            InputStream is=so.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            while((regisinfo=br.readLine())!=null){
                System.out.println("我是客户端，服务器说:"+regisinfo);
                regiscode = Integer.parseInt(regisinfo);
            }
            //4.关闭资源
            bw.close();
            pw.close();
            os.close();
            so.close();
        } catch (java.io.IOException e) {
            System.out.println("无法连接 ");
            System.out.println(e);
        }
    }
}