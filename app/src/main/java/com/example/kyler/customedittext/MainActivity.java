package com.example.kyler.customedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edittext1)
    EditTextCustomable edittext1;

    @BindView(R.id.edittext2)
    EditTextCustomable edittext2;

    @BindView(R.id.edittext3)
    EditTextCustomable edittext3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        edittext1.setErrorBackground(DrawBorder.TOP,DrawBorder.RIGHT);
        edittext2.setErrorBackground(DrawBorder.TOP_BOTTOM, DrawBorder.LEFT);
        edittext3.setNormalBackground(DrawBorder.TOP_RIGHT_BOTTOM);


//        CustomDrawable.setKylerDrawable(ed1);
//        CustomDrawable.setKylerDrawable(ed2);
//        ed1.addTextChangedListener2(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length()==8){
//                    ed1.setError();
//                    ed2.requestFocus();
//                    ed2.setText("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        ed2.addTextChangedListener2(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length()==8){
//                    ed2.setError();
//                    ed1.requestFocus();
//                    ed1.setText("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }
}
