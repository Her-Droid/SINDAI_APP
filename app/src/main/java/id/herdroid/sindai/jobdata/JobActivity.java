package id.herdroid.sindai.jobdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.herdroid.sindai.MainActivity;
import id.herdroid.sindai.R;

public class JobActivity extends AppCompatActivity implements EmployeeAdapter.FirebaseDataListener {

    private FloatingActionButton mFloatingActionButton;
    private EditText edtName, edtNip, edtKelas, edtJabatan;
    private RecyclerView mRecyclerView;
    private EmployeeAdapter mAdapter;
    private ArrayList<Karyawan> listEmployee;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;
    private static final String TAG = "firebase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseApp.initializeApp(this);
        // mengambil referensi ke Firebase Database
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("karyawan");
        mDatabaseReference.child("data_karyawan").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                listEmployee = new ArrayList<>();
                for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()){
                    Karyawan karyawan = mDataSnapshot.getValue(Karyawan.class);
                    karyawan.setKey(mDataSnapshot.getKey());
                    listEmployee.add(karyawan);
                }
                //set adapter RecyclerView
                mAdapter = new EmployeeAdapter(JobActivity.this, listEmployee);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                // TODO: Implement this method
                Toast.makeText(JobActivity.this, databaseError.getDetails()+" "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


        //FAB (FloatingActionButton) tambah karyawan
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.add_employee);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //tambah karyawan
                dialogTambahKaryawan();
            }
        });
    }




    /* method ketika data di klik
     */
    @Override
    public void onDataClick(final Karyawan karyawan, int position){
        //aksi ketika data di klik
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialogUpdateKaryawan(karyawan);
            }
        });
        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                hapusDatakaryawan(karyawan);
            }
        });
        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }



    //dialog tambah karyawan / alert dialog
    private void dialogTambahKaryawan(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_edit_employee, null);

        edtName = (EditText)view.findViewById(R.id.job_name_edit);
        edtNip = (EditText)view.findViewById(R.id.job_nip_edit);
        edtKelas = (EditText)view.findViewById(R.id.job_class_edit);
        edtJabatan = (EditText)view.findViewById(R.id.job_jabatan);

        builder.setView(view);

        //button simpan karyawan / submit karyawan
        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){

                String namaKaryawan = edtName.getText().toString();
                String nipKaryawan = edtNip.getText().toString();
                String kelasKaryawan = edtKelas.getText().toString();
                String jabatanKaryawan = edtJabatan.getText().toString();

                if(!namaKaryawan.isEmpty() && !nipKaryawan.isEmpty() && !kelasKaryawan.isEmpty() && !jabatanKaryawan.isEmpty()){
                    submitDataKaryawan(new Karyawan(namaKaryawan, nipKaryawan, kelasKaryawan, jabatanKaryawan));
                }
                else {
                    Toast.makeText(JobActivity.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                }

            }
        });

        //button kembali / batal
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }



    //dialog update karyawan / update data karyawan
    private void dialogUpdateKaryawan(final Karyawan karyawan){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Karyawan");
        View view = getLayoutInflater().inflate(R.layout.activity_edit_employee, null);

       edtName= (EditText)view.findViewById(R.id.job_name_edit);
        edtNip = (EditText)view.findViewById(R.id.job_nip_edit);
        edtKelas = (EditText)view.findViewById(R.id.job_class_edit);

        edtJabatan = (EditText)view.findViewById(R.id.job_jabatan);

        edtName.setText(karyawan.getName());
       edtNip.setText(karyawan.getNip());
        edtKelas.setText(karyawan.getKelas());

        edtJabatan.setText(karyawan.getJabatan());
        builder.setView(view);

        //final Karyawan mKaryawan = (Karyawan)getIntent().getSerializableExtra("
        if (karyawan!= null){
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    karyawan.setName(edtName.getText().toString());
                    karyawan.setNip(edtNip.getText().toString());
                    karyawan.setKelas(edtKelas.getText().toString());
                    karyawan.setJabatan(edtJabatan.getText().toString());
                    updateDataKaryawan(karyawan);
                }
            });
        }
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }


    /**
     * submit data barang
     * ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil ditambahkan
     */
    private void submitDataKaryawan(Karyawan karyawan){
        mDatabaseReference.child("data_karyawan").push().setValue(karyawan).addOnSuccessListener(this, new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(JobActivity.this, "Data barang berhasil di simpan !", Toast.LENGTH_LONG).show();
                Log.e(TAG, "eror firebase"); // Error
            }
        });
    }

    /**
     * update/edit data barang
     * ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil ditambahkan
     */
    private void updateDataKaryawan(Karyawan karyawan){
        mDatabaseReference.child("data_karyawan").child(karyawan.getKey()).setValue(karyawan).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(JobActivity.this, "Data berhasil di update !", Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * hapus data barang
     * ini kode yang digunakan untuk menghapus data yang ada di Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil dihapus
     */
    private void hapusDatakaryawan(Karyawan karyawan){
        if(mDatabaseReference != null){
            mDatabaseReference.child("data_karyawan").child(karyawan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void mVoid){
                    Toast.makeText(JobActivity.this,"Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}