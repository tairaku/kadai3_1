import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 cat_links.txtを読み込み、「存命人物」のカテゴリに入っている人の中から、被リンク数の多いページを
 上位10位まで出力するプログラム。
 */

public class Makehuman {
	public static void main(String[] args) {
		
		int linkSize=52973671;  //linl.txtの総行数 
		int pageSize=1483277;  //pages.txtの総行数
		int max=151932; //「存命人物」のリンク数
		//int max=30321;//「曖昧さ回避」のリンク数
		int human_id=78980; //「存命人物」のカテゴリ番号
		//int human_id=9;//「曖昧さ回避」のカテゴリ番号
		
		//int max=8493; //「人名の曖昧さ回避」のリンク数
		//int human_id=81075; //「人名の曖昧さ回避」のカテゴリ番号
		
		int[] human=new int[max]; //生存人間のid
	
		//ファイルを読み込む
		String inputFileName = "wikipedia_cats/cat_links.txt"; //cat_links.txt
		String inputFileName2 = "wikipedia_links/links.txt"; //links.txt
		String inputFileName3 = "wikipedia_links/pages.txt"; //pages.txt
		

		// links.txtを読み込む準備
		int[] idl=new int[linkSize];  //リンク元のページのid
		int[] linked=new int[linkSize];  //リンク先のページのid
		for(int i=0;i<linkSize;i++){  //初期化
		    idl[i]=0;
		    linked[i]=0;
		}
		
		//pages.txtを読み込む準備
		int[] idp=new int[pageSize];  //ページのid
		String[] word=new String[pageSize];  //ページの見出し語
		for(int i=0;i<pageSize;i++){
		    idp[i]=0;
		    word[i]="";
		}
		
		try{			
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			BufferedReader br2 = new BufferedReader(new FileReader(inputFileName2));
			BufferedReader br3 = new BufferedReader(new FileReader(inputFileName3));
			
			/*１行ずつ読み込む*/
			String str=br.readLine();
			String str2=br2.readLine();
			String str3=br3.readLine();
			
			//cat_links.txtを配列に
		    int i=0;
		    while(str!=null){
		    	String[] links=str.split("\t");  //タブで区切る
		    	if(Integer.parseInt(links[1])==human_id){
		    		human[i]=Integer.parseInt(links[0]);
		    		i++;
		    	}
		    str=br.readLine();	
			}
		    //link.txtを配列に
		    int j=0;
		    while(str2!=null){
		    	String[] linksf=str2.split("\t");  //タブで区切る
				idl[j]=Integer.parseInt(linksf[0]);  //タブの前
				linked[j]=Integer.parseInt(linksf[1]);  //タブの後
				str=br2.readLine();
				j++;
				str2=br2.readLine();	
			}
		    //page.txtを配列に
		    int k=0;
		    while(str3!=null){
				String[] pagesf=str3.split("\t");  //タブで区切る
				idp[k]=Integer.parseInt(pagesf[0]);  //タブの前
				word[k]=pagesf[1];  //タブの後
				str3=br3.readLine();
				k++;
			}
		    
		    br.close();
		    br2.close();
		    br3.close();
			
		}catch(IOException e){
			System.out.println("Error");
		}
		
		
		//リンク数を数える
		int[] num_linked=new int[pageSize];  //他のページへのリンク数
		for(int i=0;i<linkSize;i++){
		    num_linked[linked[i]]+=1;
		}
		
		//上位10位までをを求める
		int[] rank=new int[10];
		int[] id=new int[10];
		for(int i=0;i<10;i++){
		    rank[i]=0;
		    id[i]=0;
		}

		int j=0;  //(j+1)位を求めていく
		int bignum=pageSize;  //j位の数を入れて(j+1)位をj位より大きくしない(下のif文)
		while(j<10){
		    for(int i=0;i<pageSize;i++){
					if(rank[j]<num_linked[i] && num_linked[i]<bignum && bisearch(i,human)==1){ 
						rank[j]=num_linked[i];
						id[j]=i;
					}
		    }
		    bignum=rank[j];  
		    j++;
		}
		//結果を表示
		for(int i=0;i<10;i++){
		    System.out.println(i+1+"位 "+word[id[i]]);
		}
		
	    }
	
	//二分探索で探索する。
	public static int bisearch(int idd,int[] idarray){//存在するなら1,しないなら-1。
		int pLeft = 0;
		int pRight = idarray.length - 1;

		do {
			int center = (pLeft + pRight) / 2;

			if (idarray[center] == idd) {
				return 1;
			} else if (idarray[center] < idd){
				pLeft = center + 1; //真ん中の一つ右側を左端とする
			} else {
				pRight = center - 1;
			}
		} while (pLeft <= pRight);

		return -1;
	}
	
		
		
}
