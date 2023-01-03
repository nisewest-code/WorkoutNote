package com.example.workoutnote.utils;

public class WorkoutNoteWebClientCreator {
    private String url;
    private WorkoutNoteWebClient.Callback callback;

    public WorkoutNoteWebClientCreator(String url, WorkoutNoteWebClient.Callback callback){
        this.url = url;
        this.callback = callback;
    }

    public WorkoutNoteWebClient createWebClient(){
        return  new WorkoutNoteWebClient(url, callback);
    }
}
