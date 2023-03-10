package by.hryntsaliou.datingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.hryntsaliou.datingapp.databinding.ItemUserLayoutBinding
import by.hryntsaliou.datingapp.model.UserModel
import com.bumptech.glide.Glide

class DatingAdapter(val context: Context, val list: ArrayList<UserModel>) :
    RecyclerView.Adapter<DatingAdapter.DatingViewHolder>() {
    inner class DatingViewHolder(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
        return DatingViewHolder(
            ItemUserLayoutBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {

        holder.binding.textView5.text = list[position].name
        holder.binding.textView4.text = list[position].name

        Glide.with(context).load(list[position].image).into(holder.binding.userImage)
    }

    override fun getItemCount(): Int {
        return list.size;
    }


}