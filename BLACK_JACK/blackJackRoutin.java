import java.util.Random;

class blackJackRoutin {
	public static final Boolean TRUE = new Boolean(true);
	int playerCounter,dealerCounter;
	int trump[] = new int[52];
	int dealerCards[] = new int[52];
	int playerCards[] = new int[52];
	Random rnd = new Random();

	// すべての変数の初期化.プレイヤーとディーラーにカードを2枚配布する。
	void set(){

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

	// プレイヤーの合計点を計算する。
	int checkSumP(){
		int i,sum = 0,point = 0;
		for(i=0; playerCards[i] != 0 ; i++){
			if(playerCards[i] == 1 ){
				if(sum <= 10){
					point = 10;
				}else{
					point = 1;
				}
			}else if(playerCards[i] > 10 ){
				point = 10;
			}else{
				point = playerCards[i];
			}
			sum += point;
		}
		return sum;
	}

	// ディーラーの合計点を計算する。
	int checkSumD(){
		int i,sum = 0,point = 0;
		for(i=0; dealerCards[i] != 0 ; i++){
			if(dealerCards[i] == 1 ){
				if(sum <= 10){
					point = 10;
				}else{
					point = 1;
				}
			}else if(dealerCards[i] > 10 ){
				point = 10;
			}else{
				point = dealerCards[i];
			}
			sum += point;
		}

		return sum;
	}

	// プレイヤーのドロー動作
	void playerDraw(){
		int count;
		for(count=0; playerCards[count] != 0 ; count++){} // 配列の要素を全て0で初期化してあるのでcountを配列0のところまでカウントアップしてそこにカードの数値を代入する。
		playerCards[count] = this.draw();
	}

	// ディーラーのドロー動作
	void dealerDraw(){
		int count;
		for(count=0; dealerCards[count] != 0 ; count++){} // 配列の要素を全て0で初期化してあるのでcountを配列0のところまでカウントアップしてそこにカードの数値を代入する。
		dealerCards[count] = this.draw();
	}

	// ドローメソッド
	int draw(){
		int ran = 0;
		do{
			ran = rnd.nextInt(52);
		}while(trump[ran] == 0);// 引いたカードの配列の中身が0なら引き直し
		int drawCard = trump[ran];
		trump[ran] = 0;			// 引いたカードということを表すために0を代入しておく。

		return drawCard;
	}

	// ディーラーの思考ルーチン
	void dealerAction(int playMode){
		dealer:do{
			if(playMode==0){
				if(this.checkSumD() < 18){	// 現在の合計点が18未満ならばドロー
					this.dealerDraw();
				}else{
					break dealer;	// 行動を終了する
				}
			}
			// 接待プレイ用
			else{
				if(this.checkSumD() < this.checkSumP()){	// 現在の合計点がプレイヤーより低かったらそのまま
					break dealer;
				}else if(this.checkSumD() >= this.checkSumP()){	// 現在の合計点がプレイヤー以上であればバーストまでドローする
					while(this.checkSumD() < 22){
						this.dealerDraw();
					}
				}
				break dealer;
			}
		}while(TRUE);
	}

	// 勝敗判定クラス
	void judge(int playMode){
		if(playMode == 0){
			if(this.checkSumP() < 22 && this.checkSumD() < 22){
				if(this.checkSumP() < this.checkSumD()){
					server.out.println("\nディーラーの勝利！\n");
				}else if(this.checkSumP() > this.checkSumD()){
					server.out.println("\n＿人人人人人人人人人＿\n＞　あなたの勝利！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y￣\n");
				}else if(this.checkSumP() == this.checkSumD()){
					server.out.println("\n引き分け！\n");
				}
			}else if(this.checkSumP() > 22 && this.checkSumD() > 21){
				server.out.println("\n引き分け！\n");
			}else if(this.checkSumP() < 22 && this.checkSumD() > 21){
				server.out.println("\n＿人人人人人人人人人＿\n＞　あなたの勝利！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y￣\n");
			}else if(this.checkSumP() > 21 && this.checkSumD() < 22){
				server.out.println("\nディーラーの勝利！\n");
			}
		}
		// 接待モードでは勝利しかしないので表示はこれだけ用意しておく
		else{
			server.out.println("\n＿人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人＿\n＞　このようなゲームをプレイしてくださる懐の深きお方の勝利！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y￣\n");
		}
	}

	// ステータス表示
	void output(int playMode){
		server.out.println("-----------ステータス-----------");
		if(playMode==0){
			server.out.print("CPU:");
		}else{
			server.out.print("貴方様の足元にも及ばない愚生:");
		}
		for(int i=0; dealerCards[i] != 0 ; i++){
			// 1,11,12,13という数字で扱っているので表示するために変換する
			if(i==0){
				if(dealerCards[i] == 1){
					server.out.print(" A");
				}else if(dealerCards[i] == 11){
					server.out.print(" J");
				}else if(dealerCards[i] == 12){
					server.out.print(" Q");
				}else if(dealerCards[i] == 13){
					server.out.print(" K");
				}else{
					server.out.print(" " + dealerCards[i]);
				}
			}else{
				server.out.print(" ＊");	// CPUの手札を隠蔽する
			}
		}
		server.out.println();
		if(playMode==0){
			server.out.print("あなた:");
		}else{
			server.out.print("このようなゲームをプレイしてくださる懐の深きお方:");
		}
		for(int i=0; playerCards[i] != 0; i++){
			if(playerCards[i] == 1){
				server.out.print(" A");
			}else if(playerCards[i] == 11){
				server.out.print(" J");
			}else if(playerCards[i] == 12){
				server.out.print(" Q");
			}else if(playerCards[i] == 13){
				server.out.print(" K");
			}else{
				server.out.print(" " + playerCards[i]);
			}
		}
		server.out.println();
		server.out.println("--------------------------------");
		if(playMode==0){
			server.out.println("\nあなたの点数:" + this.checkSumP() + "\n");
		}else{
			server.out.println("\nこのようなゲームをプレイしてくださる懐の深きお方の点数:" + this.checkSumP() + "\n");
		}
	}

	// CPUの手札, 合計点を含めた最終結果を表示する
	void outputResult(int playMode){
		server.out.println("-----------ステータス-----------");
		if(playMode==0){
			server.out.print("CPU:");
		}else{
			server.out.print("貴方様の足元にも及ばない愚生:");
		}
		for(int i=0; dealerCards[i] != 0 ; i++){
			if(dealerCards[i] == 1){
				server.out.print(" A");
			}else if(dealerCards[i] == 11){
				server.out.print(" J");
			}else if(dealerCards[i] == 12){
				server.out.print(" Q");
			}else if(dealerCards[i] == 13){
				server.out.print(" K");
			}else{
				server.out.print(" " + dealerCards[i]);
			}
		}
		server.out.println();
		if(playMode==0){
			server.out.print("あなた:");
		}else{
			server.out.print("このようなゲームをプレイしてくださる懐の深きお方:");
		}
		for(int i=0; playerCards[i] != 0; i++){
			if(playerCards[i] == 1){
				server.out.print(" A");
			}else if(playerCards[i] == 11){
				server.out.print(" J");
			}else if(playerCards[i] == 12){
				server.out.print(" Q");
			}else if(playerCards[i] == 13){
				server.out.print(" K");
			}else{
				server.out.print(" " + playerCards[i]);
			}
		}
		server.out.println("\n--------------------------------\n");
		if(playMode==0){
			server.out.println("CPUの点数:" + this.checkSumD());
			server.out.println("あなたの点数:" + this.checkSumP());
		}else{
			server.out.println("貴方様の足元にも及ばない愚生の点数:" + this.checkSumD());
			server.out.println("このようなゲームをプレイしてくださる懐の深きお方の点数:" + this.checkSumP());
		}
	}

	// ゲームを続けるかの意思確認表示メソッド
	void gameContinue(int playMode){
		if(playMode==0){
			server.out.println("ゲームを続けますか？ y...はい | n...いいえ\n");
		}else{
			server.out.println("引き続きゲームで遊んで頂けますか？ y...はい | n...いいえ\n");
		}
	}

	// ゲーム終了メッセージ表示メソッド
	void endMassage(int playMode){
		if(playMode==0){
			server.out.println("また遊んで下さいね!\n");
		}else{
			server.out.println("またのプレイをお待ちしております\n");
		}
	}

	// ディーラーのターンであることを表示するためのメソッド
	void turnOfDealer(int playMode){
		if(playMode==0){
			server.out.println("ディーラーのターン\n");
		}else{
			server.out.println("貴方様の足元にも及ばない愚生のターン\n");
		}
	}

	// バーストしたかどうかを判定するメソッド
	int burstJudge(int playMode){
		if(this.checkSumP() > 21){
			if(playMode == 0){
				server.out.println("＿人人人人人人人＿\n＞　バースト！　＜\n￣Y^Y^Y^Y^Y^Y￣\n");
				return 1;
			}
			// 接待プレイ時はバーストしていればゲームを初めからスタートさせる
			else{
				server.out.println("＿人人人人人人人＿\n＞　バースト！　＜\n￣Y^Y^Y^Y^Y^Y￣\n");
				server.out.println("＿人人人人人＿\n＞　あっ…　＜\n￣Y^Y^Y^Y￣\n");
				server.out.println("＿人人人人人人人人人人人人人人人＿\n＞　なぜかカードがばらばらに！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y￣\n");
				server.out.println("＿人人人人人人人人人人人人人人人＿\n＞　最初からゲームしましょう！　＜\n￣Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y^Y￣\n");
				return 2;
			}
		}else{
			return 0;
		}
	}

	// ドローするか否かの意思確認
	void drawMessage(int playMode){
		if(playMode == 0){
			server.out.println("カードをドローしますか？ y...はい | n...いいえ\n");
		}else{
			server.out.println("カードをお渡しいたしましょうか？ y...はい | n...いいえ\n");
		}
	}

	// ゲーム開始メッセージ表示メソッド
	void gameStartMassage(int playMode){
		if(playMode==0){
			server.out.println("\nゲームを始めます");
		}else{
			server.out.println("\nゲームを始めさせて頂きます");
		}
	}
}