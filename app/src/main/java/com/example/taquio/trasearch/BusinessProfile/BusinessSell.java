package com.example.taquio.trasearch.BusinessProfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.taquio.trasearch.R;

/**
 * Created by Del Mar on 2/24/2018.
 */

public class BusinessSell extends AppCompatActivity{
//    CustomAdapter adapter = null;
//    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_sell_activity);

//        displayListView();
//        btn = (Button) findViewById(R.id.addItem);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(BusinessSell.this, BusinessSPrice.class);
//                startActivity(i);
//            }
//        });
    }

//    private void displayListView() {
//        ArrayList<Material> materialList = new ArrayList<Material>();
//        Material material = new Material("Cans", false);
//        materialList.add(material);
//        material = new Material("Copper", false);
//        materialList.add(material);
//        material = new Material("Paper", false);
//        materialList.add(material);
//        material = new Material("Plastic Bottles", false);
//        materialList.add(material);
//        material = new Material("Plastic (Assorted)", false);
//        materialList.add(material);
//        material = new Material("Plastic (Blowing)", false);
//        materialList.add(material);
//
//        adapter = new CustomAdapter(this, R.layout.business_material_info, materialList);
//        ListView listView = (ListView) findViewById(R.id.listView1);
//        listView.setAdapter(adapter);
//
//    }
//
//    private class CustomAdapter extends ArrayAdapter<Material> {
//        private ArrayList<Material> materialList;
//
//        public CustomAdapter(@NonNull Context context, int textViewResourceId, ArrayList<Material> materialList) {
//            super(context, textViewResourceId, materialList);
//            this.materialList = new ArrayList<Material>();
//            this.materialList.addAll(materialList);
//        }
//        private class ViewHolder {
//            CheckBox name;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            CustomAdapter.ViewHolder holder = null;
//
//            if (convertView == null) {
//                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = vi.inflate(R.layout.business_material_info, null);
//                holder = new CustomAdapter.ViewHolder();
//                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
//                convertView.setTag(holder);
//            }
//
//            Material material = materialList.get(position);
//            holder.name.setText(material.getName());
//            holder.name.setChecked(material.isSelected());
//            holder.name.setTag(material);
//            return convertView;
//
//        }
//    }
//
//    private void checkButtonClick() {
//        Button btn = (Button) findViewById(R.id.addItem);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ArrayList<Material> materialList = adapter.materialList;
//                for(int i = 0; i<materialList.size();i++) {
//                    Material material = materialList.get(i);
//                    if(material.isSelected()) {
//
//                    }
//                }
//            }
//        });
//    }
}
