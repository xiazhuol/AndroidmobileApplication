package edu.msu.xiazhuol.cloudhatter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import edu.msu.xizhuol.cloudhatter.R;

public class DeletingDlg extends DialogFragment {

    private final static String ID = "id";
    private final static String NAME = "name";
    @Override

    public void onSaveInstanceState(Bundle bundle) {

        super.onSaveInstanceState(bundle);



        bundle.putString(ID, catId);

        bundle.putString(NAME, catName);

    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * Id for the image we are loading
     */
    private String catId;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    private String catName;
    /**
     * Set true if we want to cancel
     */
    private volatile boolean cancel = false;

    private AlertDialog dlg;
    /**

     * Create the dialog box

     */

    @Override

    public Dialog onCreateDialog(Bundle bundle) {

        cancel = false;



        if(bundle != null) {

            catId = bundle.getString(ID);

            catName = bundle.getString(NAME);

        }



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        // Set the title

        builder.setTitle(R.string.are_you_sure);



        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();



        // Pass null as the parent view because its going in the dialog layout

        @SuppressLint("InflateParams")

        View view = inflater.inflate(R.layout.deletedlg, null);



        TextView myHatter = (TextView)view.findViewById(R.id.myhat);

        myHatter.setText(" " + catName);

        builder.setView(view);





        // Get a reference to the view we are going to load into

        final HatterView hatterView = (HatterView)getActivity().findViewById(R.id.hatterView);





        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int id) {



                /*

                 * Create a thread to delete the hatting from the cloud

                 */

                new Thread(new Runnable() {



                    @Override

                    public void run() {

                        // Create a cloud object and get the XML

                        Cloud cloud = new Cloud();

                        InputStream stream = cloud.deleteFromCloud(catId);



                        if(cancel) {

                            return;

                        }



                        // Test for an error

                        boolean fail = stream == null;

                        if(!fail) {

                            try {

                                XmlPullParser xml = Xml.newPullParser();

                                xml.setInput(stream, "UTF-8");



                                xml.nextTag();      // Advance to first tag

                                xml.require(XmlPullParser.START_TAG, null, "hatter");

                                String status = xml.getAttributeValue(null, "status");

                                if(status.equals("yes")) {



                                    while(xml.nextTag() == XmlPullParser.START_TAG) {

                                        if(xml.getName().equals("hatting")) {

                                            if(cancel) {

                                                return;

                                            }

                                            // do something with the hatting tag...

                                            hatterView.loadXml(xml);

                                            break;

                                        }



                                        Cloud.skipToEndTag(xml);

                                    }

                                } else {

                                    fail = true;

                                }



                            } catch(IOException ex) {

                                fail = true;

                            } catch(XmlPullParserException ex) {

                                fail = true;

                            } finally {

                                try {

                                    stream.close();

                                } catch(IOException ex) {

                                }

                            }

                        }



                        final boolean fail1 = fail;

                        hatterView.post(new Runnable() {



                            @Override

                            public void run() {

                                dlg.dismiss();

                                if(fail1) {

                                    Toast.makeText(hatterView.getContext(), R.string.deleting_fail, Toast.LENGTH_LONG).show();

                                } else {

                                    // Success!

                                    if(getActivity() instanceof HatterActivity) {

                                        ((HatterActivity)getActivity()).updateUI();

                                    }

                                }



                            }



                        });

                    }

                }).start();



            }

        });



        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int id) {

                cancel = true;

            }

        });



        // Create the dialog box

        dlg = builder.create();



        return dlg;



    }
    /**

     * Called when the view is destroyed.

     */

    @Override

    public void onDestroyView() {

        super.onDestroyView();

        cancel = true;

    }
}
