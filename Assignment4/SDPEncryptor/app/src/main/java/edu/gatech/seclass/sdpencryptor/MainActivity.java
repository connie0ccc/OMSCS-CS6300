package edu.gatech.seclass.sdpencryptor;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText entryText;
    private EditText argInput1;
    private EditText argInput2;
    private TextView textEncrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entryText = (EditText) findViewById(R.id.entryTextID);
        argInput1 = (EditText) findViewById(R.id.argInput1ID);
        argInput2 = (EditText) findViewById(R.id.argInput2ID);
        textEncrypted = (TextView) findViewById(R.id.textEncryptedID);

    }

    public void handleClick(View view) {
        String result;
        String value = entryText.getText().toString();
        String arg1 = argInput1.getText().toString();
        String arg2 = argInput2.getText().toString();
        if (value == "" | value.isEmpty()|value.matches("[^a-zA-Z0-9]+")) {
            entryText.setError("Invalid Entry Text");
            textEncrypted.setText("");
        }
        if (arg1.isEmpty()|arg1=="") {
            argInput1.setError("Invalid Arg Input 1");
        }
        else{
            if (parseInt(arg1) < 1 | parseInt(arg1) >= 62 | parseInt(arg1) % 2 == 0 | parseInt(arg1) % 31 == 0){
                argInput1.setError("Invalid Arg Input 1");
            }
        }
        if (arg2.isEmpty()|arg2==""){
            argInput2.setError("Invalid Arg Input 2");
        }
        else{
            if (parseInt(arg2) < 1 | parseInt(arg2) >= 62) {
                argInput2.setError("Invalid Arg Input 2");

            }
        }
        if (!arg1.isEmpty() && !arg2.isEmpty()&& arg2!=""&& arg1!="" && value != "" && !value.isEmpty()&& !value.matches("[^a-zA-Z0-9]+")) {
            if (parseInt(arg1) >= 1 && parseInt(arg1) < 62 && parseInt(arg1) % 2 != 0 && parseInt(arg1) % 31 != 0 && parseInt(arg2) >= 1 && parseInt(arg2) < 62) {
                int a1 = Integer.parseInt(arg1);
                int a2 = Integer.parseInt(arg2);
                result = Encrypt(value, a1, a2);
                textEncrypted.setText(result);
            }
            else{
                textEncrypted.setText("");
            }
        }
    }

    private String Encrypt(String inputstring, int a1, int a2) {
            List<String> convert_list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
            String newstring = "";
            String newch = "";
            for (int i = 0; i < inputstring.length(); ++i) {
                char ch = inputstring.charAt(i);
                String s_ch = String.valueOf(ch);

                if (convert_list.contains(s_ch)) {
                    int pos = convert_list.indexOf(s_ch);
                    int converter = (pos * a1 + a2) % 62;
                    newch = convert_list.get(converter);
                } else {
                    newch = s_ch;
                }
                newstring += newch;
            }
            return newstring;
        }
    }
