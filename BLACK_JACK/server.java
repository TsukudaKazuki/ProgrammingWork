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
		String fromUser = null;
		int playMode=0;

		playMode = Integer.parseInt(in.readLine());//プレイモード読み取り用

		//ゲーム内ループ
		if(playMode==0)out.println("ルールが分からない場合はオプションに--helpを付けて起動してヘルプを見て下さいね！");
		do{
			int settaiReStart=0;
			b.gameStartMassage(playMode);
			b.set();
			b.output(playMode);
			while(TRUE){
				b.drawMessage(playMode);

				fromUser = in.readLine();

				if(fromUser.equals("y")){
					b.playerDraw();
					b.output(playMode);
				}else{
					break;
				}

				settaiReStart = b.burstJudge(playMode);
				if(settaiReStart != 0)break;

			}
			
			if(settaiReStart == 2)continue;

			b.turnOfDealer(playMode);
			b.dealerAction(playMode);
			b.outputResult(playMode);
			b.judge(playMode);
			b.gameContinue(playMode);
			fromUser = in.readLine();
		}while(fromUser.equals("y"));
		
		b.endMassage(playMode);
		out.close();clientS.close();serverS.close();
	}
}

