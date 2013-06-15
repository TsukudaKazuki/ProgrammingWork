import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class server {
	public PrintWriter out;
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
		String fromC,fromUser;
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
					if(b.checkSum() == 1){
						break;
					}
				}else{break;}
			}while(1);





		}while(continue);
	}
}

class blackJack {
	int playerCounter,dealerCounter;
	int trump[] = new int[52];
	int dealerCards[] = new int[52];
	int playerCards[] = new int[52];
	Random rnd = new Random();

	//////////////////////////////////////////////////////////
	//すべての変数の初期化.プレイヤーとディーラーにカードを2枚配布する。//
	//////////////////////////////////////////////////////////

	void set(){

		playerCounter = 0;
		dealerCounter = 0;

		for(int i=0; i<52; i++){
			trump[i] = 0;
			dealerCards[i] = 0;
			playerCards[i] = 0;
		}

		for(int i=0; i<52; i++){
			trump[i] = i % 13 + 1;
		}

		for(int i=0; i<2; i++){
			playerCards[i] = this.draw();
			dealerCards[i] = this.draw();
		}
	}

	int checkSum{
		int i,sum,point;
		for(i=0; playerCards[i] != 0 ; i++){
			if(prayerCards[i] == 1 ){
				if(sum <= 10){
					point = 10;
				}else{
					point = 1;
				}
			}else if(playerCards[i] == 11 || playerCards[i] == 12 || playerCards[i] == 13 ){
				point = 10;
			}
			int sum += point;
		}
		
		if(sum > 21){
			return 1;
		}
	}

	void playerDraw(){
		int count;
		for(count=0; playerCards[count] != 0 ; count++) //配列の要素を全て0で初期化してあるのでcountを配列0のところまでカウントアップしてそこにカードの数値を代入する。
		playerCards[count] = this.draw();
	}

	int draw(){
		int ran = 0;
		do{
			ran = rnd.nextInt(52);
		}while(trump[ran] == 0);//引いたカードの配列の中身が0なら引き直し
		int drawCard = trump[ran];
		trump[ran] = 0;

		return drawCard;
	}

	//文字出力担当

	void output(){
		out.println("-----------ステータス-----------");
		out.print("CPU:");
		for(int i=0; dealerCards[i] != 0 ; i++){
			if(i==0){
				if(dealerCards[i] == 1){
					out.print(" A");
				}else if(dealerCards[i] == 11){
					out.print(" J");
				}else if(dealerCards[i] == 12){
					out.print(" Q");
				}else if(dealerCards[i] == 13){
					out.print(" K");
				}else{
					out.print(" " + dealerCards[i]);
				}
				out.print(dealerCards[i]);
			}
			out.print(" ＊");
		}
		out.println();
		out.print("あなた:");
		for(int i=0; playerCards[i] != 0; i++){
			if(playerCards[i] == 1){
				out.print(" A");
			}else if(playerCards[i] == 11){
				out.print(" J");
			}else if(playerCards[i] == 12){
				out.print(" Q");
			}else if(playerCards[i] == 13){
				out.print(" K");
			}else{
				out.print(" " + playerCards[i]);
			}
		}
		out.println();
		out.println("------------------------------");
	}
}
