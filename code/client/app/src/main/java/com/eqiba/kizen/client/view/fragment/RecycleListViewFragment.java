package com.eqiba.kizen.client.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MyContent;
import com.eqiba.kizen.client.adpter.viewHolder.ActivityViewHolder;
import com.eqiba.kizen.client.adpter.viewHolder.MyViewHolder;
import com.eqiba.kizen.client.adpter.viewHolder.NewsViewHolder;
import com.eqiba.kizen.client.adpter.viewHolder.SessionViewHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class RecycleListViewFragment<T extends MyContent> extends Fragment {

    private final static String TYPE_FRAGMENT_ARG="sessionType";
    public enum TYPE_FRAGMENT{MESSAGE,ACTIVITY,NEWS}
    private MyContent.OnListFragmentInteractionListener mListener;

    private int fragmentLayoutId,itemLayoutId;
    private Class viewHolderClass;


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecycleListViewFragment newInstance(TYPE_FRAGMENT type) {
        RecycleListViewFragment fragment=null;
        switch (type)
        {
            case MESSAGE:{
                fragment=new MessageFragment();
                break;
            }
            case ACTIVITY:{
                fragment=new ActivityFragment();
                break;
            }
            case NEWS:{
                fragment=new NewsFragment();
            }
        }
        Bundle args = new Bundle();
        args.putSerializable(TYPE_FRAGMENT_ARG,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentLayoutId=R.layout.fragment_easy;

        switch ((TYPE_FRAGMENT)getArguments().getSerializable(TYPE_FRAGMENT_ARG))
        {
            case MESSAGE:
            {
                itemLayoutId=R.layout.item_session_list;
                viewHolderClass=SessionViewHolder.class;
                break;
            }
            case ACTIVITY:
            {
                itemLayoutId=R.layout.item_activity_list;
                viewHolderClass= ActivityViewHolder.class;
                break;
            }
            case NEWS:
            {
                itemLayoutId=R.layout.item_news_list;
                viewHolderClass= NewsViewHolder.class;
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(fragmentLayoutId, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyFragmentListAdapter(initContent(),mListener, viewHolderClass,itemLayoutId));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyContent.OnListFragmentInteractionListener) {
            mListener = (MyContent.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected abstract List<T> initContent();

    private class MyFragmentListAdapter<T extends MyContent,X extends MyViewHolder> extends RecyclerView.Adapter<MyViewHolder> {

        private final List<T> mValues;
        private final MyContent.OnListFragmentInteractionListener mListener;
        private Class<X>  viewHolderClass;
        private int resourceId;

        public MyFragmentListAdapter(List<T> items, MyContent.OnListFragmentInteractionListener listener, Class<X> clazz, int resourceId) {
            mValues = items;
            mListener = listener;
            viewHolderClass=clazz;
            this.resourceId=resourceId;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(resourceId, parent, false);

            try {
                MyViewHolder holder =viewHolderClass.getConstructor(new Class[]{View.class}).newInstance(view);
                return holder;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.setContent(mValues.get(position));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.getContent());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


    }

}
