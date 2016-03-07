package realm.demo.cdrussell.com.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import realm.demo.cdrussell.com.realmdemo.adapter.PersonAdapter;
import realm.demo.cdrussell.com.realmdemo.model.Person;

import static io.realm.Sort.DESCENDING;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText nameEntry;
    private EditText ageEntry;
    private TextView personCountText;
    private Realm realm;
    private List<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        personCountText = (TextView) findViewById(R.id.person_count);
        nameEntry = (EditText) findViewById(R.id.name);
        ageEntry = (EditText) findViewById(R.id.age);

        people = new ArrayList<>();
        PersonAdapter adapter = new PersonAdapter(people);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        realm = Realm.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshView();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    private void refreshView() {
        RealmResults<Person> searchResults = getAllSavedPeople();
        updateRecyclerView(searchResults);
        personCountText.setText(getString(R.string.person_count, searchResults.size()));
    }

    private void updateRecyclerView(RealmResults<Person> searchResults) {
        people.clear();
        people.addAll(searchResults);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private int getAge() {
        try {
            return Integer.valueOf(ageEntry.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void onCreateButtonPressed(View view) {
        Person person = new Person();
        person.setName(nameEntry.getText().toString());
        person.setAge(getAge());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(person);
        realm.commitTransaction();

        refreshView();
    }

    public void onSortAscending(View view) {
        RealmResults<Person> searchResults = getAllSavedPeople();
        searchResults.sort("name");
        updateRecyclerView(searchResults);
    }

    public void onSortDescending(View view) {
        RealmResults<Person> searchResults = getAllSavedPeople();
        searchResults.sort("name", DESCENDING);
        updateRecyclerView(searchResults);
    }

    private RealmResults<Person> getAllSavedPeople() {
        return realm.allObjects(Person.class);
    }
}
