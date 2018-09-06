package ga.hamzabenhida.trendingrepos.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ga.hamzabenhida.trendingrepos.R;
import ga.hamzabenhida.trendingrepos.TrendingFragment;

/**
 * MListAdapter
 * extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 * List Adapter implementation to be used by a RecyclerView
 * and display repository items from layout at 'repo_item.xml'
 *
 */
public class MListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public TrendingFragment fragment;

    public MListAdapter(TrendingFragment trendingFragment) {
        this.fragment = trendingFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //System.out.println("onCreateViewHolder");
        View repoItem = fragment.getLayoutInflater().inflate(R.layout.repo_item, parent, false);
        return new MViewHolder(repoItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //System.out.println("onBindViewHolder repo: "+ repoItems.get(position).getTitle());

        holder.itemView.setAlpha(1f);
        fragment.getActivity().findViewById(R.id.loading_indicator).setVisibility(View.GONE);
        ((TextView)holder.itemView.findViewById(R.id.repo_title)).setText(fragment.getRepoItems().get(position).getTitle());
        ((TextView)holder.itemView.findViewById(R.id.repo_desc)).setText(fragment.getRepoItems().get(position).getDesc());
        ((TextView)holder.itemView.findViewById(R.id.repo_owner_name)).setText(fragment.getRepoItems().get(position).getOwnerName());
        ((TextView)holder.itemView.findViewById(R.id.repo_stars)).setText(fragment.getRepoItems().get(position).getStars());
        if(position != fragment.getRepoItems().size()-1) {
            ((ImageView)holder.itemView.findViewById(R.id.repo_owner_image)).setImageBitmap(fragment.getRepoItems().get(position).getImage());
        }else{
            holder.itemView.setAlpha(0.5f);
            ((ImageView)holder.itemView.findViewById(R.id.repo_owner_image)).setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_portrait_black_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return fragment.getRepoItems().size();
    }

    private class MViewHolder extends RecyclerView.ViewHolder {
        public MViewHolder(View v) {
            super(v);
        }
    }
}