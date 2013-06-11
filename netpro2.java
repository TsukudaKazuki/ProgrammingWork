import java.util.*;
import java.io.*;
public class netpro2{
    void dataInOut(String outputfilename) throws IOException{
       FileReader filein=new FileReader("data1.txt");
       FileWriter fileout=new FileWriter(outputfilename);
       BufferedReader buffin =new BufferedReader(filein);
       BufferedWriter buffout=new BufferedWriter(fileout);
       Scanner sc=new Scanner(buffin);
       while(sc.hasNext()){
	   buffout.write(sc.next());//値を返しつつ次のデータへ移動するはたらきもの
	   buffout.newLine();//これで出力ファイル側で改行できる。
       }
       buffin.close();
       sc.close();
       buffout.close();
    }
    public static void main(String args[]){
       netpro2 r=new netpro2();
       try{r.dataInOut(args[0]);
       }catch(IOException error){System.out.println("ERROR!");}
    }
}