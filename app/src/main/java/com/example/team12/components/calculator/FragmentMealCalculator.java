package com.example.team12.components.calculator;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team12.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentMealCalculator extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    private static final String IMG_URL = "https://firebasestorage.googleapis.com/v0/b/calo-a7a97.appspot.com/o/egg.png?alt=media&token=72163b7e-056a-40b8-909e-ea9e92248aa3";
    private static final String API_USER_TOKEN = "4c89d7bacc291b3bf1a1535dd9461db2e464b782";
    private static final String LOGMEAL_API_URL = "https://api.logmeal.es/v2";
    ImageView captureImage;
    TextView captureText;
    Button saveBtn;
    Button submitBtn;
    OutputStream outputStream;

    private final ActivityResultLauncher<Intent> displayCaptureResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (data != null && result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    captureImage.setImageURI(uri);
                } else {
                    Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mealCalculatorView = inflater.inflate(R.layout.fragment_calculator_meal, container, false);
        return mealCalculatorView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        captureImage = view.findViewById(R.id.meal_cal_image);
        captureText = view.findViewById(R.id.meal_cal_capture);
        saveBtn = view.findViewById(R.id.meal_cal_save_btn);
        submitBtn = view.findViewById(R.id.meal_cal_btn);

        captureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(FragmentMealCalculator.this)
                        .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent(intent -> {
                            displayCaptureResult.launch(intent);
                            return null;
                        });
            }
        });
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(FragmentMealCalculator.this)
                        .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent(intent -> {
                            displayCaptureResult.launch(intent);
                            return null;
                        });
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(FragmentMealCalculator.this.getContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(FragmentMealCalculator.this.requireActivity(),
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetFoodName();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                saveImage();
            else
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {
        Uri uri;
        ContentResolver contentResolver = requireActivity().getContentResolver();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri imageUri = contentResolver.insert(uri, contentValues);

        try {
            BitmapDrawable drawable = (BitmapDrawable) captureImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            outputStream = contentResolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);
            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Image not saved", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onGetFoodName() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                // Step 1: Download Image
                byte[] imgData = getImg();

                if (imgData != null) {
                    // Step 2: Upload Image for Segmentation
                    String imageId = uploadImageForSegmentation(imgData);

                    // Step 3: Get Nutritional Info
                    getNutritionalInfo(imageId);
                }
            } catch (IOException | JSONException e) {
                Log.e("ExecutorError: ", String.valueOf(e));
            }
        });
    }

    private byte[] getImg() {
        Bitmap bitmap = ((BitmapDrawable) captureImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

    private String uploadImageForSegmentation(byte[] imgData) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.jpg", RequestBody.create(MediaType.parse("image/*"), imgData))
                .build();

        Request request = new Request.Builder()
                .url(LOGMEAL_API_URL + "/image/segmentation/complete")
                .addHeader("Authorization", "Bearer " + API_USER_TOKEN)
                .post(requestBody)
                .build();

        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse.getString("imageId");
            } else {
                Log.d("UploadImageError: ", response.body().string());
                throw new IOException("Failed to upload image for segmentation");
            }
        } catch (JSONException e) {
            Log.d("DownloadImageErrorbutdifferent: ", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    private void getNutritionalInfo(String imageId) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        json.put("imageId", imageId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

        Request request = new Request.Builder()
                .url(LOGMEAL_API_URL + "/recipe/nutritionalInfo")
                .addHeader("Authorization", "Bearer " + API_USER_TOKEN)
                .post(requestBody)
                .build();

        try (Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JSONObject jsonObject = new JSONObject(jsonData);
                Log.d("NutritionalInfo: ",jsonObject.toString(4)); // Indented with 4 spaces
            } else {
                Log.d("NutritionalInfoError: ", response.body().string());
                throw new IOException("Failed to get nutritional info");
            }
        }
    }
}