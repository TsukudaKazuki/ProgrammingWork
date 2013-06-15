import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Ex42 {
	public static void main(String[] args)throws IOException{
		Socket dateS = null; //ソケット
		BufferedReader in = null;//ソケットからの入力
		PrintWriter out = null;//ソケットへの出力
		try{
			dateS = new Socket(InetAddress.getLocalHost(),50000);
			in = new BufferedReader(new InputStreamReader(dateS.getInputStream()));
			out = new PrintWriter(dateS.getOutputStream(),true);
		}catch(UnknownHostException e){
			System.out.println("ホストに接続出来ません。");
			System.exit(1);
		}catch(IOException e){
			System.out.println("IOコネクションを得られません。");
			System.exit(1);
		}
		/*BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));*/
        String fromServer;
        /*String fromUser;*/
        fromServer = in.readLine();
        System.out.println(fromServer);//応答を表示
        out.close(); in.close();/*stdIn.close();*/dateS.close();
	}
}
