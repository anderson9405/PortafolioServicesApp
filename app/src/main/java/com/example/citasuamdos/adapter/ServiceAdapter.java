package com.example.citasuamdos.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import com.example.citasuamdos.HomeActivity;
import com.example.citasuamdos.MainActivity;
import com.example.citasuamdos.model.Service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citasuamdos.R;
import com.example.citasuamdos.model.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class ServiceAdapter extends FirestoreRecyclerAdapter<Service, ServiceAdapter.ViewHolder>{
    
    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ServiceAdapter(@NonNull Context context, @NonNull FirestoreRecyclerOptions<Service> options) {
        super(options);
        this.context = context;
    }


    // llena el contenedor con los atributos del objeto
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Service model) {
        holder.title.setText(model.getTitle());
        holder.name.setText(model.getName());
        holder.phone.setText("Teléfono: " + model.getPhone());
        holder.description.setText(model.getDescription());
    }

    // llama el diseño de la card, donde llenará los datos de cada servicio
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_service_single, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, name, phone, description;
        ImageButton callButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.tv_title);
            name = itemView.findViewById(R.id.tv_name);
            phone = itemView.findViewById(R.id.tv_phone);
            description = itemView.findViewById(R.id.tv_description);
            callButton = itemView.findViewById(R.id.btn_llamar);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Obtener el número de teléfono del servicio en la posición seleccionada
                        String numeroTelefono = getItem(position).getPhone();
                        abrirAppLlamadas(numeroTelefono);
                    }
                }
            });
        }
    }

    private void abrirAppLlamadas(String numeroTelefono) {
        Intent intentLlamada = new Intent(Intent.ACTION_DIAL);

        intentLlamada.setData(Uri.parse("tel:" + numeroTelefono));
        context.startActivity(intentLlamada);
    }
}
