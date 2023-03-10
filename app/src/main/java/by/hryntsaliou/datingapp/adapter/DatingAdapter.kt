package by.hryntsaliou.datingapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.hryntsaliou.datingapp.databinding.ItemUserLayoutBinding
import by.hryntsaliou.datingapp.model.UserModel
import kotlinx.coroutines.NonDisposableHandle.parent

class DatingAdapter(val context: Context, val list: ArrayList<UserModel>) :
    RecyclerView.Adapter<DatingAdapter.DatingViewHolder>() {
    inner class DatingViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {
//        return DatingViewHolder(ItemUserLayoutBinding.bind(context),
//        parent, false)
    }

    override fun getItemCount(): Int {
        return list.size;
    }


}