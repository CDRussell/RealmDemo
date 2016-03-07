package realm.demo.cdrussell.com.realmdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import realm.demo.cdrussell.com.realmdemo.R;
import realm.demo.cdrussell.com.realmdemo.model.Person;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> people;

    public PersonAdapter(List<Person> people) {
        this.people = people;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View personRow = inflater.inflate(R.layout.person_list_row, parent, false);

        return new ViewHolder(personRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Person person = people.get(position);

        viewHolder.nameTextView.setText(person.getName());
        viewHolder.ageTextView.setText(String.valueOf(person.getAge()));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView ageTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_view);
            ageTextView = (TextView) itemView.findViewById(R.id.age_view);
        }
    }
}
