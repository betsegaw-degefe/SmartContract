package com.gebeya.smartcontract.view.searchUser;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.SearchUserLayoutBinding;
import com.gebeya.smartcontract.model.data.dto.UserDTO;
import com.gebeya.smartcontract.model.data.dto.UserLoginResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserViewHolder> {

    private ArrayList<UserDTO> mUsers;
    private ArrayList<UserDTO> mArrayList;
    private SearchUserCallback mCallback;
    private Context mContext;

    public SearchUserAdapter(Context context,
                             ArrayList<UserDTO> userResponse,
                             SearchUserCallback searchUserCallback) {

        this.mContext = context;
        this.mUsers = userResponse;
        this.mArrayList = userResponse;
        this.mCallback = searchUserCallback;
    }

    @NonNull
    @Override
    public SearchUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        SearchUserLayoutBinding binding = DataBindingUtil.inflate(
              LayoutInflater.from(viewGroup.getContext()),
              R.layout.search_user_layout, viewGroup, false);

        return new SearchUserViewHolder(viewGroup, mCallback, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserViewHolder searchUserViewHolder, int position) {

        UserDTO user = mUsers.get(position);


        searchUserViewHolder.setName(user.getFirstName(), user.getLastName());
        searchUserViewHolder.setPhoneNumber(user.getPhoneNo());
        searchUserViewHolder.setId(user.getId());

    }

    /**
     * Total number of users respond from the network.
     *
     * @return number of users.
     */

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * get the users from the network and update the search view.
     *
     * @param users: list of user from the network
     */
    public void updateUsers(ArrayList<UserDTO> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    /**
     * Search the key word from the list of user
     *
     * @return list of users that match the search key.
     */
    Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    mUsers = mArrayList;
                } else {
                    ArrayList<UserDTO> filteredList = new ArrayList<>();

                    for (UserDTO user : mArrayList) {

                        if (user.getFirstName().toLowerCase().contains(charString) ||
                              user.getLastName().toLowerCase().contains(charString)) {

                            filteredList.add(user);
                        }
                    }
                    mUsers = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mUsers = (ArrayList<UserDTO>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * when the user click from the list return the position that the user clicked.
     *
     * @param adapterPosition: The position clicked from the list
     * @return the position.
     */
    private UserDTO getUser(int adapterPosition) {
        return mUsers.get(adapterPosition);
    }


}
