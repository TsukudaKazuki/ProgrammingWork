import java.util.Random;

class blackJackRoutin {
	public static final Boolean TRUE = new Boolean(true);
	int playerCounter,dealerCounter;
	int trump[] = new int[52];
	int dealerCards[] = new int[52];
	int playerCards[] = new int[52];
	Random rnd = new Random();

	//////////////////////////////////////////////////////////
	//すべての変数の初期化.プレイヤーとディーラーにカードを2枚配布する。//
	//////////////////////////////////////////////////////////

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

	void playerDraw(){
		int count;
		for(count=0; playerCards[count] != 0 ; count++){} //配列の要素を全て0で初期化してあるのでcountを配列0のところまでカウントアップしてそこにカードの数値を代入する。
		playerCards[count] = this.draw();
	}

	void dealerDraw(){
		int count;
		for(count=0; dealerCards[count] != 0 ; count++){} //配列の要素を全て0で初期化してあるのでcountを配列0のところまでカウントアップしてそこにカードの数値を代入する。
		dealerCards[count] = this.draw();
	}

	int draw(){
		int ran = 0;
		do{
			ran = rnd.nextInt(52);
		}while(trump[ran] == 0);//引いたカードの配列の中身が0なら引き直し
		int drawCard = trump[ran];
		trump[ran] = 0;			//

		return drawCard;
	}

	void dealerAction(){
		do{
			if(this.checkSumD() < 18){
				this.dealerDraw();
			}else{
				break;
				}
		}while(TRUE);
	}
}