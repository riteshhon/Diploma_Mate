package com.sanjivanidsc.diplomamate.fragments;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.sanjivanidsc.diplomamate.fragments.SubjectsFragment.pdfDownload;
import static com.sanjivanidsc.diplomamate.helper.KeyAdapter.API;
import static com.sanjivanidsc.diplomamate.helper.KeyAdapter.KEY;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.sanjivanidsc.diplomamate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AnswerPaperFragment extends Fragment {

    PDFView pdfView;
    CardView cvProgressBar;
    LinearLayout llDownloadPDF;
    ProgressDialog progressDialog;

    public static String subjectName;
    public static String pdfLink = "";
    TextView tvEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer_paper, container, false);

        pdfView = view.findViewById(R.id.pdfView);
        cvProgressBar = view.findViewById(R.id.cvProgressBar);
        llDownloadPDF = view.findViewById(R.id.llDownloadPDF);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressbar_loading);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        llDownloadPDF.setOnClickListener(view1 -> {
            downloadPdf();
        });
        if (pdfDownload.equals("true")){
            llDownloadPDF.setVisibility(View.VISIBLE);
        } else {
            llDownloadPDF.setVisibility(View.GONE);
        }

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        };
//        handler.postDelayed(runnable, 5000);

        callLink();

        return view;
    }

    private void callLink() {
        StringRequest request = new StringRequest(Request.Method.POST, API+"answerPaper.php", response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() == 0){
                    tvEmpty.setVisibility(View.VISIBLE);
                    llDownloadPDF.setVisibility(View.GONE);
                    progressDialog.dismiss();
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    llDownloadPDF.setVisibility(View.VISIBLE);
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                pdfLink = jsonObject.getString("answerPaperUrl");
                new AnswerPaperFragment.RetrievePdfStream().execute(pdfLink);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", KEY);
                map.put("answerPaperSubject", subjectName);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            try {
                pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 500);
                    }
                }).load();
            } catch (Exception e){

            }
        }
    }

    private void downloadPdf() {
        Toast.makeText(getContext(), "Downloading started...", Toast.LENGTH_SHORT).show();
        DownloadManager dm = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfLink));

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!

        dm.enqueue(request);
    }

}