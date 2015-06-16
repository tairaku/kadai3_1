import java.io.*;
import java.util.*;

/*
pages.txtを呼び出し、最短経路の距離を計算。
計算した値をファイルに書き込み。1行は「見出し語のスタート　見出し語のゴール　スタートからゴールまでの距離」
計算時間が大きくなってしまうので、今回はスタートとする見出し語を一つに決め、そこから全てのページ番号への距離を
ファイルに書き込みしました。
 */

public class Dijkstra {

	public static void main(String[] args) {
		
		//Randomクラスのインスタンス化
        Random rnd = new Random();

        int ran1 = rnd.nextInt(10000);
        int ran2 = rnd.nextInt(10000);
		
		
		int src=ran1;  //スタート
		int dst=ran2;//ゴール
		
		int nText = 10000; // 見出し語の数
		int nLink = /*52973671*/218657; // リンクの数
		
		String[] sss = new String[2];
		String[] pages = new String[nText];
		int[] links = new int[nLink];
		int[] linked = new int[nLink];
		int ii=0;
		
		//ファイル読み込み
		String FileName = "wikipedia_links/subgraph_pages.txt";
		String FileName2 = "wikipedia_links/subgraph_links.txt";
		//String outputFileName="wikipedia_links/dijkstra_1000.txt";//書き込み用
		try{
			BufferedReader br = new BufferedReader(new FileReader(FileName));
			BufferedReader br2 = new BufferedReader(new FileReader(FileName2));
					
			/* 
			1行ずつ読み込み、２次元配列に入れる。
			*/
			String line2 = "";
			while((line2 = br2.readLine()) != null){
				sss = line2.split("	",0);		
				links[ii] = Integer.parseInt(sss[0]);
				linked[ii] = Integer.parseInt(sss[1]);				
				ii++;
			}
			String line = "";
			int jj=0;
			while((line = br.readLine()) != null){
				sss = line.split("	",0);		
				pages[jj] = sss[1];				
				jj++;				
			}
			//ファイル閉じる
			br.close();
			br2.close();
		}catch(IOException e){
			System.out.println("Error");
		}
		
		/*System.out.println("Start:"+pages[src]);
		System.out.println("Goal:"+pages[dst]);*/
		

		int[][] map = new int[nText][nText]; // 見出し語の接続関係のマップ
		for (int i=0; i<nText; i++){ // 接続マップを初期化する
		    for (int j=0; j<nText; j++){
		    	map[i][j] = (i==j) ? 0 : -1;
		    }
		}
		for (int i=0; i<nLink; i++) { // リンクの状況を読み込む
		    int from = links[i];
		    int to = linked[i];
		    int len = 1;
		    map[from][to] = len;
		}
		
		int[] distance = new int[nText]; // 各見出し語までの最短距離
				System.out.println("Start:"+pages[src]+"	Goal:"+pages[dst]);
				dijkstra(map,src,distance);
				if (distance[dst]==Integer.MAX_VALUE) {	// 解なし
					System.out.println("no route");
				} else {
					System.out.println("distance="+distance[dst]);
				}
		
	}
	
	
	
	public static void dijkstra(int[][] map,int src,int[] distance) {
		int nText= distance.length;
		boolean[] fixed = new boolean[nText]; // 最短距離が確定したかどうか
		for (int i=0; i<nText; i++) { // 各都市について初期化する
		    distance[i] = Integer.MAX_VALUE; // 最短距離の初期値は無限遠
		    fixed[i] = false;	// 最短距離はまだ確定していない
		}
		distance[src] = 0;	// 出発地点までの距離を0とする
		while (true) {
		    // 未確定の中で最も近い見出し語を求める
		    int marked = minIndex(distance,fixed);
		    if (marked < 0) return; // 全見出し語が確定した場合
		    if (distance[marked]==Integer.MAX_VALUE)return; 
		    fixed[marked] = true; // その見出し語までの最短距離は確定
		    for (int j=0; j<nText; j++) { // 隣の見出し語(i)について
		    	if (map[marked][j]>0 && !fixed[j]) { // 未確定ならば
		    		int newDistance = distance[marked]+map[marked][j];
		    		// 今までの距離よりも小さければ、それを覚える
		    		if (newDistance < distance[j]){
		    			distance[j] = newDistance;
		    		}
		    	}
		    }
		}
	}
	    // まだ距離が確定していない見出し語の中で、もっとも近いものを探す
	static int minIndex(int[] distance,boolean[] fixed) {
		int idx=0;
		for (; idx<fixed.length; idx++)	// 未確定の見出し語をどれか一つ探す
		    if (!fixed[idx]) break;
			if (idx == fixed.length){
			return -1; // 未確定の見出し語が存在しなければ-1
			}
			for (int i=idx+1; i<fixed.length; i++) // 距離が小さいものを探す
				if (!fixed[i] && distance[i]<distance[idx]) idx=i;
			return idx;
	}	

}

