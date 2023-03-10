package by.hryntsaliou.datingapp.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import by.hryntsaliou.datingapp.MainActivity
import by.hryntsaliou.datingapp.databinding.ActivityRegisterBinding
import by.hryntsaliou.datingapp.model.UserModel
import by.hryntsaliou.datingapp.utils.Config
import by.hryntsaliou.datingapp.utils.Config.hideDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private var imageUri: Uri? = null;

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {

        imageUri = it

        binding.userImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userImage.setOnClickListener {
            selectImage.launch("image/*")
        }

        binding.saveData.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        if (binding.userName.text.toString().isEmpty()
            || binding.userEmail.text.toString().isEmpty()
            || binding.userCity.text.toString().isEmpty()
            || imageUri == null
        ) {
            Toast.makeText(this, "Please, fill in all the fields", Toast.LENGTH_SHORT)
                .show()
        } else if (!binding.termsCondition.isChecked) {
            Toast.makeText(
                this,
                "Read and agree to the terms of condition to proceed",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            uploadImage()
        }
    }

    private fun uploadImage() {

        Config.showDialog(this)

        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profile.jpg")

        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl
                    .addOnSuccessListener {
                        storeData(it)
                    }.addOnFailureListener {
                        hideDialog()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
            }.addOnFailureListener {
                hideDialog()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun storeData(imageUrl: Uri?) {
        val data = UserModel(
            name = binding.userName.text.toString(),
            image = imageUrl.toString(),
            email = binding.userEmail.text.toString(),
            city = binding.userCity.text.toString(),
        )

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)
            .setValue(data).addOnCompleteListener {
                hideDialog()
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    Toast.makeText(this, "User registered successful", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}