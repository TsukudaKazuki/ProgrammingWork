import java.io.*;
import java.util.Scanner;

public class Ex1{
	public static void main(String[] args){
		String[] are = new String[100];
		int i=0,sum=0;
		try{
			FileReader filein = new FileReader(args[0]);
			BufferedReader buffin = new BufferedReader(filein);
			Scanner sc = new Scanner(buffin);   // コマンドライン引数で指定されたファイルを読み込む
			while(sc.hasNext()){
				i++;
				are[i-1] = sc.next();   // are[i-1]にファイルを一行ずつ読み込む
			}

			for(i=0;i<10;i += 2){
				System.out.println(are[i]+" "+are[i+1]);    //中身を表示する
			}

			for(i=1;i<10;i += 2){
				sum += Integer.parseInt(are[i]);     //点数を加算する
			}

			System.out.println("合計 "+ sum);  //合計点を表示する

			for(i=1;i<10;i += 2){
				System.out.print(are[i]+" ");   //すべての点数だけを表示する
			}

			System.out.println();

			buffin.close();



		}catch(IOException e){
			System.out.println(e);
		}
	}
}