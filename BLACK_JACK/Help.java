import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Help {
	public static final Boolean TRUE = new Boolean(true);
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	String in;
	void help() throws IOException{
		do{
			System.out.println("\n＞ヘルプです。");
			System.out.println("＞見たい項目を選んでください。\n");
			System.out.println("＞(1)ルール\n＞(2)製作者\n＞(3)見たくない\n");

			do{
			in = stdIn.readLine();

			if(!(in.equals("1") || in.equals("2") || in.equals("3"))){
				System.out.println("\n＞指定外の入力です。\n");
			}else{
				break;
			}

			}while(TRUE);

			if(in.equals("1")){
				System.out.println("\n（１）ルール");
				System.out.println("　・まず両者にカードを2枚ずつ配布する。 ");
				System.out.println("　・その後、カードを引くかあがるかを決める。");
				System.out.println("　・A, J, Q, K以外のカードを引いた時は表記されて いる数字をそのまま持ち点に加算する。");
				System.out.println("　・Aを引いた場合、持ち点が10以下のときは11 を、11以上のときは1を加算する。");
				System.out.println("　・J, Q, Kを引いた場合は持ち点を10加算する。");
				System.out.println("　・交互に繰り返し、どちらかがあがりを宣言した後、両者の点数を比べ、より21に近い方が勝者となる。");
				System.out.println("　・持ち点が22以上になるとその時点で敗者となる。");
				System.out.println("\n＞以上です。\n");
			}else if(in.equals("2")){
				System.out.println("\n＞このゲームは\n\n　入出力処理:さけちー\n　内部処理:つくっち\n\n＞の提供でお送りします。");
			}else if(in.equals("3")){
				System.out.println("\n＞ではゲームを楽しんでください！\n");
				break;
			}



		}while(TRUE);

	}
}
