package com.wonbuddism.bupmun.Progress;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;

import java.util.List;

public class ProgressRecyclerViewAdapter extends RecyclerView.Adapter<ProgressRecyclerViewAdapter.CardViewHolder> {
    public static class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView cotnent;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_title_cardview);
            title = (TextView)itemView.findViewById(R.id.item_title_top_title);
            cotnent = (TextView)itemView.findViewById(R.id.item_title_top_content);
        }
    }

    List<Card> cards;

    ProgressRecyclerViewAdapter(List<Card> cards){
        this.cards = cards;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress_title_toprank, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder personViewHolder, int i) {
        personViewHolder.title.setText(cards.get(i).getTitle());
        personViewHolder.cotnent.setText(cards.get(i).getContent());

    }

    @Override
    public int getItemCount() {
        /*return cards.size();*/
        return 1;
    }

}
