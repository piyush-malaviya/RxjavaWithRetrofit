package com.ln.rxjavawithretrofit.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ln.rxjavawithretrofit.R;
import com.ln.rxjavawithretrofit.api.response.GitHubUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GitHubUserAdapter extends RecyclerView.Adapter<GitHubUserAdapter.ViewHolder> {

    private Context mContext;
    private List<GitHubUser> mGitHubUserList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public GitHubUserAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<GitHubUser> gitHubUserList) {
        mGitHubUserList = gitHubUserList;
        notifyDataSetChanged();
    }

    public void addAll(List<GitHubUser> gitHubUserList) {
        int pos = mGitHubUserList.size();
        mGitHubUserList.addAll(gitHubUserList);
        notifyItemRangeInserted(pos, gitHubUserList.size());
    }

    public void add(GitHubUser gitHubUser) {
        int pos = mGitHubUserList.size();
        mGitHubUserList.add(gitHubUser);
        notifyItemInserted(pos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_git_hub_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GitHubUser gitHubUser = mGitHubUserList.get(position);

        holder.tvUserName.setText(gitHubUser.getLogin());
        if (!TextUtils.isEmpty(gitHubUser.getAvatarUrl())) {
            Picasso.with(mContext)
                    .load(gitHubUser.getAvatarUrl())
                    .placeholder(R.drawable.ic_github_logo)
                    .error(R.drawable.ic_github_logo)
                    .into(holder.ivAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return mGitHubUserList.size();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.cleanup();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, GitHubUser gitHubUser, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivAvatar;
        private TextView tvUserName;


        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, mGitHubUserList.get(getAdapterPosition()), getAdapterPosition());
            }
        }

        public void cleanup() {
            Picasso.with(ivAvatar.getContext())
                    .cancelRequest(ivAvatar);
            ivAvatar.setImageDrawable(null);
        }
    }
}
