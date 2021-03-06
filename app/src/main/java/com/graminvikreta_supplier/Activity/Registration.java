package com.graminvikreta_supplier.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.andreabaccega.widget.FormEditText;
import com.graminvikreta_supplier.Adapter.AssignToAdapter;
import com.graminvikreta_supplier.Extra.RecyclerTouchListener;
import com.graminvikreta_supplier.Model.AssignResponse;
import com.graminvikreta_supplier.Model.LoginResponse;
import com.graminvikreta_supplier.R;
import com.graminvikreta_supplier.Retrofit.Api;
import com.graminvikreta_supplier.Retrofit.ApiInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.itextpdf.layout.element.Text;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    @BindViews({R.id.firstName, R.id.lastName, R.id.mobileNumber, R.id.address, R.id.aadharCard, R.id.panCard, R.id.planterArea, R.id.dryArea, R.id.emailID, R.id.password, R.id.bankName,
            R.id.accountNumber, R.id.branchName, R.id.iFSC, R.id.confirmPassword, R.id.companyName})
    List<FormEditText> formEditTexts;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.companyTxt)
    TextView companyTxt;
    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;
    private ChoosePhotoHelper choosePhotoHelper;
    Bitmap bitmap;
    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    Matcher matcher;
    boolean isEmailValid, isPasswordValid, isPasswordVisible;
    private List<String> rootFilters;
    private List<AssignResponse> assignResponseList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });


        formEditTexts.get(0).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(1).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(3).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(6).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(7).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(10).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(12).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        formEditTexts.get(15).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        formEditTexts.get(5).setFilters(new InputFilter[] {new InputFilter.LengthFilter(10), new InputFilter.AllCaps()});
        formEditTexts.get(13).setFilters(new InputFilter[] {new InputFilter.LengthFilter(11), new InputFilter.AllCaps()});

        formEditTexts.get(0).setSelection(formEditTexts.get(0).getText().toString().length());
        formEditTexts.get(1).setSelection(formEditTexts.get(1).getText().toString().length());
        formEditTexts.get(2).setSelection(formEditTexts.get(2).getText().toString().length());
        formEditTexts.get(3).setSelection(formEditTexts.get(3).getText().toString().length());
        formEditTexts.get(4).setSelection(formEditTexts.get(4).getText().toString().length());
        formEditTexts.get(5).setSelection(formEditTexts.get(5).getText().toString().length());
        formEditTexts.get(6).setSelection(formEditTexts.get(6).getText().toString().length());
        formEditTexts.get(7).setSelection(formEditTexts.get(7).getText().toString().length());
        formEditTexts.get(8).setSelection(formEditTexts.get(8).getText().toString().length());
        formEditTexts.get(9).setSelection(formEditTexts.get(9).getText().toString().length());
        formEditTexts.get(10).setSelection(formEditTexts.get(10).getText().toString().length());
        formEditTexts.get(11).setSelection(formEditTexts.get(11).getText().toString().length());
        formEditTexts.get(12).setSelection(formEditTexts.get(12).getText().toString().length());
        formEditTexts.get(13).setSelection(formEditTexts.get(13).getText().toString().length());
        formEditTexts.get(14).setSelection(formEditTexts.get(14).getText().toString().length());
        formEditTexts.get(15).setSelection(formEditTexts.get(15).getText().toString().length());


        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(imageView)
                                .load(photo)
                                .apply(RequestOptions.placeholderOf(R.drawable.profileimg))
                                .into(imageView);
                    }
                });


        rootFilters = Arrays.asList(this.getResources().getStringArray(R.array.filter_category));
        for (int i = 0; i < rootFilters.size(); i++) {
            AssignResponse model = new AssignResponse();
            model.setName(rootFilters.get(i));
            assignResponseList.add(model);
        }

    }

    @OnClick({R.id.profile_add, R.id.back, R.id.signIn, R.id.companyTxt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                moveTaskToBack(false);
                break;

            case R.id.companyTxt:

                final Dialog dialog = new Dialog(Registration.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.dialog_list);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);

                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                TextView close = dialog.findViewById(R.id.close);

                //close.setText("Select Supplier Type");

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Registration.this, recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        AssignResponse countryResponse = assignResponseList.get(position);
                        companyTxt.setText(assignResponseList.get(position).getName());
                        dialog.dismiss();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

                AssignToAdapter assignToAdapter = new AssignToAdapter(Registration.this, assignResponseList);
                recyclerView.setLayoutManager(new LinearLayoutManager(Registration.this));
                recyclerView.setAdapter(assignToAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                assignToAdapter.notifyDataSetChanged();
                recyclerView.setHasFixedSize(true);

                dialog.show();

                break;

            case R.id.profile_add:
                choosePhotoHelper.showChooser();
                break;

            case R.id.signIn:

                if (!companyTxt.getText().toString().trim().equals("")) {

                    if (formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity() && formEditTexts.get(2).testValidity() && formEditTexts.get(3).testValidity() && formEditTexts.get(4).testValidity()
                            && formEditTexts.get(5).testValidity() && formEditTexts.get(8).testValidity() && formEditTexts.get(9).testValidity() && formEditTexts.get(14).testValidity()) {

                        if (formEditTexts.get(2).getText().toString().trim().length() == 10) {

                            if (formEditTexts.get(4).getText().toString().trim().length() == 12) {

                                if (formEditTexts.get(5).getText().toString().trim().length() == 10) {

                                    matcher = pattern.matcher(formEditTexts.get(5).getText().toString());
                                    if (matcher.matches()) {

                                            if (formEditTexts.get(14).getText().toString().equals(formEditTexts.get(9).getText().toString())) {

                                                String imageString = "";

                                                if (imageView.getDrawable() != null) {
                                                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                                    imageString = getStringImage(bitmap);

                                                    registration(imageString, companyTxt.getText().toString(), formEditTexts.get(15).getText().toString(), formEditTexts.get(0).getText().toString(), formEditTexts.get(1).getText().toString(), formEditTexts.get(2).getText().toString(), formEditTexts.get(3).getText().toString(),
                                                            formEditTexts.get(4).getText().toString(), formEditTexts.get(5).getText().toString(), formEditTexts.get(6).getText().toString(), formEditTexts.get(7).getText().toString(),
                                                            formEditTexts.get(8).getText().toString(), formEditTexts.get(9).getText().toString(), formEditTexts.get(10).getText().toString(), formEditTexts.get(11).getText().toString(),
                                                            formEditTexts.get(12).getText().toString(), formEditTexts.get(13).getText().toString());
                                                } else {
                                                    Toasty.error(Registration.this, "Select profile image", Toasty.LENGTH_SHORT, true).show();
                                                }
                                            } else {
                                                formEditTexts.get(14).setError("Enter valid confirm password");
                                                formEditTexts.get(14).requestFocus();
                                            }

                                    } else {
                                        formEditTexts.get(5).setError("Enter valid pan number");
                                        formEditTexts.get(5).requestFocus();
                                    }

                                } else {
                                    formEditTexts.get(5).setError("Enter valid pan number");
                                    formEditTexts.get(5).requestFocus();
                                }

                            } else {
                                formEditTexts.get(4).setError("Enter valid aadhar number");
                                formEditTexts.get(4).requestFocus();
                            }

                        } else {
                            formEditTexts.get(2).setError("Enter valid mobile number");
                            formEditTexts.get(2).requestFocus();
                        }
                    }
                }else {
                    Toast.makeText(Registration.this, "Select company type", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void registration(String profilePhoto, String companyType, String companyName, String firstName, String lastName, String mobileNumber, String address, String aadharCard, String panCard, String planterArea, String dryArea, String emailId, String password, String bankName, String accountNumber, String branchNme, String iFSC) {

        ProgressDialog progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Account is creating");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.Registration(profilePhoto, companyType, companyName, firstName, lastName, mobileNumber, address, aadharCard, panCard, planterArea, dryArea, emailId, password, bankName, accountNumber, branchNme, iFSC);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus().equals("true")){
                    progressDialog.dismiss();
                    Toast.makeText(Registration.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                } else if (response.body().getStatus().equals("false")){
                    progressDialog.dismiss();
                    Toast.makeText(Registration.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Error", ""+t.getMessage());
            }
        });

    }

    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }
}