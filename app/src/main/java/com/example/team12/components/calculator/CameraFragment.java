package com.example.team12.components.calculator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.team12.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {
    public static final String EXTRA_INFO = "default";
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code = 1;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        btnCapture =(Button) view.findViewById(R.id.capture_btn);
        imgCapture = (ImageView) view.findViewById(R.id.captured_img);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        return view;
    }

    // https://stackoverflow.com/questions/54275594/how-to-start-camera-from-fragment-in-android

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Image_Capture_Code) {
//            if (resultCode == ) {
//                Bitmap bp = (Bitmap) data.getExtras().get("data");
//                imgCapture.setImageBitmap(bp);
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}