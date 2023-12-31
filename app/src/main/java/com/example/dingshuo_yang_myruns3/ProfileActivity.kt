package com.example.dingshuo_yang_myruns3

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import java.io.File

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val PREF_FILE_NAME = "my_shared_preferences"
        const val PROFILE_PHOTO_NAME = "profile_photo_filename.jpg"
        const val TEMP_PROFILE_PHOTO_NAME = "temp_profile_photo_filename.jpg"
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
        const val KEY_PHONE = "phone"
        const val KEY_CLASS = "class"
        const val KEY_MAJOR = "major"
        const val KEY_GENDER = "gender"
        const val KEY_TEMP_PROFILE_PHOTO = "tempProfilePhotoUriStr"
        const val TOAST_TEXT = "SAVED"
    }

    private fun saveProfile() {
        val nameValue = nameEditText.text.toString()
        val emailValue = emailEditText.text.toString()
        val phoneValue = phoneEditText.text.toString()
        val classValue = classEditText.text.toString()
        val majorValue = majorEditText.text.toString()
        val selectedGender = when (genderRadioButtonGroup.checkedRadioButtonId) {
            R.id.male_radio_button -> "male"
            R.id.female_radio_button -> "female"
            else -> ""
        }
        val editor = sharedPref.edit()
        editor.putString(KEY_NAME, nameValue)
        editor.putString(KEY_EMAIL, emailValue)
        editor.putString(KEY_PHONE, phoneValue)
        editor.putString(KEY_GENDER, selectedGender)
        editor.putString(KEY_CLASS, classValue)
        editor.putString(KEY_MAJOR, majorValue)
        editor.apply()
    }

    private fun loadProfile() {
        val nameValue = sharedPref.getString(KEY_NAME, "")
        val emailValue = sharedPref.getString(KEY_EMAIL, "")
        val phoneValue = sharedPref.getString(KEY_PHONE, "")
        val genderValue = sharedPref.getString(KEY_GENDER, "")
        val classValue = sharedPref.getString(KEY_CLASS, "")
        val majorValue = sharedPref.getString(KEY_MAJOR, "")

        nameEditText.setText(nameValue)
        emailEditText.setText(emailValue)
        phoneEditText.setText(phoneValue)
        when (genderValue) {
            "male" -> genderRadioButtonGroup.check(R.id.male_radio_button)
            "female" -> genderRadioButtonGroup.check(R.id.female_radio_button)
            else -> {}
        }
        classEditText.setText(classValue)
        majorEditText.setText(majorValue)
    }

    // Initialize shared preference
    private lateinit var sharedPref: SharedPreferences

    // Components for profile photo
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var cameraResult: ActivityResultLauncher<Intent>
    private lateinit var galleryResult: ActivityResultLauncher<Intent>
    private lateinit var localStorage: LocalStorageViewModel
    private lateinit var profilePhotoUri: Uri
    private var tempProfilePhotoUri: Uri? = null


    // Components for inputs
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var classEditText: EditText
    private lateinit var majorEditText: EditText
    private lateinit var genderRadioButtonGroup: RadioGroup


    // Components for Save & Cancel buttons
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    // Create custom AlertDialog learned from: https://www.geeksforgeeks.org/how-to-create-a-custom-alertdialog-in-android/
    private fun showPhotoOptionsDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Profile Photo")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> {
                    // Take a photo through camera
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempProfilePhotoUri)
                    cameraResult.launch(cameraIntent)
                }

                1 -> {
                    // Take a photo from Gallery
                    // Choose a photo from gallery learned from: https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
                    val galleryIntent = Intent(Intent.ACTION_PICK)
                    galleryIntent.type = "image/*"
                    galleryResult.launch(galleryIntent)
                }
            }
        }
        builder.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        imageView = findViewById(R.id.profilePhoto)
        if (savedInstanceState != null) {
            val tempProfilePhotoUriStr = savedInstanceState.getString(KEY_TEMP_PROFILE_PHOTO)
            if (tempProfilePhotoUriStr != null) {
                tempProfilePhotoUri = Uri.parse(tempProfilePhotoUriStr)
            }
        }
        button = findViewById(R.id.take_photo_button)
        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        phoneEditText = findViewById(R.id.phone_edit_text)
        classEditText = findViewById(R.id.class_edit_text)
        majorEditText = findViewById(R.id.major_edit_text)
        genderRadioButtonGroup = findViewById(R.id.gender_radio_button_group)

        // Get profile photo and temp profile photo
        val profilePhotoFile = File(getExternalFilesDir(null), PROFILE_PHOTO_NAME)
        val tempProfilePhotoFile = File(getExternalFilesDir(null), TEMP_PROFILE_PHOTO_NAME)

        // Get Uri for profile photo
        profilePhotoUri =
            FileProvider.getUriForFile(this, "com.example.dingshuo_yang_myruns3", profilePhotoFile)
        tempProfilePhotoUri =
            FileProvider.getUriForFile(
                this,
                "com.example.dingshuo_yang_myruns3",
                tempProfilePhotoFile
            )

        // Profile photo button logics
        button.setOnClickListener {
            showPhotoOptionsDialog()
        }

        localStorage = ViewModelProvider(this).get(LocalStorageViewModel::class.java)
        localStorage.profilePhotoImage.observe(this) {
            imageView.setImageBitmap(it)
        }

        // Get camera result
        cameraResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Safe use of !! as the camera result returns OK already in the line above
                    val bitmap = Util.getBitmap(this, tempProfilePhotoUri!!)
                    localStorage.profilePhotoImage.value = bitmap
                }
            }
        galleryResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Get Uri of the selected gallery photo
                    val selectedImageUri: Uri? = result.data?.data
                    if (selectedImageUri != null) {
                        val bitmap = Util.getBitmap(this, selectedImageUri)
                        localStorage.profilePhotoImage.value = bitmap

                        // Use contentResolver to save photo uri to file: https://stackoverflow.com/questions/2975197/convert-file-uri-to-file-in-android
                        val inputStream = contentResolver.openInputStream(selectedImageUri)
                        val outputStream = tempProfilePhotoFile.outputStream()

                        inputStream?.copyTo(outputStream)
                        inputStream?.close()
                        outputStream.close()

                        // Update tempProfilePhotoUri to point to the new temp file
                        tempProfilePhotoUri = FileProvider.getUriForFile(
                            this,
                            "com.example.dingshuo_yang_myruns3",
                            tempProfilePhotoFile
                        )
                    }
                }
            }


        if (profilePhotoFile.exists()) {
            val bitmap = Util.getBitmap(this, profilePhotoUri)
            imageView.setImageBitmap(bitmap)
        }

        // Initialize text inputs with previous texts
        loadProfile()

        // Save button
        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener {
            // Overwrite existing profile photo by the temp photo to save it
            // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.io.-file/copy-to.html
            if (tempProfilePhotoFile.exists()) {
                tempProfilePhotoFile.copyTo(profilePhotoFile, overwrite = true)
            }
            saveProfile()
            // Toast: https://developer.android.com/guide/topics/ui/notifiers/toasts
            val toast = Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_SHORT) // in Activity
            toast.show()
            finish()

        }
        cancelButton = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener {
            tempProfilePhotoUri = null
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TEMP_PROFILE_PHOTO, tempProfilePhotoUri.toString())
    }

    // Garbage cleaning, remove temp file if exists on exit of the application
    override fun onDestroy() {
        super.onDestroy()
        val tempProfilePhotoFile = File(getExternalFilesDir(null), TEMP_PROFILE_PHOTO_NAME)
        if (tempProfilePhotoFile.exists()) {
            tempProfilePhotoFile.delete()
        }
    }
}
