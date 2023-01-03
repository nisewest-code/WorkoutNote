package com.example.workoutnote.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<BasicViewHolder<T>> {
    private List<T> list = new ArrayList<>();
    private Constructor<? extends BasicViewHolder<T>> ctor;
    private Callback callback;

    public RecyclerViewAdapter(Class<? extends BasicViewHolder<T>> impl, Callback callback){
        this.callback = callback;
        try {
            ctor = impl.getConstructor(ViewGroup.class, Callback.class);
        } catch (NoSuchMethodException e) {
            ctor = null;
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public BasicViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            return ctor.newInstance(parent, callback);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
        }
        return new BasicViewHolder<>(parent, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicViewHolder<T> holder, int position) {
        holder.setHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<T> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public List<T> getItems(){
        return  list;
    }
}
