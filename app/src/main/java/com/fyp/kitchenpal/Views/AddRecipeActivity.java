package com.fyp.kitchenpal.Views;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.kitchenpal.Adapters.IngredientsAdapter;
import com.fyp.kitchenpal.Model.Ingredients;
import com.fyp.kitchenpal.Model.Recipe;
import com.fyp.kitchenpal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    public static final int IMAGECHOOSERCODE = 1;
    ImageView recipeImg;
    FloatingActionButton addRecipeIMGbt;
    FloatingActionButton addIngredientBT;
    EditText recipeNameEt;
    EditText recipeMethodET;
    RecyclerView ingredientsRV;
    IngredientsAdapter ingredientsAdapter;
    Button saveRecipeBT;
    List<Ingredients> ingredientsList;

    DatabaseReference reference;//firebase referenee
    String id;

    Uri imageUri;
    String fileExtension;
    StorageReference storageReference;
    String imageURL;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        initViews();

        progressDialog = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference("recipes");
        storageReference = FirebaseStorage.getInstance().getReference("recipeImages");

        ingredientsList = new ArrayList<>();
        ingredientsAdapter = new IngredientsAdapter(AddRecipeActivity.this, new ArrayList<Ingredients>());
        ingredientsRV.setLayoutManager(new LinearLayoutManager(AddRecipeActivity.this));
        ingredientsRV.setAdapter(ingredientsAdapter);

        addRecipeIMGbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        addIngredientBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(AddRecipeActivity.this).create();
                LayoutInflater inflater = AddRecipeActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.ingredient_dialogue, null);

                final EditText ingredientET = dialogView.findViewById(R.id.ingredientET);
                Button addBt = dialogView.findViewById(R.id.saveIngredientBT);


                addBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = ingredientET.getText().toString().trim();
//                        String message = messageET.getText().toString().trim();

                        if (name.isEmpty()) {
                            ingredientET.setError("This Can't be empty");
                            return;
                        }
                        Ingredients ingredients = new Ingredients(name);
                        ingredientsList.add(ingredients);
                        ingredientsAdapter.setIngredients(ingredientsList);
                        dialogBuilder.dismiss();
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }

        });

        saveRecipeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = recipeNameEt.getText().toString();
                String recipeMethod = recipeMethodET.getText().toString();

                if (recipeName.isEmpty()) {
                    recipeNameEt.setError("Invalid Name");
                    return;
                }

                if (recipeMethod.isEmpty()) {
                    recipeMethodET.setError("Invalid Method");
                    return;
                }

                id = reference.push().getKey();
                Recipe recipe = new Recipe(id, recipeName, imageURL, ingredientsList, recipeMethod,0f);
                saveRecipe(recipe);
            }
        });
    }

    public void saveRecipe(Recipe recipe) {
        reference.child(recipe.getRecipeName()).setValue(recipe);//saves the recipe to firebase
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGECHOOSERCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGECHOOSERCODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(recipeImg);
            fileExtension = getFileExtension(imageUri);
            uploadFile(imageUri,fileExtension);
        }
    }

    public String getFileExtension(Uri uri) {
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            return MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
    }

    public void uploadFile(Uri imageUri, String extension) {

//        imgProgressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        final StorageReference stRef = storageReference.child(System.currentTimeMillis() + "." + extension);
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.i(TAG, "onSuccess: " + storageReference.getDownloadUrl().toString());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageURL = uri.toString();
                        Toast.makeText(AddRecipeActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //tell user that uploading failed
                Toast.makeText(AddRecipeActivity.this, "Failed To Upload : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //keep the user updated about this task
                double val = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    imgProgressBar.setProgress((int) val, true);
                } else {
//                    imgProgressBar.setProgress((int) val);
                }
            }
        });
    }

    public void initViews() {
        recipeImg = findViewById(R.id.addRecipeImage);
        addIngredientBT = findViewById(R.id.addIngredientBT);
        addRecipeIMGbt = findViewById(R.id.addRecipeImg);
        recipeNameEt = findViewById(R.id.recipeNameET);
        recipeMethodET = findViewById(R.id.recipeMethodET);
        ingredientsRV = findViewById(R.id.ingredientsRV);
        saveRecipeBT = findViewById(R.id.saveRecipeBT);
    }
}
