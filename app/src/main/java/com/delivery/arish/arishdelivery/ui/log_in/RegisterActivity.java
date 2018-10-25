package com.delivery.arish.arishdelivery.ui.log_in;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.internet.ProgressRequestBody;
import com.delivery.arish.arishdelivery.mvp.presenter.RegisterPresenter;
import com.delivery.arish.arishdelivery.util.MyAnimation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks, View.OnClickListener {

    protected static final int REQUEST_CODE_MANUAL = 5;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

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
    @BindView(R.id.addImage)
    protected ImageView mAddImage;
    @BindView(R.id.btnRegister)
    protected Button mBtnRegister;
    @BindView(R.id.imgHolder)
    protected CircleImageView mImgHolder;
    @BindView(R.id.rg_proressbar)
    protected ProgressBar mProgressBar;

    private String mPart_image;
    private File mActualImageFile;

    private AnimationDrawable mAnimationDrawable;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAnimationDrawable = MyAnimation.animateBackground(mRelativeLayout);
        mAddImage.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri dataimage = data.getData();
                        String[] imageprojection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(dataimage, imageprojection, null, null, null);

                        if (cursor != null) {
                            cursor.moveToFirst();
                            int indexImage = cursor.getColumnIndex(imageprojection[0]);
                            mPart_image = cursor.getString(indexImage);

                            if (mPart_image != null) {
                                mActualImageFile = new File(mPart_image);
                                // mImgHolder.setImageBitmap( BitmapFactory.decodeFile( mActualImageFile.getAbsolutePath() ) );
                                Glide.with(this).load(mActualImageFile).into(mImgHolder);


                            }
                        }
                    } catch (Exception e) {
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

        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            hasWriteContactsPermission = ContextCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;

        } else {
            openGallry();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                }
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for READ_EXTERNAL_STORAGE

                boolean showRationale = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // All Permissions Granted
                        galleryPermissionDialog();
                    } else {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (showRationale) {
                            showMessageOKCancel(getString(R.string.read_storage_permisio_not),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            galleryPermissionDialog();

                                        }
                                    });
                        } else {
                            showMessageOKCancel(getString(R.string.read_storage_permisio_not),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(RegisterActivity.this, getString(R.string.enable_permision), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, REQUEST_CODE_MANUAL);
                                        }
                                    });

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
        new android.app.AlertDialog.Builder(RegisterActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }

    @Override
    public void onProgressUpdate(int percentage) {
        mProgressBar.setProgress(percentage);

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        mProgressBar.setProgress(100);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnRegister:
                initRegister();
                break;

            case R.id.addImage:
                initImage();
                break;
        }
    }

    private void initRegister() {
        RegisterPresenter registerPresenter = new RegisterPresenter(this);
        if (mPart_image == null) {
            registerPresenter.requestRegister(
                    mEtName.getText().toString(),
                    mEtName.getText().toString(),
                    mEtPassword.getText().toString(),
                    mEtPhone.getText().toString());

            Log.d(TAG, "ImageUploaingCase = null");
        } else {
            registerPresenter.requestRegisterWithPhoto(
                    mPart_image,
                    mActualImageFile,
                    mEtName.getText().toString(),
                    mEtName.getText().toString(),
                    mEtPassword.getText().toString(),
                    mEtPhone.getText().toString());

            Log.d(TAG, "ImageUploaingCase = Value");

        }
    }


    private void initImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            galleryPermissionDialog();

            openGallry();

            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }
            requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {

            galleryPermissionDialog();

            openGallry();
            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }
            requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            galleryPermissionDialog();

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


}



