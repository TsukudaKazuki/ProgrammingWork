import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	private static final Boolean TRUE = new Boolean(true);;
	public static PrintWriter out;
	public static void main (String[] args)throws IOException{
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

		//出力ストリーム
		PrintWriter out = new PrintWriter(clientS.getOutputStream(),true);
		//入力ストリーム
		BufferedReader in = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
		String fromUser;
		blackJack b = new blackJack();

		//ゲーム内ループ
		do{
			out.println("ゲームを始めます");
			b.set();
			b.output();
			do{
				out.println("カードをドローしますか？ y...はい | n...いいえ");
				fromUser = in.readLine();
				if(fromUser == "y"){
					b.playerDraw();
					b.output();
					if(b.checkSumP() > 21 ){
						out.println("バースト！");
						break;
					}
				}else{break;}
			}while(TRUE);

			out.println("ディーラーのターン");
			b.dealerAction();
			b.output();
			b.judge();

			out.println("ゲームを続けますか？ y...はい | n...いいえ");
			fromUser = in.readLine();
		}while(fromUser == "y");
		out.println("また遊んで下さいね!");
		out.close();clientS.close();serverS.close();
	}
}

