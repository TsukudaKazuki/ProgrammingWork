import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class dataIO {
	private static final Boolean TRUE = new Boolean(true);
	public static void main(String[] args)throws IOException{
		int playMode = 0;			//0で通常起動,1で接待プレイ,2でヘルプ,3は不正オプションのため終了
		Socket gameS = null;		//ソケット
		BufferedReader in = null;	//ソケットからの入力
		PrintWriter out = null;		//ソケットへの出力
		File file = null;
		BufferedWriter bw=null;		//ファイル出力用
		Help h = new Help();//Helpクラス

		//オプション入力判定。--helpでヘルプ。-settaiPlayで接待モード起動。
		if(args.length > 0)
		{
			if(args[0].equals("--help")){
				playMode=2;
			}else if(args[0].equals("-settaiPlay")){
				playMode=1;
			}else{
				System.out.println("入力されたオプションは存在しません。");
				playMode=3;
			}
		}

		if(playMode == 2){
			h.help();
		}else if(playMode==3){
			//何もせずに終了。
		}else{
			try{
				gameS = new Socket(InetAddress.getLocalHost(),50000);
				in = new BufferedReader(new InputStreamReader(gameS.getInputStream()));
				out = new PrintWriter(gameS.getOutputStream(),true);
				file = new File("Result.txt");
				bw = new BufferedWriter(new FileWriter(file));
			}catch(UnknownHostException e){
				if(playMode==0){
					System.out.println("\n＞ホストに接続出来ません。\n");
				}else{
					System.out.println("\n＞ホストに接続することが出来ませんでした。申し訳ございません。\n");
				}
				System.exit(1);
			}catch(IOException e){
				if(playMode==0){
					System.out.println("\n＞IOコネクションを得られません。\n");
				}else{
					System.out.println("\n＞IOコネクションを得ることが出来ませんでした。申し訳ございません。\n");
				}
				System.exit(1);
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser = null;


			out.println(playMode);

			while((fromServer = in.readLine()) != null){
				bw.write(fromServer);
				bw.newLine();
				System.out.println(">" + fromServer);//応答を表示
				if(fromServer.equals("また遊んで下さいね!") || fromServer.equals("またのプレイをお待ちしております")){
					System.out.println();
					break;//サーバが会話を打ち切ったので終了
				}

				//入力用。文字の不正入力も検知する。
				if(fromServer.equals("カードをドローしますか？ y...はい | n...いいえ") || fromServer.equals("カードをお渡しいたしましょうか？ y...はい | n...いいえ")){
					do{
						fromUser = stdIn.readLine();
						if(fromUser.equals("y") || fromUser.equals("n")){
							bw.write(fromUser);		//ファイルへの書き込み
							bw.newLine();
							break;
						}else{
							if(playMode==0){
								System.out.println("＞指定外の文字列です。");
							}else{
								System.out.println("＞恥ずかしながらご記入された文字が打ち込まれることを想定しておりませんでした。\nお手数ですが再入力頂けませんでしょうか。");
							}
							continue;
						}
					}while(TRUE);
					out.println(fromUser);
				}

				//入力用。文字の不正入力も検知する。
				if(fromServer.equals("ゲームを続けますか？ y...はい | n...いいえ") || fromServer.equals("引き続きゲームで遊んで頂けますか？ y...はい | n...いいえ")){
					do{
						fromUser = stdIn.readLine();
						if(fromUser.equals("y") || fromUser.equals("n") ){
							bw.write(fromUser);
							bw.newLine();
							break;
						}else{
							if(playMode==0){
								System.out.println("＞指定外の文字列です。");
							}else{
								System.out.println("＞恥ずかしながらご記入された文字が打ち込まれることを想定しておりませんでした。\nお手数ですが再入力頂けませんでしょうか。");
							}
							continue;
						}
					}while(TRUE);
					out.println(fromUser);
				}
			}
			bw.close();out.close();in.close();stdIn.close();gameS.close();
		}
	}
}
