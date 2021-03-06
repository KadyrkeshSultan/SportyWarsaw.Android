package pl.sportywarsaw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.sportywarsaw.MyApplication;
import pl.sportywarsaw.R;
import pl.sportywarsaw.adapters.UsersRecyclerViewAdapter;
import pl.sportywarsaw.infrastructure.CustomCallback;
import pl.sportywarsaw.models.MeetingPlusModel;
import pl.sportywarsaw.models.UserModel;
import pl.sportywarsaw.services.MeetingsService;
import pl.sportywarsaw.utils.DividerItemDecoration;
import retrofit.Call;

/**
 * Created by Marcin Chudy on 07/01/2016.
 */
public class MeetingParticipantsFragment extends Fragment {
    private List<UserModel> participants = new ArrayList<>();
    private MeetingPlusModel model;
    @Inject MeetingsService service;
    private UsersRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public MeetingParticipantsFragment() {
    }

    public static MeetingParticipantsFragment newInstance(MeetingPlusModel model){
        MeetingParticipantsFragment fragment = new MeetingParticipantsFragment();
        Bundle args = new Bundle();
        args.putSerializable("model", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = (MeetingPlusModel) getArguments().getSerializable("model");
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings_participants, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.participants_list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        adapter = new UsersRecyclerViewAdapter(participants, recyclerView, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        loadParticipants();

        return view;
    }

    private void loadParticipants() {
        adapter.showProgressBar();
        final Call<List<UserModel>> call = service.getMeetingParticipants(model.getId());
        call.enqueue(new CustomCallback<List<UserModel>>(getActivity()) {
            @Override
            public void onSuccess(List<UserModel> models) {
                adapter.hideProgressBar();
                for (UserModel model : models) {
                    participants.add(model);
                    adapter.notifyItemInserted(participants.size());
                }
                adapter.setLoaded();
            }

            @Override
            public void always() {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
