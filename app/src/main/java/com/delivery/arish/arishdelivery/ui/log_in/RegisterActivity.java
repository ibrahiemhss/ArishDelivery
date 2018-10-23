package com.delivery.arish.arishdelivery.ui.log_in;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.util.MyAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_MANUAL = 5;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final String[] INITIAL_PERMS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_register)
    protected RelativeLayout mRelativeLayout;
    @BindView(R.id.etName)
    protected EditText mEtName;
    @BindView(R.id.etEmail)
    protected EditText mEtEmail;
    @BindView(R.id.etPhone)
    protected EditText mEtPhone;
    @BindView(R.id.etPassword)
    protected EditText mEtPassword;
    @BindView(R.id.imgHolder)
    protected ImageView mAddImage;
    @BindView(R.id.btnRegister)
    protected Button mBtnRegister;

    private Context mContext;
    private BaseApiService mApiService;
    private ProgressDialog mDialog;
    private CircleImageView mImgHolder;
    private String mPart_image;
    private File mActualImageFile;
    private AlertDialog.Builder mBuilder;

    private AnimationDrawable mAnimationDrawable;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);

        mAnimationDrawable = MyAnimation.animateBackground(mRelativeLayout);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            // start the animation
            mAnimationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            // stop the animation
            mAnimationDrawable.stop();
        }
    }

    private void initComponents() {

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(getString(R.string.registring));
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();

                if (mPart_image == null) {
                    requestRegister();
                    mImgHolder.setVisibility(View.VISIBLE);

                } else {
                    uploadPhoto();
                }
            }
        });


        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    openGallry();
                    if (mPart_image == null) {
                        mImgHolder.setVisibility(View.VISIBLE);
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    openGallry();
                    if (mPart_image == null) {
                        mImgHolder.setVisibility(View.VISIBLE);
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    openGallry();
                    if (mPart_image == null) {
                        mImgHolder.setVisibility(View.VISIBLE);
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

                } else {
                    openGallry();
                    if (mPart_image == null) {
                        mImgHolder.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    private void uploadPhoto() {

        //  pd.show(mContext, null, "جارى التسجيل", true, false);
        mBuilder = new AlertDialog.Builder( mContext);


        File imagefile= new File(mPart_image);

        try {
            imagefile = new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath( Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(mActualImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //resizeAndCompressImageBeforeSend(mContext,part_image,imagefile.getName());

        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);

        MultipartBody.Part partImage = MultipartBody.Part.createFormData("pic", imagefile.getName(),reqBody);
        RequestBody name = createPartFromString(mEtName.getText().toString());
        RequestBody email = createPartFromString(mEtEmail.getText().toString());
        RequestBody password = createPartFromString( mEtPassword.getText().toString());
        RequestBody phone = createPartFromString(mEtPhone.getText().toString());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("password", password);
        map.put("phone", phone);


        Call<ResponseApiModel> upload = mApiService.uploadImage(map,partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @Override
            public void onResponse(Call<ResponseApiModel> call, final Response<ResponseApiModel> response) {
                Log.d( "RETRO", "ON RESPONSE  : " + response.body().toString() );
                mDialog.dismiss();
                mBuilder.setMessage( response.body().getError_msg() );

                if (response.body().getEerror().equals( "false" )) {
                    mBuilder.setMessage( response.body().getError_msg() );
                    Toast.makeText(mContext, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mContext, LogInActivity.class));
                    Log.d( "RETRO", "ON SUCCESS : " + response.body().getError_msg() );

                } else {
                    Log.d( "RETRO", "ON ERRORR : " + response.body().getError_msg() );
                    Toast.makeText(mContext, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    mBuilder.setMessage( response.body().getError_msg() );

                }
            }
            @Override
            public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                Log.d( "RETRO", "ON FAILURE : " + t.getMessage() );
            }
        });

    }
    private RequestBody createPartFromString (String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }

    private void requestRegister() {

        mBuilder=new AlertDialog.Builder( mContext );

        mApiService.registerRequest(
                mEtName.getText().toString(),
                mEtEmail.getText().toString(),
                mEtPassword.getText().toString(),
                mEtPhone.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse:start ");
                            mDialog.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);


                                if (jsonRESULTS.getString("error").equals("false")){
                                    mBuilder.setMessage( jsonRESULTS.getString("error_msg") );
                                    Toast.makeText(mContext, jsonRESULTS.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LogInActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "خطا بالاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri dataimage = data.getData();
                        String[] imageprojection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query( dataimage, imageprojection, null, null, null );

                        if (cursor != null) {
                            cursor.moveToFirst();
                            int indexImage = cursor.getColumnIndex( imageprojection[0] );
                            mPart_image = cursor.getString( indexImage );

                            if (mPart_image != null) {
                                mActualImageFile = new File( mPart_image );
                                mImgHolder.setImageBitmap( BitmapFactory.decodeFile( mActualImageFile.getAbsolutePath() ) );
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    void openGallry() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    void galleryPermissionDialog() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(RegisterActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;

        } else {
            openGallry();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for READ_EXTERNAL_STORAGE

                boolean showRationale = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // All Permissions Granted
                        galleryPermissionDialog();
                    } else {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (showRationale) {
                            showMessageOKCancel("Read Storage Permission required for this app ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            galleryPermissionDialog();

                                        }
                                    });
                        } else {
                            showMessageOKCancel("Read Storage Permission required for this app ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(RegisterActivity.this, "Please Enable the Read Storage permission in permission", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, REQUEST_CODE_MANUAL);
                                        }
                                    });

                            //proceed with logic by disabling the related features or quit the app.
                        }


                    }


                } else {
                    galleryPermissionDialog();

                }

            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder( RegisterActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}



