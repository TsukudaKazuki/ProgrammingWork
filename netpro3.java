import java.io.*;
import java.net.*;
public class netpro3{
    public static void main(String[] args) throws IOException{
        Socket soc=null;
        BufferedReader in=null;
        PrintWriter out=null;
        String host="japan.unity3d.com";
        String line;
        int donotreadflag=0;
        soc=new Socket(host,80);
        in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out=new PrintWriter(soc.getOutputStream(),true);
        //出力ストリームへHTTPのリクエストを書き出す
        out.print("GET "+"/index.html"+" HTTP/1.0\r\n");
        out.println("Host: "+host+":80\r\n");
        
        while((line=in.readLine())!=null){
            if(line.indexOf("<script")!=-1)donotreadflag=1;
            if(line.indexOf("</script")!=-1)donotreadflag=0;//ソース内にscriptタグが入ってたときにフラグをいじってsplitしないように。
            if(line.indexOf("-->")!=-1){
            }else{
                if(line != ""&&donotreadflag==0){//読んだラインが空白　もしくはdonotoreadflagが経っていないときだけ実行
                    String[] str=line.split("<");
                    System.out.print(str[0]);//<br>後の次の行など、いきなり文章が始まる行を出力する
                    for(String s: str){
                        String[] temp=s.split(">");//分割する
                        
                        if(temp.length>=2){
                            System.out.print(temp[1]);//2分割したものの後ろ側はテキストか、空白。
                        }
                    }
                    System.out.println();//改行して見やすくする。
                }
            }
        }
        in.close();
        out.close();
        soc.close();
        
    }
}
