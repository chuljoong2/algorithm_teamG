package PackageNo1;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

class Node {
	private char cCharacter;		//문자
	private int iFrequency;			//빈도수
	
	//생성자
	public Node() {};
	public Node(char cCharacter)
	{
		this.cCharacter = cCharacter;
		this.iFrequency = 0;
	}
	public Node(char cCharacter, int iFrequency)
	{
		this.cCharacter = cCharacter;
		this.iFrequency = iFrequency;
	}
	
	//get, set함수
	public void setiFrequency(int iFrequency)
	{
		this.iFrequency = iFrequency;
	}
	public int getiFrequency()
	{
		return this.iFrequency;
	}
	public void setcCharacter(char cCharacter)
	{
		this.cCharacter = cCharacter;
	}
	public char getcCharacter()
	{
		return this.cCharacter;
	}
	
}

public class PracticeClass {

	public static void main(String[] args) {
		
		Map<String , Integer> hm = new HashMap<>();	//해시맵 선언 <문자, 빈도수>
		String sInput = "abcdeeefGggHHisjdkk !d.";		//입력된 텍스트(임시)
		String[] sInputSplit = sInput.split("");		//입력된 텍스트를 한글자씩 배열에 넣음
		int Nodelength = 0;								//노드수
		
		for(int i = 0 ; i < sInput.length() ; i++)
		{
			if(hm.containsKey(sInputSplit[i]))			//해시맵에 이미 있는 글자면 value값을 1 증가시킴
			{
				hm.put(sInputSplit[i], hm.get(sInputSplit[i])+1);
			}
			else if(!hm.containsKey(sInputSplit[i]))	//해시맵에 없는 글자면 글자를 넣고 value를 1로 설정
			{
				hm.put(sInputSplit[i], 1);
				Nodelength++;
			}
		}
		
		Node[] node = new Node[Nodelength];
		int nodeindex = 0;
		
		for(Entry<String , Integer> entrySet : hm.entrySet())	//해시맵을 Entry에 넣고 그걸 node객체에 넣음
		{
			node[nodeindex++] = new Node(entrySet.getKey().charAt(0), entrySet.getValue());
		}
		
		/*Set<String> keySet = hm.keySet();
		for (String key : keySet) {
		System.out.println(key + " : " + hm.get(key));
		}*/
		
		for(int i = 0 ; i < Nodelength ; i++)
		{
			System.out.println(node[i].getcCharacter() + " : " + node[i].getiFrequency());		
		}

	}
}