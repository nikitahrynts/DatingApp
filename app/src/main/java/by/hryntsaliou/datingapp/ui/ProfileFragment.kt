package by.hryntsaliou.datingapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.hryntsaliou.datingapp.R
import by.hryntsaliou.datingapp.activity.EditProfileActivity
import by.hryntsaliou.datingapp.auth.LoginActivity
import by.hryntsaliou.datingapp.databinding.FragmentProfileBinding
import by.hryntsaliou.datingapp.model.UserModel
import by.hryntsaliou.datingapp.utils.Config
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Config.showDialog(requireContext())
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.getValue(UserModel::class.java)

                    binding.name.setText(data!!.name.toString())
                    binding.number.setText(
                        FirebaseAuth
                            .getInstance()
                            .currentUser?.phoneNumber
                            .toString()
                    )
                    binding.email.setText(data.email.toString())
                    binding.city.setText(data.city.toString())

                    Glide.with(requireContext())
                        .load(data.image)
                        .placeholder(R.drawable.profile_svgrepo_com)
                        .into(binding.userImage)

                    Config.hideDialog()
                }
            }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.edit.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        return binding.root
    }
}