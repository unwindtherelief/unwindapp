package com.depression.relief.depressionissues;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.depression.relief.depressionissues.databinding.ActivityMainBinding;
import com.depression.relief.depressionissues.fragments.CollectionFragment;
import com.depression.relief.depressionissues.fragments.CommunityFragment;
import com.depression.relief.depressionissues.fragments.HomeFragment;
import com.depression.relief.depressionissues.fragments.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();

/*
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);
*/

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
/*
        if (account != null) {
            String email = account.getEmail();
            binding.usermail.setText(email);
        }*/

        replaced(new HomeFragment());

        binding.bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hometab:
                        item.setChecked(true);
                        replaced(new HomeFragment());
                        break;
                    case R.id.communitytab:
                        item.setChecked(true);
                        replaced(new CommunityFragment());
                        break;
                    case R.id.collectiontab:
                        item.setChecked(true);
                        replaced(new CollectionFragment());
                        break;
                    case R.id.usertab:
                        item.setChecked(true);
                        replaced(new ProfileFragment());
                        break;
                }
                return false;
            }
        });

    }

    private void replaced(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameview, fragment);
        fragmentTransaction.commit();
    }
}