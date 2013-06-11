import java.io.*;
import java.util.Scanner;

public class Ex1{
	public static void main(String[] args){
        String[] are = new String[100];
        int i=0,sum=0;
        try{
            FileReader filein = new FileReader(args[0]);
            //うんこちんちんアヘ顔ダブルピース
            BufferedReader buffin = new BufferedReader(filein);
            Scanner sc = new Scanner(buffin);
            while(sc.hasNext()){
                i++;
                are[i-1] = sc.next();
            }
            
            for(i=0;i<10;i += 2){
                System.out.println(are[i]+" "+are[i+1]);
            }
            
            for(i=1;i<10;i += 2){
                
                sum += Integer.parseInt(are[i]);
            }
            
            System.out.println("合計 "+ sum);
            
            for(i=1;i<10;i += 2){
                System.out.print(are[i]+" ");
            }
            
            System.out.println();
            
            buffin.close();
            
            
            
        }catch(IOException e){
            System.out.println(e);
        }
    }
}