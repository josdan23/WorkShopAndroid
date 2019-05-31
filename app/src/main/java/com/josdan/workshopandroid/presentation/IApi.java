package com.josdan.workshopandroid.presentation;

import android.content.Context;
import com.josdan.workshopandroid.data.IListenerApi;

public interface IApi {

    void loadData(Context context);

    void updateData(Context context);

    void setListener(IListenerApi listener);

}
