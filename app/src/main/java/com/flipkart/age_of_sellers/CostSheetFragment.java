package com.flipkart.age_of_sellers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CostSheetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CostSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CostSheetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CostSheetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CostSheetFragment newInstance(String param1, String param2) {
        CostSheetFragment fragment = new CostSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CostSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cost_sheet, container, false);
// The detail Activity called via intent. Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();

        Button buttonOne = (Button) rootView.findViewById(R.id.homeButton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmenthome, new HomeFragment())
                        .commit();

            }
        });
        Button buttonTwo = (Button) rootView.findViewById(R.id.gameboard);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                getFragmentManager().beginTransaction()
                        .replace(R.id.dashboardfragment, new DashBoard())
                        .commit();

            }
        });
        Button buttonThree = (Button) rootView.findViewById(R.id.profile);
        buttonThree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                getFragmentManager().beginTransaction()
                        .replace(R.id.profilefragment, new ProfileFragment())
                        .commit();

            }
        });
        Button buttonFour = (Button) rootView.findViewById(R.id.gamestat);
        buttonFour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here

                getFragmentManager().beginTransaction()
                        .replace(R.id.costsheet, new CostSheetFragment())
                        .commit();

            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}