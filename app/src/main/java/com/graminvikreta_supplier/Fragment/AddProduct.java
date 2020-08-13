package com.graminvikreta_supplier.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.andreabaccega.widget.FormEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.graminvikreta_supplier.Activity.Login;
import com.graminvikreta_supplier.Activity.MainPage;
import com.graminvikreta_supplier.Adapter.CategoryTxtAdapter;
import com.graminvikreta_supplier.Adapter.SubCategoryTxtAdapter;
import com.graminvikreta_supplier.Extra.DetectConnection;
import com.graminvikreta_supplier.Model.CategoryResponse;
import com.graminvikreta_supplier.Model.LoginResponse;
import com.graminvikreta_supplier.Model.ProductResponse;
import com.graminvikreta_supplier.Adapter.ProductTxtAdapter;
import com.graminvikreta_supplier.R;
import com.graminvikreta_supplier.Retrofit.Api;
import com.graminvikreta_supplier.Retrofit.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProduct extends Fragment {

    View view;
    @BindViews({R.id.productDescription, R.id.productStock, R.id.productUnit, R.id.productPrice})
    List<FormEditText> formEditTexts;
    @BindViews({R.id.productPrimaryImage, R.id.productSecondaryImage})
    List<ImageView> imageViews;
    private ChoosePhotoHelper choosePhotoHelper, choosePhotoHelper1;
    Bitmap bitmap, bitmap1;
    public static String firstImage="", secondImage="", categoryId, subCategoryId, productId;
    public static Dialog dialog;
    RecyclerView recyclerView;
    TextView close;
    FormEditText searchEdit;
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    List<CategoryResponse> searchCategoryResponseList = new ArrayList<>();
    List<CategoryResponse> subcategoryResponseList = new ArrayList<>();
    List<CategoryResponse> searchSubcategoryResponseList = new ArrayList<>();
    List<ProductResponse> productResponseList = new ArrayList<>();
    List<ProductResponse> searchProductResponseList = new ArrayList<>();
    public static TextView categoryTxt, subCategoryTxt, productTxt;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_product, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        initViews();

        choosePhotoHelper1 = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(imageViews.get(0))
                                .load(photo)
                                .apply(RequestOptions.placeholderOf(R.drawable.defaultimage))
                                .into(imageViews.get(0));
                    }
                });

        choosePhotoHelper1 = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(imageViews.get(1))
                                .load(photo)
                                .apply(RequestOptions.placeholderOf(R.drawable.defaultimage))
                                .into(imageViews.get(01));
                    }
                });



        return view;

    }

    private void initViews() {

        categoryTxt = view.findViewById(R.id.categorySpin);
        subCategoryTxt = view.findViewById(R.id.subCategorySpin);
        productTxt = view.findViewById(R.id.productSpin);

    }

    @OnClick({R.id.categorySpin, R.id.subCategorySpin, R.id.productSpin, R.id.submit, R.id.primaryImage, R.id.secondaryImage})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.primaryImage:
                firstImage="first";
                choosePhotoHelper.showChooser();
                break;

            case R.id.secondaryImage:
                secondImage="second";
                choosePhotoHelper1.showChooser();
                break;

            case R.id.categorySpin:
                if (categoryResponseList.size()>0) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.product_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);
                    searchEdit = dialog.findViewById(R.id.searchEdit);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    searchEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            Log.d("text", "" + editable.toString());
                            String s = editable.toString();
                            searchCategoryResponseList = new ArrayList<>();
                            if (s.length() > 0) {
                                for (int i = 0; i < categoryResponseList.size(); i++)
                                    if (categoryResponseList.get(i).getCategoryType().toLowerCase().contains(s.toLowerCase().trim())) {
                                        searchCategoryResponseList.add(categoryResponseList.get(i));
                                    }
                                if (searchCategoryResponseList.size() < 1) {
                                    Toast.makeText(getActivity(), "Record Not Found", Toast.LENGTH_SHORT).show();
                                } else {

                                }
                                Log.e("size", searchCategoryResponseList.size() + "" + categoryResponseList.size());
                            } else {
                                searchCategoryResponseList = new ArrayList<>();
                                for (int i = 0; i < categoryResponseList.size(); i++) {
                                    searchCategoryResponseList.add(categoryResponseList.get(i));
                                }

                            }

                            try {

                                CategoryTxtAdapter categoryTxtAdapter = new CategoryTxtAdapter(getActivity(), searchCategoryResponseList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(categoryTxtAdapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                categoryTxtAdapter.notifyDataSetChanged();
                                categoryTxtAdapter.notifyItemInserted(searchCategoryResponseList.size() - 1);
                                recyclerView.setHasFixedSize(true);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });

                    try {

                        CategoryTxtAdapter categoryTxtAdapter = new CategoryTxtAdapter(getActivity(), categoryResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(categoryTxtAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        categoryTxtAdapter.notifyDataSetChanged();
                        categoryTxtAdapter.notifyItemInserted(categoryResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();
                } else {

                    categoryTxt.setHint("Select Category");
                    subCategoryTxt.setHint("Select Sub Category");
                    productTxt.setHint("Select Product");

                    categoryTxt.setText("");
                    subCategoryTxt.setText("");
                    productTxt.setText("");

                    Toast.makeText(getActivity(), "No Category Found", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.subCategorySpin:

                if (subcategoryResponseList.size()>0) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.product_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);
                    searchEdit = dialog.findViewById(R.id.searchEdit);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    searchEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            Log.d("text", "" + editable.toString());
                            String s = editable.toString();
                            searchSubcategoryResponseList = new ArrayList<>();
                            if (s.length() > 0) {
                                for (int i = 0; i < subcategoryResponseList.size(); i++)
                                    if (subcategoryResponseList.get(i).getSub_type_name().toLowerCase().contains(s.toLowerCase().trim())) {
                                        searchSubcategoryResponseList.add(subcategoryResponseList.get(i));
                                    }
                                if (searchSubcategoryResponseList.size() < 1) {
                                    Toast.makeText(getActivity(), "Record Not Found", Toast.LENGTH_SHORT).show();
                                } else {

                                }
                                Log.e("size", searchSubcategoryResponseList.size() + "" + subcategoryResponseList.size());
                            } else {
                                searchSubcategoryResponseList = new ArrayList<>();
                                for (int i = 0; i < subcategoryResponseList.size(); i++) {
                                    searchSubcategoryResponseList.add(subcategoryResponseList.get(i));
                                }

                            }

                            try {

                                SubCategoryTxtAdapter subCategoryTxtAdapter = new SubCategoryTxtAdapter(getActivity(), searchSubcategoryResponseList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(subCategoryTxtAdapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                subCategoryTxtAdapter.notifyDataSetChanged();
                                subCategoryTxtAdapter.notifyItemInserted(searchSubcategoryResponseList.size() - 1);
                                recyclerView.setHasFixedSize(true);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });

                    try {

                        SubCategoryTxtAdapter subCategoryTxtAdapter = new SubCategoryTxtAdapter(getActivity(), subcategoryResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(subCategoryTxtAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        subCategoryTxtAdapter.notifyDataSetChanged();
                        subCategoryTxtAdapter.notifyItemInserted(subcategoryResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();

                }else {

                    subCategoryTxt.setHint("Select Sub Category");
                    productTxt.setHint("Select Product");

                    subCategoryTxt.setText("");
                    productTxt.setText("");

                    Toast.makeText(getActivity(), "No Sub Category Found", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.productSpin:

                if (productResponseList.size()>0) {

                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.product_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(true);

                    recyclerView = dialog.findViewById(R.id.recyclerView);
                    close = dialog.findViewById(R.id.close);
                    searchEdit = dialog.findViewById(R.id.searchEdit);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                        }
                    });

                    searchEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            Log.d("text", "" + editable.toString());

                            String s = editable.toString();
                            searchProductResponseList = new ArrayList<>();
                            if (s.length() > 0) {
                                for (int i = 0; i < productResponseList.size(); i++)
                                    if (productResponseList.get(i).getProductName().toLowerCase().contains(s.toLowerCase().trim())) {
                                        searchProductResponseList.add(productResponseList.get(i));
                                    }
                                if (searchProductResponseList.size() < 1) {
                                    Toast.makeText(getActivity(), "Record Not Found", Toast.LENGTH_SHORT).show();
                                } else {

                                }

                            } else {
                                searchProductResponseList = new ArrayList<>();
                                for (int i = 0; i < productResponseList.size(); i++) {
                                    searchProductResponseList.add(productResponseList.get(i));
                                }

                            }

                            try {

                                ProductTxtAdapter productTxtAdapter = new ProductTxtAdapter(getActivity(), searchProductResponseList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(productTxtAdapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                productTxtAdapter.notifyDataSetChanged();
                                productTxtAdapter.notifyItemInserted(searchProductResponseList.size() - 1);
                                recyclerView.setHasFixedSize(true);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });

                    try {

                        ProductTxtAdapter productTxtAdapter = new ProductTxtAdapter(getActivity(), productResponseList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(productTxtAdapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        productTxtAdapter.notifyDataSetChanged();
                        productTxtAdapter.notifyItemInserted(productResponseList.size() - 1);
                        recyclerView.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.show();

                }else {

                    productTxt.setHint("Select Product");
                    productTxt.setText("");

                    Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.submit:

                if (!categoryTxt.getText().toString().isEmpty()) {

                    if (!subCategoryTxt.getText().toString().isEmpty()) {

                        if (!productTxt.getText().toString().isEmpty()) {

                            if (formEditTexts.get(0).testValidity() && formEditTexts.get(1).testValidity()
                                    && formEditTexts.get(2).testValidity() && formEditTexts.get(3).testValidity()){

                                if (imageViews.get(0).getDrawable() != null) {

                                    bitmap = ((BitmapDrawable) imageViews.get(0).getDrawable()).getBitmap();
                                    String productImage = getStringImage(bitmap);
                                    String secondImage="";

                                    if (imageViews.get(1).getDrawable()==null){
                                        secondImage = "";
                                    } else {
                                        bitmap1 = ((BitmapDrawable) imageViews.get(1).getDrawable()).getBitmap();
                                        secondImage = getStringImage(bitmap1);
                                    }

                                    addProduct(categoryId, subCategoryId, productId, formEditTexts.get(0).getText().toString(), formEditTexts.get(1).getText().toString(),
                                            formEditTexts.get(2).getText().toString(), formEditTexts.get(3).getText().toString(), productImage, secondImage);

                                } else {
                                    Toasty.error(getActivity(), "Select Product Primary Image", Toasty.LENGTH_SHORT, true).show();
                                }
                            }

                        } else {
                            productTxt.setError("Select Product");
                            productTxt.requestFocus();
                        }

                    } else {
                        subCategoryTxt.setError("Select Sub Category");
                        subCategoryTxt.requestFocus();
                    }

                } else {
                    categoryTxt.setError("Select Category");
                    subCategoryTxt.requestFocus();
                }

                break;

        }
    }

    private void addProduct(String categoryId, String subCategoryId, String productId, String productDescription, String productStock, String productUnit, String productPrice, String productImage, String secondImage) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Product Adding..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.addProduct(MainPage.userId, categoryId, subCategoryId, productId, productDescription, productStock, productUnit, productPrice, productImage, secondImage);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus().equalsIgnoreCase("true")){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body().getStatus().equalsIgnoreCase("false")){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("productError", ""+t.getMessage());
            }
        });


    }

    private String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (firstImage.equalsIgnoreCase("first")) {
            choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
        } else if (secondImage.equalsIgnoreCase("second")){
            choosePhotoHelper1.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (firstImage.equalsIgnoreCase("first")) {
            choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else if (secondImage.equalsIgnoreCase("second")){
            choosePhotoHelper1.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        MainPage.title.setVisibility(View.VISIBLE);
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        MainPage.drawerLayout.closeDrawers();
        if (DetectConnection.checkInternetConnection(getActivity())) {


        } else {
            Toasty.warning(getActivity(), "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
        }
    }
}