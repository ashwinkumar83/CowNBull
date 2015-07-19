package com.ash.android.cowsbulls.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;

import android.content.Context;

import com.ash.android.cowsbulls.utility.AssetsPropertyReader;
import com.ash.android.cowsbulls.vo.CowBullResultVo;

public class CowAndBullEngine {
  private String playWord = null;
  Properties props = null;
 
  public CowAndBullEngine(Context context){
	try {
	AssetsPropertyReader sp = new AssetsPropertyReader(context);
	props = sp.getProperties("DictionaryData.properties");
	//CowAndBull cb= new CowAndBull();
	//System.out.println(s);
	String s = RandomStringUtils.randomAlphabetic(1).toUpperCase();
	String[] inputData = props.getProperty(s).split(",");
	playWord = getPlayWord(inputData);
	//System.out.println(cb.checkDuplicateChars("mall"));
	//System.out.println(RandomStringUtils.randomAlphabetic(1));
	//System.out.println("Values " + inputData.length);
	//System.out.println(getPlayWord(inputData));
	} catch (Exception e) {
		e.printStackTrace();
	}
	
  }
  
 public CowAndBullEngine(Context context,String playWord){
		this.playWord = playWord;
		AssetsPropertyReader sp = new AssetsPropertyReader(context);
		props = sp.getProperties("DictionaryData.properties");
	  }

 public String getPlayWord(){
	 return this.playWord;
 }
private String getPlayWord(String[] inputData) throws Exception{
	int x = new Random().nextInt(inputData.length);
	String p = inputData[x].toString().trim();
	if(checkDuplicateChars(p)){
		return getPlayWord(inputData);
	}
	//System.out.println(p);
	return p;
}

public boolean validatePlayWord(String playWord) throws Exception{
	char[] c = {playWord.charAt(0)};
	String strPlayWord=new String(c).toUpperCase();
	String[] inputData = props.getProperty(strPlayWord).split(",");
	List<String> wordList= Arrays.asList(inputData);
	System.out.println("Result List:: " + wordList);
	System.out.println("Result playWord:: " + playWord.toLowerCase());
	System.out.println("Result:: " + wordList.contains(playWord.toLowerCase()));

	return wordList.contains(playWord.toLowerCase());
}

public CowBullResultVo comparePlayerWords(String guessWord) throws Exception{
	CowBullResultVo result = new CowBullResultVo() ;
	/*if(checkDuplicateChars(guessWord)){
		result.setResult("Invalid word.Contains duplicate letters!!");
		result.setStatus(false);
		System.out.println("Invalid word.Contains duplicate letters!!");
	}else{*/
	result = compareEngine(playWord.toUpperCase(),guessWord.toUpperCase());
	//}
	return result;
}

private CowBullResultVo compareEngine(String playWord, String guessWord) {
	CowBullResultVo cr = new CowBullResultVo();
	char[] pc = playWord.toCharArray();
	char[] gc = guessWord.toCharArray();
	StringBuilder result = new StringBuilder();
	int cows = 0,bulls =0 ;
	for(int i=0;i<gc.length;i++){
		for(int j=0;j<pc.length;j++){
			if(gc[i]==pc[j]){
				if(i==j){
					bulls++;
				}else{
					cows++;
				}
			}
		}
	}
	cr.setResult(result.append(bulls).append("B").append("|").append(cows).append("C").toString());
	cr.setStatus(false);
	if(playWord.equalsIgnoreCase(guessWord)){
		cr.setStatus(true);
	}
	return cr;
}


public boolean checkDuplicateChars(String s) throws Exception{
	if(null==s) throw new Exception("Value is null");
	
	Set<Character> st = new HashSet<Character>();
	for(Character c:s.toCharArray()){
		st.add(c);
	}
	//System.out.println("String word: " +  s);
	//System.out.println("S length" + s.length());
	//System.out.println("set Size: " +st.size());
	if(s.length()!=(st.size()))
	return true;
	
	return false;
}
}
