package com.example.orderfoodonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfoodonline.Utils.Utils;
import com.example.orderfoodonline.databinding.ItemCartBinding;
import com.example.orderfoodonline.listener.NumberListener;
import com.example.orderfoodonline.models.Cart;

import java.util.List;

import io.paperdb.Paper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private NumberListener listener;
    private List<Cart> cartList;

    //Ham khoi tao


    public CartAdapter(Context context, List<Cart> cartList , NumberListener listener) {
        this.context = context;
        this.listener = listener;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding cartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.binding.tvNameFood.setText(cart.getFoodDetail().getFood_name());
        Glide.with(context).load(cart.getFoodDetail().getFoodThumb()).into(holder.binding.imageCart);
        holder.binding.tvIdFoodCart.setText("Mã đơn hàng: "+cart.getFoodDetail().getId());
        holder.binding.tvPriceFood.setText("Giá: "+cart.getFoodDetail().getPrice() +"VND");
        holder.binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart(holder.getAdapterPosition());
                notifyDataSetChanged();
                listener.change();
            }
        });
        holder.binding.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minustoCart(holder.getAdapterPosition());
                notifyDataSetChanged();
                listener.change();
            }
        });
        holder.binding.tvMany.setText(cart.getAmount() + "");
        holder.binding.tvTotalPrice.setText("Tổng tiền: "+String.valueOf(cart.getAmount() * cart.getFoodDetail().getPrice()) + "VND");
        //Remove
        holder.binding.btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCart(holder.getAdapterPosition());
                notifyDataSetChanged();
                listener.change();
            }
        });
    }

    private void removeCart(int adapterPosition) {
        Utils.cartList.remove(adapterPosition);
        Paper.book().write("cart", Utils.cartList);
    }

    private void minustoCart(int adapterPosition) {
        //Lay vi tri tu click giam soluong cho cart
        if (Utils.cartList.get(adapterPosition).getAmount() == 1){
            //neu amount = 1 thi bo phan tu adapter tai vi tri do ra ngoai
            Utils.cartList.remove(adapterPosition);
        }else{
            Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount() - 1);
        }
        //ghi lai du lieu vao paper
        Paper.book().write("cart", Utils.cartList);
    }

    private void addtoCart(int adapterPosition) {
        //Lay vi tri tu click set soluong cho cart
        Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount() + 1);
        //ghi lai du lieu vao paper
        Paper.book().write("cart", Utils.cartList);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemCartBinding binding;

        public MyViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}