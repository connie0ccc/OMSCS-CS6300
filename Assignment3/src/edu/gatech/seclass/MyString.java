package edu.gatech.seclass;
import java.util.Arrays;
import java.util.List;
public class MyString implements MyStringInterface {
    public MyString(){}
    public String mystring;
    public String getString()
    {
        if(mystring.isEmpty())
            return null;
        else
        {
            return mystring;
        }
    }
    public void setString(String string) {
        mystring = string;
        if(mystring == null){
            throw new NullPointerException();
        }
        if(mystring == ""){
            throw new IllegalArgumentException();
        }
        if(mystring.matches("[^a-zA-Z0-9]+")){
            throw new IllegalArgumentException();
        }
        if (mystring == easterEgg){
            throw new IllegalArgumentException("Easter Egg");
        }
    }
    public int countAlphabeticWords()
    {
        String newstring = getString();
        if(newstring.isEmpty())
            return 0;
        if (newstring ==null) {
            throw new NullPointerException("string is null");
        }

        int count = 0;
        boolean IsAlphabet=false;

        for (int i = 0; i <newstring.length(); i++) {
            if ((newstring.charAt(i) >= 'A' &&newstring.charAt(i) <= 'Z') || (newstring.charAt(i) >= 'a' && newstring.charAt(i) <= 'z')) {
                if (IsAlphabet)
                    continue;
                IsAlphabet = true;
                count++;
            }
            else {
                IsAlphabet= false;
            }
        }
        return count;
    }
    public String encrypt(int arg1, int arg2)
    {
        String oldstring = getString();
        if (oldstring ==null) {
            throw new NullPointerException("string is null");
        }
        if (arg1%2 == 0 | arg1%31 == 0){
            throw new IllegalArgumentException("Arg1 is not co-prime to 62.");
        }
        if (arg1 > 61 | arg1 < 1 | arg2 > 61 | arg2 < 1){
            throw new IllegalArgumentException("Arg out of range.");
        }

        List<String> convert_list= Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        String newstring = "";
        String newch ="";
        for (int i = 0; i < oldstring.length(); ++i) {
            char ch = oldstring.charAt(i);
            String s_ch=String.valueOf(ch);

            if(convert_list.contains(s_ch)){
                int pos = convert_list.indexOf(s_ch);
                int converter = (pos*arg1 + arg2) % 62;
                newch=convert_list.get(converter);}
            else{
                newch =s_ch;}
            newstring +=newch;
        }
        return newstring;
    }
    public void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition)
    {
        int len = mystring.length();
        if (mystring == null){
            throw new NullPointerException();
        }
        if (firstPosition > finalPosition || firstPosition < 1) {
            throw new IllegalArgumentException("Positions invalid.");
        }
        if (firstPosition < 1 || finalPosition > len){
            throw new MyIndexOutOfBoundsException("Final position out of range");
        }

        String headstring = mystring.substring(0, firstPosition-1);
        String positioned_string = mystring.substring(firstPosition - 1, finalPosition);
        positioned_string = positioned_string.replaceAll("0", "Zero");
        positioned_string = positioned_string.replaceAll("1", "One");
        positioned_string = positioned_string.replaceAll("2", "Two");
        positioned_string = positioned_string.replaceAll("3", "Three");
        positioned_string = positioned_string.replaceAll("4", "Four");
        positioned_string = positioned_string.replaceAll("5", "Five");
        positioned_string = positioned_string.replaceAll("6", "Six");
        positioned_string = positioned_string.replaceAll("7", "Seven");
        positioned_string = positioned_string.replaceAll("8", "Eight");
        positioned_string = positioned_string.replaceAll("9", "Nine");

        String tailstring = mystring.substring(finalPosition, len);
        String newstring= headstring + positioned_string + tailstring;
        setString(newstring);
        System.out.println(newstring);
    }
}
