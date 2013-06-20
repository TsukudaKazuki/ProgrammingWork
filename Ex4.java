import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;

public class Ex4 {
	public static void main(String[] args)throws IOException{
		ServerSocket serverS = null;
		Socket clientS = null;
		try{serverS = new ServerSocket(50000);//接続要求を持つソケット生成
		}catch(IOException e){
			System.out.println("ポート番号にアクセス出来ません。");
			System.exit(1);
		}
		try{clientS = serverS.accept();
		}catch(IOException e){
			System.out.println("acceptに失敗しました。");
			System.exit(1);
		}

        PrintWriter out = new PrintWriter(clientS.getOutputStream(),true);
        Date now = new Date();
        Format fmt= DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG);   //　日時を取得する


        out.println(fmt.format(now));   // 日時を表示する
        out.close();clientS.close();serverS.close();

	}

}