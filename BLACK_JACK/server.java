import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	private static final Boolean TRUE = new Boolean(true);			//無限ループ用Boolean
	public  static PrintWriter out;									//クライアントソフトへの出力用
	public static blackJackRoutin b = new blackJackRoutin();		//ゲームルーチンクラス
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

		//プレイモード読み取り用
		playMode = Integer.parseInt(in.readLine());

		//通常起動起動した際にヘルプモードがあることをアナウンス
		if(playMode==0)out.println("ルールが分からない場合はオプションに--helpを付けて起動してヘルプを見て下さいね！");
		
		//ゲーム内ループ
		do{
			int settaiReStart=0;			//接待プレイ時用フラグ
			b.gameStartMassage(playMode);	//ゲームスタートのメッセージを出力
			b.set();						//トランプ、プレイヤーの手札、ディーラーの手札の初期化
			b.output(playMode);				//ステータスを出力
			
			//ドローを繰り返すための無限ループ。バースト(点数が21を超える）するか非ドローを選択するとループから抜ける。
			while(TRUE){
				b.drawMessage(playMode);	//ドローするか否かのメッセージを表示

				fromUser = in.readLine();	//入力待ち

				if(fromUser.equals("y")){	//入力が"y"ならドローを続ける
					b.playerDraw();
					b.output(playMode);
				}else{
					break;
				}

				/*バーストしていない場合はドローの続き、
				バーストしていればループから抜ける。
				playModeが1（接待プレイ）のときバーストしていれは、
				settaiRestartに2が入りゲームスタートからやり直し*/
				settaiReStart = b.burstJudge(playMode);
				if(settaiReStart != 0)break;

			}

			if(settaiReStart == 2)continue;	//ここでループやり直し

			b.turnOfDealer(playMode);		//ディーラーのターンであることを表示する
			b.dealerAction(playMode);		//ディーラーのドロー等のアクションを行う
			b.outputResult(playMode);		//CPUの手札、点数を含めたステータスの表示を行う
			b.judge(playMode);				//勝敗判定を行い、どちらの勝利かを表示する
			b.gameContinue(playMode);		//ゲームの続行意思の確認。y入力でもう一度ループの先頭へ、nでループから抜ける。
			fromUser = in.readLine();
		}while(fromUser.equals("y"));

		b.endMassage(playMode);				//ゲーム終了メッセージの表示
		out.close();clientS.close();serverS.close();
	}
}

