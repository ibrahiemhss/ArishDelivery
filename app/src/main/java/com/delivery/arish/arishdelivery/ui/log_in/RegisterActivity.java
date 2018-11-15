package com.delivery.arish.arishdelivery.ui.log_in;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.presenter.RegisterPresenter;
import com.delivery.arish.arishdelivery.util.EditTextUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static final int PICK_IMAGE_REQUEST = 1;//intent value that used to get Image from device storage

    private static final int REQUEST_CODE_MANUAL = 5;//intent value used in onRequestPermissionsResult
    private static final String[] INITIAL_PERMS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;//intent value used in ask permissions

   /* @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_register)
    protected RelativeLayout mRelativeLayout;*/
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etName)
    protected EditText mEtName;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etEmail)
    protected EditText mEtEmail;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etPhone)
    protected EditText mEtPhone;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etPassword)
    protected EditText mEtPassword;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.addImage)
    protected ImageView mAddImage;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btnRegister)
    protected Button mBtnRegister;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.imgHolder)
    protected CircleImageView mImgHolder;

    private String mPart_image;
    private File mActualImageFile;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAddImage.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageData = data.getData();//url get path of selected file

                        @SuppressWarnings("DanglingJavadoc")
                        /**all action of cursor to return string value of selected file from storage to send this value to
                         * requestRegisterWithPhoto method inside class
                         ** {@linkplain com.samples.webserver.webserversamples.log_in_system.mvp.presenter.RegisterPresenter */
                        String[] imageProjection = {MediaStore.Images.Media.DATA}; /*get all images from storage */
                        Cursor cursor = getContentResolver().//initialize cursor on specific url that come from Intent data
                                query(Objects.requireNonNull(imageData),
                                imageProjection,
                                null,
                                null,
                                null);

                        if (cursor != null) {//after make sur cursor not null
                            cursor.moveToFirst();//start move cursor
                            int indexImage = cursor.getColumnIndex(imageProjection[0]);//get index of movement of cursor
                            mPart_image = cursor.getString(indexImage);//get string value of index to get image file wanted

                            if (mPart_image != null) {
                                mActualImageFile = new File(mPart_image);//initialize File mActualImageFile from String come from cursor

                                /*set our imageView to show the file of image that came from string path cursor
                                get this string from that intent Url path that and all finally came as File*/

                                // mImgHolder.setImageBitmap( BitmapFactory.decodeFile( mActualImageFile.getAbsolutePath() ) );//show by Bitmap
                                Glide.with(this).load(mActualImageFile).into(mImgHolder);//show with Glide (Recommended)
                                // customCompressImage();
                                cursor.close();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    private void openGallery() {//open gallery intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
    }

    private void galleryPermissionDialog() {/*this dialog will appear when first open
                                            to note user to give app permission to read device storage*/
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            hasWriteContactsPermission = ContextCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            //noinspection UnnecessaryReturnStatement
            return;

        } else {
            openGallery();
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

                boolean showRationale;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // All Permissions Granted
                        galleryPermissionDialog();
                    } else {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (showRationale) {
                            showMessageOKCancel(getString(R.string.read_storage_permission),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            galleryPermissionDialog();

                                        }
                                    });
                        } else {
                            showMessageOKCancel(getString(R.string.read_storage_permission),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(RegisterActivity.this, getString(R.string.enable_permission), Toast.LENGTH_SHORT).show();
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(RegisterActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initRegister() {
        RegisterPresenter registerPresenter = new RegisterPresenter(this);

        if (mPart_image == null) {


            if (EditTextUtil.isNameCase(mEtName.getText().toString()) == 1) {
                mEtName.setError(getResources().getString(R.string.name_error));
                return;

            }

            if (EditTextUtil.isNameCase(mEtName.getText().toString()) == 2) {
                mEtName.setError(getResources().getString(R.string.name_error_char));
                return;

            }

            if (EditTextUtil.isEmailValid(mEtEmail.getText().toString())) {
                mEtEmail.setError(getResources().getString(R.string.email_error));
                return;

            }

            if (EditTextUtil.passCases(mEtPassword.getText().toString()) == 6) {
                mEtPassword.setError(getResources().getString(R.string.password_error));
                return;

            }
            if (EditTextUtil.phoneCases(mEtPhone.getText().toString()) == 10) {
                mEtPhone.setError(getResources().getString(R.string.phone_error));
                return;
            }
            registerPresenter.requestRegister(
                    mEtName.getText().toString(),
                    mEtEmail.getText().toString(),
                    mEtPassword.getText().toString(),
                    mEtPhone.getText().toString());


            Log.d(TAG, "ImageUploadingCase = null");
        } else {


            if (EditTextUtil.isNameCase(mEtName.getText().toString()) == 1) {
                mEtName.setError(getResources().getString(R.string.name_error));
                return;

            }

            if (EditTextUtil.isNameCase(mEtName.getText().toString()) == 2) {
                mEtName.setError(getResources().getString(R.string.name_error_char));
                return;

            }

            if (EditTextUtil.isEmailValid(mEtEmail.getText().toString())) {
                mEtEmail.setError(getResources().getString(R.string.email_error));
                return;

            }

            if (EditTextUtil.passCases(mEtPassword.getText().toString()) == 6) {
                mEtPassword.setError(getResources().getString(R.string.password_error));
                return;

            }
            if (EditTextUtil.phoneCases(mEtPhone.getText().toString()) == 10) {
                mEtPhone.setError(getResources().getString(R.string.phone_error));
                return;
            }
            registerPresenter.requestRegisterWithPhoto(
                    mPart_image,
                    mActualImageFile,
                    mEtName.getText().toString(),
                    mEtEmail.getText().toString(),
                    mEtPassword.getText().toString(),
                    mEtPhone.getText().toString());


            Log.d(TAG, "ImageUploadingCase = Value");

        }
    }


    private void initImage() {//to open image and ask permission for all  versions of android want permissions

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            galleryPermissionDialog();

            openGallery();

            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }
            requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {

            galleryPermissionDialog();

            openGallery();
            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }
            requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            galleryPermissionDialog();

            openGallery();
            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }
            requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);


        } else {
            openGallery();
            if (mPart_image == null) {
                mImgHolder.setVisibility(View.VISIBLE);
            }

        }
    }


}
