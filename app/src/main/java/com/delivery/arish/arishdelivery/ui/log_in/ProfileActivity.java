package com.delivery.arish.arishdelivery.ui.log_in;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.mvp.presenter.ProfilePresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.util.EditTextUtil;
import com.delivery.arish.arishdelivery.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Gravity.CENTER;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_toolbar)
    protected Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_pp_bar)
    protected AppBarLayout mAppBarLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.extxt_prf_name)
    protected EditText mEtxtName;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_prf_name)
    protected TextView mTxtName;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.extxt_prf_email)
    protected EditText mEtxtEmail;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_prf_email)
    protected TextView mTxtEmail;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.extxt_prf_phone)
    protected EditText mEtxtPhone;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_prf_phone)
    protected TextView mTxtPhone;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_prf_location)
    protected TextView mTxtLocation;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btn_prf_edit)
    protected Button mBtnEdit;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btn_prf_confirm)
    protected Button mBtnConfirmChanges;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.img_holder_profile)
    protected CircleImageView mCircleImageViewHolder;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.img_edit_prf)
    protected ImageView mImgEdit;

    private Boolean isClick=false;


    private ProfilePresenter mProfilePresenter;
    private Dialog mUpdateDialog;
    private EditText mEdtxtGetConfirmPass;
    private String mPart_image;
    private File mActualImageFile;


    private boolean isTablet;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        mProfilePresenter=new ProfilePresenter(this);
        setupToolbar();
        // AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();

    }
    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {
        return R.layout.activity_profile;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {


        mProfilePresenter.getUserInfo(mTxtName,mTxtEmail,mTxtPhone,mCircleImageViewHolder);
    }


    @Override
    protected void setListener() {
        mBtnEdit.setOnClickListener(this);
        mBtnConfirmChanges.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                Uri imageData = data.getData();
                String[] imageProjection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(Objects.requireNonNull(imageData), imageProjection, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageProjection[0]);
                    mPart_image = cursor.getString(indexImage);

                    if (mPart_image != null) {
                        mActualImageFile = FileUtil.from(this, data.getData());
                        Glide.with(this).load(mActualImageFile).into(mCircleImageViewHolder);
                        cursor.close();

                       // mCircleImageViewHolder.setImageBitmap(BitmapFactory.decodeFile(mActualImageFile.getAbsolutePath()));
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            mCollapsingToolbarLayout.setTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            if (!isTablet) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }


    }
    @SuppressWarnings("SameReturnValue")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_prf_edit:
                if(!isClick){
                    mEtxtName.setVisibility(View.VISIBLE);
                    mTxtName.setVisibility(View.GONE);
                    mEtxtEmail.setVisibility(View.VISIBLE);
                    mTxtEmail.setVisibility(View.GONE);
                    mEtxtPhone.setVisibility(View.VISIBLE);
                    mTxtPhone.setVisibility(View.GONE);
                    mImgEdit.setVisibility(View.VISIBLE);
                    if(!mTxtName.getText().toString().equals("")&&
                       !mTxtEmail.getText().toString().equals("")&&
                       !mTxtPhone.getText().toString().equals("")){

                        mEtxtName.setText(mTxtName.getText().toString());
                        mEtxtEmail.setText(mTxtEmail.getText().toString());
                        mEtxtPhone.setText(mTxtPhone.getText().toString());

                    }

                    if (EditTextUtil.isNameCase(mEtxtName.getText().toString()) == 1) {
                        mEtxtName.setError(getResources().getString(R.string.name_error));
                        return;

                    }

                    if (EditTextUtil.isNameCase(mEtxtName.getText().toString()) == 2) {
                        mEtxtName.setError(getResources().getString(R.string.name_error_char));
                        return;

                    }
                    if (EditTextUtil.isEmailValid(mEtxtEmail.getText().toString())) {
                        mEtxtEmail.setError(getResources().getString(R.string.email_error));
                        return;

                    }


                    if (EditTextUtil.phoneCases(mEtxtPhone.getText().toString()) == 10) {
                        mEtxtPhone.setError(getResources().getString(R.string.phone_error));
                        return;
                    }


                    isClick=true;
                }else {
                    mBtnConfirmChanges.setVisibility(View.VISIBLE);
                    Log.e(TAG, "emailValue_in_profile 1= " + mEtxtEmail.getText().toString());

                    isClick=false;
                }
                break;
            case R.id.btn_prf_confirm:
                launchAddDialog(
                        mEtxtName.getText().toString(),
                        mEtxtEmail.getText().toString(),
                        mEtxtPhone.getText().toString());
                break;

            case R.id.img_edit_prf:
                chooseImage();
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void launchAddDialog( final String newName,final String newEmail, final String newPhone) {

        mUpdateDialog = new Dialog(Objects.requireNonNull(this));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(mUpdateDialog.getWindow()).getAttributes());
        lp.width = 48;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity =  CENTER;
        lp.windowAnimations = R.style.Theme_Dialog;
        mUpdateDialog.getWindow().setAttributes(lp);

        mUpdateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mUpdateDialog.setContentView(R.layout.custom_dialog_cofirm_password);

        TextView mAdd = mUpdateDialog.findViewById(R.id.txt_add);
        TextView mCancel = mUpdateDialog.findViewById(R.id.txt_cancel);
        mEdtxtGetConfirmPass = mUpdateDialog.findViewById(R.id.etxt_get_pass);

        Log.e(TAG, "emailValue_in_profile 2= " + newEmail);



        mCancel.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           mUpdateDialog.dismiss();
                                       }
                                   });


                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d(TAG,"confirm_passV="+mEdtxtGetConfirmPass.getText().toString());
                        Toast.makeText(ProfileActivity.this, "confirm_passV="+mEdtxtGetConfirmPass.getText().toString(), Toast.LENGTH_SHORT).show();

                        if (mPart_image != null) {
                            Log.e(TAG, "mPart_image_in_profile= " + mPart_image);

                            mProfilePresenter.requestUpdateInfoWithImg(
                                    mPart_image,
                                    mActualImageFile,
                                    mEdtxtGetConfirmPass.getText().toString(),
                                    newName,
                                    newEmail
                                    ,newPhone,mTxtName,mTxtEmail,mTxtPhone,mCircleImageViewHolder);

                        }else {
                            mProfilePresenter.updateUserInfo(
                                    mEdtxtGetConfirmPass.getText().toString(),
                                    newName,
                                    newEmail
                                    ,newPhone,mTxtName,mTxtEmail,mTxtPhone,mCircleImageViewHolder);

                        }

                        Log.e(TAG, "emailValue_in_profile 3= " + newEmail);


                        mEtxtName.setVisibility(View.GONE);
                        mTxtName.setVisibility(View.VISIBLE);
                        mEtxtEmail.setVisibility(View.GONE);
                        mTxtEmail.setVisibility(View.VISIBLE);
                        mEtxtPhone.setVisibility(View.GONE);
                        mTxtPhone.setVisibility(View.VISIBLE);
                        mBtnConfirmChanges.setVisibility(View.GONE);
                        mImgEdit.setVisibility(View.GONE);

                        mUpdateDialog.dismiss();
                    }
                });

        mUpdateDialog.show();

    }
}
