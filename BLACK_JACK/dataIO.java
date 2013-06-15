import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class dataIO {
	private static final Boolean TRUE = new Boolean(true);
	public static void main(String[] args)throws IOException{
		Socket gameS = null; //ソケット
		BufferedReader in = null;//ソケットからの入力
		PrintWriter out = null;//ソケットへの出力
		try{
			gameS = new Socket(InetAddress.getLocalHost(),50000);
			in = new BufferedReader(new InputStreamReader(gameS.getInputStream()));
			out = new PrintWriter(gameS.getOutputStream(),true);
		}catch(UnknownHostException e){
			System.out.println("ホストに接続出来ません。");
			System.exit(1);
		}catch(IOException e){
			System.out.println("IOコネクションを得られません。");
			System.exit(1);
		}
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser = null;
		while((fromServer = in.readLine()) != null){
			System.out.println(">" + fromServer);//応答を表示
			if(fromServer.equals("また遊んで下さいね!")){
				System.out.println();
				break;//サーバが会話を打ち切ったので終了
			}

			if(fromServer.equals("カードをドローしますか？ y...はい | n...いいえ")){
				do{
					fromUser = stdIn.readLine();
					if(fromUser.equals("y") || fromUser.equals("n")){
						break;
					}else{
						System.out.println("指定外の文字列です。");
						continue;
					}
				}while(TRUE);
			out.println(fromUser);
			}

			if(fromServer.equals("ゲームを続けますか？ y...はい | n...いいえ")){
				do{
					fromUser = stdIn.readLine();
					if(fromUser.equals("y") || fromUser.equals("n") ){
						break;
					}else{
						System.out.println("指定外の文字列です。");
						continue;
					}
				}while(TRUE);
			out.println(fromUser);
			}
		}
		out.close();in.close();stdIn.close();gameS.close();
	}
}
