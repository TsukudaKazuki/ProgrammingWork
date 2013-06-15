import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class dataIO {
	private static final Boolean TRUE = new Boolean(true);
	public static void main(String[] args)throws IOException{
		int playMode = 0;
		Socket gameS = null; //ソケット
		BufferedReader in = null;//ソケットからの入力
		PrintWriter out = null;//ソケットへの出力
		Help h = new Help();

		if(args.length > 0)
		{
			if(args[0].equals("--help")){
				playMode=2;
			}else if(args[0].equals("-settaiPlay")){
				playMode=1;
			}else{
				System.out.println("入力されたオプションは存在しません。");
			}
		}

		if(playMode == 2){
			h.help();
		}else{
			try{
				gameS = new Socket(InetAddress.getLocalHost(),50000);
				in = new BufferedReader(new InputStreamReader(gameS.getInputStream()));
				out = new PrintWriter(gameS.getOutputStream(),true);
			}catch(UnknownHostException e){
				if(playMode==0){
					System.out.println("ホストに接続出来ません。");
				}else{
					System.out.println("ホストに接続することが出来ませんでした。申し訳ございません。");
				}
				System.exit(1);
			}catch(IOException e){
				if(playMode==0){
				System.out.println("IOコネクションを得られません。");
				}else{
					System.out.println("IOコネクションを得ることが出来ませんでした。申し訳ございません。");
				}
				System.exit(1);
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser = null;


			out.println(playMode);

			while((fromServer = in.readLine()) != null){
				System.out.println(">" + fromServer);//応答を表示
				if(fromServer.equals("また遊んで下さいね!") || fromServer.equals("またのプレイをお待ちしております")){
					System.out.println();
					break;//サーバが会話を打ち切ったので終了
				}

				if(fromServer.equals("カードをドローしますか？ y...はい | n...いいえ") || fromServer.equals("カードをお渡しいたしましょうか？ y...はい | n...いいえ")){
					do{
						fromUser = stdIn.readLine();
						if(fromUser.equals("y") || fromUser.equals("n")){
							break;
						}else{
							if(playMode==0){
								System.out.println("指定外の文字列です。");
							}else{
								System.out.println("恥ずかしながらご記入された文字が打ち込まれることを想定しておりませんでした。\nお手数ですが再入力頂けませんでしょうか。");
							}
							continue;
						}
					}while(TRUE);
					out.println(fromUser);
				}

				if(fromServer.equals("ゲームを続けますか？ y...はい | n...いいえ") || fromServer.equals("引き続きゲームで遊んで頂けますか？ y...はい | n...いいえ")){
					do{
						fromUser = stdIn.readLine();
						if(fromUser.equals("y") || fromUser.equals("n") ){
							break;
						}else{
							if(playMode==0){
								System.out.println("指定外の文字列です。");
							}else{
								System.out.println("恥ずかしながらご記入された文字が打ち込まれることを想定しておりませんでした。\nお手数ですが再入力頂けませんでしょうか。");
							}
							continue;
						}
					}while(TRUE);
					out.println(fromUser);
				}
			}
			out.close();in.close();stdIn.close();gameS.close();
		}
	}
}
