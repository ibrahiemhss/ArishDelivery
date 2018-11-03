package com.delivery.arish.arishdelivery.ui.log_in.resetPassword;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.presenter.ResetPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSenEmail extends Fragment implements View.OnClickListener {


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.et_send_email)
    protected EditText mEtxtGetEmail;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btn_send_code)
    protected Button mBtnAddEmail;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnAddEmail.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_send_code:
                ResetPasswordPresenter resetPasswordPresenter = new ResetPasswordPresenter(getActivity());
                resetPasswordPresenter.requestResetPass(mEtxtGetEmail.getText().toString());
                SharedPrefManager.getInstance(getActivity()).isNotAccess();
                if (!SharedPrefManager.getInstance(getActivity()).isNotAccess()) {
                    Log.d("checkValue", "access true");
                    SharedPrefManager.getInstance(getActivity()).setIsNotAccess(true);


                }


        }

    }
}
