package com.example.fragmentdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class BottomFragment extends Fragment {

    Button button;
    SendInfo sendInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
         button = view.findViewById(R.id.button2);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 sendInfo.update("UPDATE");
             }
         });
        return view;

    }

    public void onAttach(Context context){
        super.onAttach(context);
        sendInfo = (SendInfo) context;
    }

    public interface SendInfo{
        void update(String str);
    }
}