package com.metacoders.dailyearn;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.metacoders.dailyearn.Database.Database;
import com.metacoders.dailyearn.models.Order;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

class  CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_cart_name , txt_cart_price ,txt_quantity  ;
    public ElegantNumberButton Quantity ;
    public Button cartCanelBtn;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public void setTxt_cart_price(TextView txt_cart_price) {
        this.txt_cart_price = txt_cart_price;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
            txt_cart_name = (TextView)itemView.findViewById(R.id.nameOnrow) ;
            txt_cart_price = (TextView)itemView.findViewById(R.id.totalPriceOnRow);
            Quantity = (ElegantNumberButton) itemView.findViewById(R.id.elegentbtn);
            cartCanelBtn =(Button) itemView.findViewById(R.id.cancelBtn);


    }

    @Override
    public void onClick(View v) {

    }
}


public class CartAdapter extends  RecyclerView.Adapter<CartViewHolder>{


    private List<Order> listData = new ArrayList<>() ;
    private cart cart ;

    public CartAdapter(List<Order> listData, cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View itemView = inflater.inflate(R.layout.cart_product_row, viewGroup  , false);
        return  new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder cartViewHolder, final int i) {

      // TextDrawable drawble = TextDrawable.builder()
           //   .buildRound(""+listData.get(i).getQuantity(),Color.RED);

        cartViewHolder.cartCanelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int delete  = cartViewHolder.getAdapterPosition() ;

                try{
                    Order order = listData.get(cartViewHolder.getAdapterPosition()) ; //repal,ec with i if any crash
                    new Database(cart).deleteMenu(order);
                    //  notifyItemChanged(getItemCount());
                    //  cartViewHolder.notifyAll();
                    removeItem(delete);
                    notifyDataSetChanged();
                    new Database(cart).updatetoCart(order);

                    //update txtTotal
                    int total = 0 ;
                    List<Order> orders = new Database(cart).getcarts() ;
                    for(Order item :orders){
                        total+= (Integer.parseInt(item.getPrice())* Integer.parseInt(item.getQuantity()));


                    }
                    cart.totalPriceTv.setText(String.valueOf(total)+"");
                   // cart.FinalTextPriceFloatBar.setText(total+"");

                }
                    catch(IndexOutOfBoundsException e ){



                    Toast.makeText(v.getContext(), "Error : " , Toast.LENGTH_SHORT).show();

                    }
                    catch (InputMismatchException e){
                        Toast.makeText(v.getContext(), "Error : " , Toast.LENGTH_SHORT).show();
                    }

    }
});

        cartViewHolder.Quantity.setNumber(listData.get(i).getQuantity());
        cartViewHolder.Quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                Order order = listData.get(i) ;
                order.setQuantity(String.valueOf(newValue));
                new Database(cart).updatetoCart(order);

                    //update txtTotal
                  int total = 0 ;
                  List<Order> orders = new Database(cart).getcarts() ;
                  for(Order item :orders){



                      total= total +  (Integer.parseInt(item.getPrice())* Integer.parseInt(item.getQuantity()));


                    cart.totalPriceTv.setText(String.valueOf(total)+"");
                     // cart.FinalTextPriceFloatBar.setText(total+"");

                  }

            }
        });


     //   Locale locale = new Locale("en", "US");
    //    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(i).getPrice()))*(Integer.parseInt(listData.get(i).getQuantity()));

       cartViewHolder.txt_cart_price.setText(price+" BDT");
        cartViewHolder.txt_cart_name.setText(listData.get(i).getProductName());
        cartViewHolder.Quantity.setNumber(listData.get(i).getQuantity());



    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


public void removeItem(int position ){
    listData.remove(position);
    notifyItemChanged(position);

}
    public void restoreItem(Order item , int position ){
        listData.add(position , item);
        notifyItemInserted(position);
    }




}