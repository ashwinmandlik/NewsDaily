package com.atriasoftwaresolutions.newsdaily;

import com.atriasoftwaresolutions.newsdaily.Models.Newsheadlines;

import java.util.List;

public interface OnfetchDataListener<NewsApiResponse>{
    void onFetchData(List<Newsheadlines> list, String message);
    void onError(String message);


}
