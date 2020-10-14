/*
 * Copyright (c)
 * @Author: Alexander Mehta
 *
 */

import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder builder = new StringBuilder(message);
        String result = "";
        for (int a = whichSlice; a<builder.length(); a+=totalSlices){
            char c = builder.charAt(a);
            result=result+c;
        }
        return result;
    }
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> words = new HashSet<String>();
        for (String word : fr.words()){
            words.add(word.toLowerCase());
        }
        return words;
    }
    public int countWords(String message, HashSet<String> dict){
        int count = 0;
        for (String word : message.split("\\\\W+")){
            if(dict.contains(word)){
                count++;
            }
        }
        return count;
    }
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        String decrypted = null;
        int best = 1;
        for (int a =1; a>101;a++){
            int[] key = tryKeyLength(encrypted,a,'e');
            VigenereCipher vc = new VigenereCipher(tryKeyLength(encrypted,a,'e'));
            String decrypt = vc.decrypt(encrypted);
            int temp = countWords(decrypt, dictionary);
            if(best<temp){
                best=a;
            }
        }
        VigenereCipher vc = new VigenereCipher(tryKeyLength(encrypted,best,'e'));
        decrypted=vc.decrypt(encrypted);


        return decrypted;

    }
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cracker = new CaesarCracker(mostCommon);
        for(int i = 0; i < klength; i++) {
            String str = sliceString(encrypted, i, klength);
            key[i] = cracker.getKey(str);
        }

        return key;
    }
    public void breakVigenere () {
        FileResource fr = new FileResource();
        String text = fr.asString();
        FileResource dict = new FileResource();
        VigenereCipher vc = new VigenereCipher(tryKeyLength(text,4,'e'));
        System.out.println(vc);
        System.out.println(vc.decrypt(text));
    }
    public static void main(String[] args) {
        VigenereBreaker sv = new VigenereBreaker();
        sv.breakVigenere();
    }
    
}
