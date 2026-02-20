package com.example.padelscore.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.padelscore.R;
import com.example.padelscore.model.User;
import com.example.padelscore.network.ApiClient;
import com.example.padelscore.network.ApiService;
import com.example.padelscore.ui.login.LoginActivity;
import com.example.padelscore.util.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView avatarView = view.findViewById(R.id.profile_avatar);
        TextView nameView = view.findViewById(R.id.profile_name);
        TextView usernameView = view.findViewById(R.id.profile_username);
        TextView emailView = view.findViewById(R.id.profile_email);
        TextView pointsView = view.findViewById(R.id.profile_points);

        String token = TokenManager.getToken(requireContext());
        if (token == null) {
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
            return view;
        }
        ApiService apiService = ApiClient.getClient(requireContext()).create(ApiService.class);
        apiService.getUserProfile("Bearer " + token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    if (user.avatar != null && !user.avatar.isEmpty()) {
                        Glide.with(requireContext()).load(user.avatar).into(avatarView);
                    } else {
                        avatarView.setImageResource(R.drawable.ic_user);
                    }
                    nameView.setText(user.name != null && !user.name.isEmpty() ? user.name : user.username);
                    usernameView.setText("@" + user.username);
                    emailView.setText(user.email);
                    pointsView.setText("Puntos: " + user.points);
                } else if (response.code() == 401) {
                    TokenManager.saveToken(requireContext(), null);
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
