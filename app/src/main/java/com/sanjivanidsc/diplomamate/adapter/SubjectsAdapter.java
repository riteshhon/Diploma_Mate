package com.sanjivanidsc.diplomamate.adapter;

import static com.sanjivanidsc.diplomamate.home.HomeFragment.typeOfResources;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.sanjivanidsc.diplomamate.R;
import com.sanjivanidsc.diplomamate.account.FragmentActivity;
import com.sanjivanidsc.diplomamate.fragments.AnswerPaperFragment;
import com.sanjivanidsc.diplomamate.fragments.ManualsFragment;
import com.sanjivanidsc.diplomamate.fragments.NotesFragment;
import com.sanjivanidsc.diplomamate.fragments.QuestionPaperFragment;
import com.sanjivanidsc.diplomamate.fragments.SyllabusFragment;
import com.sanjivanidsc.diplomamate.model.SubjectsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    Context context;
    List<SubjectsModel> list;

    public SubjectsAdapter(Context context, List<SubjectsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getSubjectImage()).into(holder.ivSubjectImage);
        holder.tvSubjectName.setText(list.get(position).getSubjectName());
        holder.tvSubjectDescribe.setText(list.get(position).getSubjectDescribe());

        holder.cvSubject.setOnClickListener(view -> {

            if (typeOfResources.equals("Syllabus")){
                FragmentActivity.fragment = "Syllabus";
                SyllabusFragment.subjectName = list.get(position).getSubjectName().toString();
            } else if (typeOfResources.equals("Manual")){
                FragmentActivity.fragment = "Manual";
                ManualsFragment.subjectName = list.get(position).getSubjectName().toString();
            } else if (typeOfResources.equals("Notes")){
                FragmentActivity.fragment = "Notes";
                NotesFragment.subjectName = list.get(position).getSubjectName().toString();
            } else if (typeOfResources.equals("QuestionPaper")){
                FragmentActivity.fragment = "QuestionPaper";
                QuestionPaperFragment.subjectName = list.get(position).getSubjectName().toString();
            } else if (typeOfResources.equals("AnswerPaper")){
                FragmentActivity.fragment = "AnswerPaper";
                AnswerPaperFragment.subjectName = list.get(position).getSubjectName().toString();
            }

            context.startActivity(new Intent(context, FragmentActivity.class));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivSubjectImage;
        TextView tvSubjectName, tvSubjectDescribe;
        MaterialCardView cvSubject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSubjectImage = itemView.findViewById(R.id.ivSubjectImage);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvSubjectDescribe = itemView.findViewById(R.id.tvSubjectDescribe);
            cvSubject = itemView.findViewById(R.id.cvSubject);

        }
    }

}
