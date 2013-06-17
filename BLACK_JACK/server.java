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
		int playMode=0;

		playMode = Integer.parseInt(in.readLine());//プレイモード読み取り用

		//ゲーム内ループ
		if(playMode==0)out.println("ルールが分からない場合はオプションに--helpを付けて起動してヘルプを見て下さいね！");
		do{
			int settaiReStart=0;
			if(playMode==0){
				out.println("\nゲームを始めます");
			}else{
				out.println("\nゲームを始めさせて頂きます");
			}
			b.set();
			b.output(playMode);
			do{
				if(playMode == 0){
					out.println("カードをドローしますか？ y...はい | n...いいえ\n");
				}else{
					out.println("カードをお渡しいたしましょうか？ y...はい | n...いいえ\n");
				}

				fromUser = in.readLine();

				if(fromUser.equals("y")){
					b.playerDraw();
					b.output(playMode);
				}else{
					break;
				}

				if(b.checkSumP() > 21){
					if(playMode == 0){
						out.println("＿人人人人人人人＿\n＞　バースト！　＜\n￣Y^Y^Y^Y^Y^Y￣\n");
					}else{
						settaiReStart=1;
						out.println("＿人人人人人人人＿\n＞　バースト！　＜\n￣Y^Y^Y^Y^Y^Y￣\n");
						out.println("＿人人人人人＿\n＞　あっ…　＜\n￣Y^Y^Y^Y￣\n");
						out.println("＿人人人人人人人人人人人人人人人＿\n＞　なぜかカードがばらばらに！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y￣\n");
						out.println("＿人人人人人人人人人人人人人人人＿\n＞　最初からゲームしましょう！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y￣\n");
					}
					break;
				}
			}while(TRUE);

			if(settaiReStart == 1)continue;
			if(playMode==0){
				out.println("ディーラーのターン\n");
			}else{
				out.println("貴方様の足元にも及ばない愚生のターン\n");
			}
			b.dealerAction(playMode);
			b.outputResult(playMode);
			b.judge(playMode);

			if(playMode==0){
				out.println("ゲームを続けますか？ y...はい | n...いいえ\n");
			}else{
				out.println("引き続きゲームで遊んで頂けますか？ y...はい | n...いいえ\n");
			}
			fromUser = in.readLine();
		}while(fromUser.equals("y"));
		if(playMode==0){
			out.println("また遊んで下さいね!\n");
		}else{
			out.println("またのプレイをお待ちしております\n");
		}
		out.close();clientS.close();serverS.close();
	}

}

