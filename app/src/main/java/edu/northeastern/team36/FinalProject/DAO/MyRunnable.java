package edu.northeastern.team36.FinalProject.DAO;

import com.google.gson.JsonObject;

public interface MyRunnable extends Runnable {
    public MyRunnable setParam(JsonObject param);
}
