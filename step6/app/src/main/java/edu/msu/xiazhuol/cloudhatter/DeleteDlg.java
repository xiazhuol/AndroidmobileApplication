package edu.msu.xiazhuol.cloudhatter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.msu.xizhuol.cloudhatter.R;

public class DeleteDlg extends DialogFragment {

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // Set the title

        builder.setTitle(R.string.deletedlg);


        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();


        // Pass null as the parent view because its going in the dialog layout

        @SuppressLint("InflateParams")

        View view = inflater.inflate(R.layout.catalog_dlg, null);

        builder.setView(view);


        // Add a cancel button

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int id) {

                // Cancel just closes the dialog box

            }

        });


        final AlertDialog dlg = builder.create();


        // Find the list view

        ListView list = (ListView) view.findViewById(R.id.listHattings);


        // Create an adapter

        final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListView.OnItemClickListener() {



            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position,

                                    long id) {



                // Get the id of the one we want to load

                String catId = adapter.getId(position);

                String catName = adapter.getName(position);



                // Dismiss the dialog box

                dlg.dismiss();



                DeletingDlg deletingDlg = new DeletingDlg();

                deletingDlg.setCatId(catId);

                deletingDlg.setCatName(catName);

                deletingDlg.show(getActivity().getFragmentManager(), "confirm");

            }



        });



        return dlg;

    }

}
