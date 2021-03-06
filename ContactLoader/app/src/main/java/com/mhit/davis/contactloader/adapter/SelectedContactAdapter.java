
package com.mhit.davis.contactloader.adapter;

import android.net.Uri;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mhit.davis.contactloader.R;
import com.mhit.davis.contactloader.model.ContactModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectedContactAdapter extends RecyclerView.Adapter<SelectedContactAdapter.ViewHolder> {

    SortedList<ContactModel> contactsList = new SortedList<ContactModel>(ContactModel.class, new SortedList.Callback<ContactModel>() {

        @Override
        public int compare(ContactModel o1, ContactModel o2) {
            return o1.getName().compareTo(o2.getName());
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(ContactModel oldItem, ContactModel newItem) {
            // return whether the items' visual representations are the same or not.
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areItemsTheSame(ContactModel item1, ContactModel item2) {
            return item1.getName() == item2.getName();
        }
    });

    public SelectedContactAdapter(ArrayList<ContactModel> contactsList) {
        this.contactsList.clear();
        this.contactsList.addAll(contactsList);
    }

    public void addContact(ContactModel contactModel) {
        contactsList.add(contactModel);
    }

    public void removeContact(ContactModel contactModel) {
        contactsList.remove(contactModel);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_contact_list, parent, false);
        return new SelectedContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactModel contact = contactsList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_selected_contact_list_riv_avatar)
        RoundedImageView rivAvatar;

        int avatarSize = 0;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            avatarSize = rivAvatar.getContext().getResources().getDimensionPixelOffset(R.dimen.avatar_size_normal);
        }

        public void bind(final ContactModel contactModel) {
            if (contactModel.getAvatar() != null) {
                Glide.with(rivAvatar.getContext())
                        .load(Uri.parse(contactModel.getAvatar()))
                        .centerCrop()
                        .override(avatarSize, avatarSize)
                        .into(rivAvatar);
            } else {
                rivAvatar.setImageResource(R.mipmap.ic_default_avatar);
            }
        }
    }
}
