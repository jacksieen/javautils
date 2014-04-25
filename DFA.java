import java.util.*;
import java.io.*;

class DFA
{
	boolean recognizeString(int move[][], int accept_state[], String word)
	{
		int curState = 0;
		int i, strn;
		for (i=0; i<word.length(); i++){
			/* if (word.charAt(i) == 'a') strn = 0; */
			/* else if (word.charAt(i) == 'b') strn =1; */
			/* else return false; */
			strn = word.charAt(i) - 'a';
			if (strn >= 26) return false;
			curState = move[curState][strn];
		}
		i = 0;
		while (curState != accept_state[i]){
			i++;
			if (i==accept_state.length) return false;
		}
		return true;
	}
	public static void main(String args[]) throws IOException
	{
		int n, m;
		BufferedReader in = new BufferedReader(new FileReader("DFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		while (n != 0)
		{
			int[][] move = new int[n][m];
			for(int i=0; i<n; i++)
			{
				st = new StringTokenizer(in.readLine());
				for (int j=0; j<m; j++)
					move[i][j] = Integer.parseInt(st.nextToken());
			}
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			for (int i=0; i<accept.length; i++) accept[i] = Integer.parseInt(temp[i]);
			String word = in.readLine();
			while (word.compareTo("#") != 0)
			{
				DFA dfa = new DFA();
				if (dfa.recognizeString(move, accept, word)) System.out.println("YES"); else System.out.println("NO");
				word = in.readLine();
			}
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
		}
	}
}
