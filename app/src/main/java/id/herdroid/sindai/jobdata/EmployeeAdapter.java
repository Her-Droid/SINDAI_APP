package id.herdroid.sindai.jobdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.herdroid.sindai.R;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder>
{
    private Context context;
    private ArrayList<Karyawan> listKaryawan;
    private FirebaseDataListener listener;

    public EmployeeAdapter(Context context, ArrayList<Karyawan> daftarBarang){
        this.context = context;
        this.listKaryawan = daftarBarang;
        this.listener = (FirebaseDataListener)context;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // TODO: Implement this method
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        EmployeeViewHolder holder = new EmployeeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, final int position)
    {
        // TODO: Implement this method
        holder.namaKaryawan.setText("Nama   : "+listKaryawan.get(position).getName());
        holder.nipKaryawan.setText("Nip     : "+listKaryawan.get(position).getNip());
        holder.kelasKaryawan.setText("Kelas   : "+listKaryawan.get(position).getKelas());
        holder.jabatanKaryawan.setText("jabatan   : "+listKaryawan.get(position).getJabatan());

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onDataClick(listKaryawan.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        // TODO: Implement this method
        return listKaryawan.size();
    }


    //interface data listener
    public interface FirebaseDataListener {
        void onDataClick(Karyawan karyawan, int position);
    }
}
