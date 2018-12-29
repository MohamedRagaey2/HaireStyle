package m.ragaey.mohamed.hairestyle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    FloatingActionButton fab;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    BookingAdapter bookingAdapter;

    List<BookList> bookLists;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview);

        bookLists = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("allbooks");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        progressBar = findViewById(R.id.progressbar);

        bookingAdapter = new BookingAdapter(getApplicationContext(), bookLists);

        recyclerView.setAdapter(bookingAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                bookLists.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    BookList bookList = postSnapshot.getValue(BookList.class);
                    bookLists.add(bookList);
                }

                bookingAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext() , databaseError.getMessage() , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                startActivity(intent);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {

        finishAffinity();
    }
}
