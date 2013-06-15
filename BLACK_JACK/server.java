import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	private static final Boolean TRUE = new Boolean(true);
	public  static PrintWriter out;
	public static blackJackRoutin b = new blackJackRoutin();
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
		out = new PrintWriter(clientS.getOutputStream(),true);
		//入力ストリーム
		BufferedReader in = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
		String fromUser;


		//ゲーム内ループ
		do{
			out.println("\nゲームを始めます");
			b.set();
			output();
			do{
				out.println("カードをドローしますか？ y...はい | n...いいえ");
				fromUser = in.readLine();
				if(fromUser.equals("y")){
					b.playerDraw();
					output();
				}else{break;}
				if(b.checkSumP() > 22){
					out.println("\n＿人人人人人人人＿\n＞　バースト！　＜\n￣Y^Y^Y^Y^Y^Y￣\n");
					break;
				}
			}while(TRUE);

			out.println("ディーラーのターン\n");
			b.dealerAction();
			output(1);
			judge();

			out.println("ゲームを続けますか？ y...はい | n...いいえ");
			fromUser = in.readLine();
		}while(fromUser.equals("y"));
		out.println("また遊んで下さいね!");
		out.close();clientS.close();serverS.close();
	}

	static void judge(){
		if(b.checkSumP() < 22 || b.checkSumD() < 22){
			if(b.checkSumP() < b.checkSumD()){

				out.println("\nディーラーの勝利！\n");
			}else if(b.checkSumP() > b.checkSumD()){
				out.println("\n＿人人人人人人人人人＿\n＞　あなたの勝利！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y￣\n");
			}else if(b.checkSumP() == b.checkSumD()){
				out.println("\n引き分け！\n");
			}
		}else if(b.checkSumP() > 21 && b.checkSumD() > 21){
			out.println("\n引き分け！\n");
		}else if(b.checkSumP() < 22 && b.checkSumD() > 21){
			out.println("\n＿人人人人人人人人人＿\n＞　あなたの勝利！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y￣\n");
		}else if(b.checkSumP() > 21 && b.checkSumD() < 22){
			out.println("\nディーラーの勝利！\n");
		}
	}

	static void output(){
		out.println("-----------ステータス-----------");
		out.print("CPU:");
		for(int i=0; b.dealerCards[i] != 0 ; i++){
			if(i==0){
				if(b.dealerCards[i] == 1){
					out.print(" A");
				}else if(b.dealerCards[i] == 11){
					out.print(" J");
				}else if(b.dealerCards[i] == 12){
					out.print(" Q");
				}else if(b.dealerCards[i] == 13){
					out.print(" K");
				}else{
					out.print(" " + b.dealerCards[i]);
				}
			}else{
				out.print(" ＊");
				}
		}
		out.println();
		out.print("あなた:");
		for(int i=0; b.playerCards[i] != 0; i++){
			if(b.playerCards[i] == 1){
				out.print(" A");
			}else if(b.playerCards[i] == 11){
				out.print(" J");
			}else if(b.playerCards[i] == 12){
				out.print(" Q");
			}else if(b.playerCards[i] == 13){
				out.print(" K");
			}else{
				out.print(" " + b.playerCards[i]);
			}
		}
		out.println();
		out.println("--------------------------------");
		out.println("あなたの点数:" + b.checkSumP());
	}

	static void output(int j){
		out.println("-----------ステータス-----------");
		out.print("CPU:");
		for(int i=0; b.dealerCards[i] != 0 ; i++){
				if(b.dealerCards[i] == 1){
					out.print(" A");
				}else if(b.dealerCards[i] == 11){
					out.print(" J");
				}else if(b.dealerCards[i] == 12){
					out.print(" Q");
				}else if(b.dealerCards[i] == 13){
					out.print(" K");
				}else{
					out.print(" " + b.dealerCards[i]);
				}
		}
		out.println();
		out.print("あなた:");
		for(int i=0; b.playerCards[i] != 0; i++){
			if(b.playerCards[i] == 1){
				out.print(" A");
			}else if(b.playerCards[i] == 11){
				out.print(" J");
			}else if(b.playerCards[i] == 12){
				out.print(" Q");
			}else if(b.playerCards[i] == 13){
				out.print(" K");
			}else{
				out.print(" " + b.playerCards[i]);
			}
		}
		out.println("\n--------------------------------\n");
		out.println("CPUの点数:" + b.checkSumD());
		out.println("あなたの点数:" + b.checkSumP());
	}
}

