package com.mobile.peticos.Home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.R;
import com.mobile.peticos.Home.AdapterCuriosidadesDiarias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;
    public static final String[] REQUIRED_PERMISSIONS;

    static {
        List<String> requiredPermissions = new ArrayList<>();
        // Adicionando a permissão de notificação para Android 13 ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        REQUIRED_PERMISSIONS = requiredPermissions.toArray(new String[0]);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Verificar e solicitar permissão de notificação ao abrir a tela
        checkNotificationPermission();

        // Configuração do RecyclerView para dicas
        RecyclerView recyclerViewDicas = view.findViewById(R.id.RecyclerViewDicas);
        recyclerViewDicas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dados de exemplo para o RecyclerViewDicas
        List<String> dicasItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");

        // Configuração do Adapter para o RecyclerViewDicas
        AdapterCuriosidadesDiarias dicasAdapter = new AdapterCuriosidadesDiarias(dicasItems);
        recyclerViewDicas.setAdapter(dicasAdapter);

        // Configuração do RecyclerView para o feed de pets
        RecyclerView recyclerViewFeedPets = view.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));

        // Exemplo de dados para o feed de pets
        List<FeedPet> feedPets = Arrays.asList(
                new FeedPet("geogeo43", "nutela", "Há 2 dias", "curtido por João", "descrição da imagem", R.drawable.user1, R.drawable.publicacao1),
                new FeedPet("amanda_pet", "lola", "Há 1 dia", "curtido por Maria", "uma foto da lola", R.drawable.user1, R.drawable.publicacao1)
        );

        // Configuração do Adapter para o RecyclerViewFeedPets
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(feedPets, feedPet -> {
            // Tratar clique no item (feedPet)
        });
        recyclerViewFeedPets.setAdapter(feedPetsAdapter);

        return view;
    }

    // Verificar e solicitar permissão de notificação
    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }
}
