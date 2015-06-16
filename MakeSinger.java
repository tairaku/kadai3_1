import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 pages.txtの中から歌手のページだけを取り出し、ファイルに書き込み。
 1行を「ページ番号　歌手の名前」の形でファイルに書き込み。
 */

public class MakeSinger{
	public static void main(String[] args) {
		
		String s1="_(";
		String s2="歌手";
		String s3=")";
		
		int pageSize=1483277;  //pages.txtの総行数
		
		int[] idp=new int[pageSize];  //ページのid
		String[] word=new String[pageSize];  //ページの見出し語
		
		//ファイルを読み込む
		String inputFileName = "wikipedia_links/pages.txt"; //読み込み用
		String outputFileName = "wikipedia_links/singer_pages.txt";//書き込み用
		
		
		try{			
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
			
			/*「単語　ソートしたもの」を１行として書き込む*/
			String str=br.readLine();
		    int j=0;
		    while(str!=null){
			String[] pagesf=str.split("\t");  //タブで区切る
			if(pagesf[1].indexOf(s1) != -1 && pagesf[1].indexOf(s2) != -1 && pagesf[1].indexOf(s3) != -1){
				idp[j]=Integer.parseInt(pagesf[0]);  //タブの前
				bw.write(pagesf[0]);
				bw.write(" ");
				word[j]=pagesf[1];  //タブの後
				bw.write(word[j]);
				System.out.println(pagesf[1]);
				bw.newLine();
				j++;
			}
			str=br.readLine();
		    }
			
			//ファイルを閉じる
			br.close(); 
			bw.close();

		}catch(IOException e){
			System.out.println("Error");
		}
		
	}
}
	