package realm.demo.cdrussell.com.realmdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import realm.demo.cdrussell.com.realmdemo.model.Person;

public class HomeActivity extends AppCompatActivity {

    private EditText nameEntry;
    private EditText ageEntry;
    private TextView personCountText;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        personCountText = (TextView) findViewById(R.id.person_count);
        nameEntry = (EditText) findViewById(R.id.name);
        ageEntry = (EditText) findViewById(R.id.age);

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
        personCountText.setText(getString(R.string.person_count, realm.where(Person.class).findAll().size()));
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

    private int getAge() {
        try {
            return Integer.valueOf(ageEntry.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
