package id.herdroid.sindai.jobdata;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import id.herdroid.sindai.R;

public class EmployeeViewHolder extends RecyclerView.ViewHolder
{
    public TextView namaKaryawan;
    public TextView nipKaryawan;
    public TextView kelasKaryawan;
    public TextView jabatanKaryawan;
    public View view;

    public EmployeeViewHolder(View view){
        super(view);

        namaKaryawan = (TextView)view.findViewById(R.id.employee_name);
        nipKaryawan = (TextView)view.findViewById(R.id.employee_nip);
        kelasKaryawan = (TextView)view.findViewById(R.id.employment_class);
        jabatanKaryawan = (TextView)view.findViewById(R.id.employee_jabatan);
        this.view = view;
    }
}
