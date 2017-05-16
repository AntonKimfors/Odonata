package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Quiz;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-16.
 */

public class storageUtils {



    public static void saveQuizList(Context context) {
        //TODO: Try saving the data

        ArrayList<Quiz> quizlist = KwizGeeQ.getInstance().getQuizList();

        Gson gson = new Gson();
        String jsonquizlist = gson.toJson(quizlist);

        try
        {
            File quizFile = new File(context.getFilesDir(), "quiz.txt");
            FileWriter fileWriter = new FileWriter(quizFile, false);
            BufferedWriter writer = new BufferedWriter((fileWriter));
            writer.write(jsonquizlist);
            writer.close();
        }
        catch (Exception e)
        {
            Log.e("Persistance", "Error saving file " + e.getMessage());
        }
    }

    public static void saveImage(Context context, String imageName){
        File fileDirectory = getFileDirectory(context);
        //File fileToWrite = new

    }


    public static File getFileDirectory(Context context) {
        String storageType = StorageType.PRIVATE_EXTERNAL; //Choose what sort of storage type

        if (storageType.equals(StorageType.INTERNAL)) {
            return context.getFilesDir(); //If internal, just return it
        } else {
            if (isExternalStorageWritable()) {
                if (storageType.equals((StorageType.PRIVATE_EXTERNAL))) {
                    return context.getExternalFilesDir(null); //get private externel files dir //TODO: Why is parameter null?
                } else {
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //Get public external file dir with standard "DIRECTORY_PICTURES"
                }
            } else {
                return context.getFilesDir(); //If not writable, return internal anyways
            }
        }
    }//end getFileDirectory


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }//end isExternalStorageWritable


}
