package com.xiongda.ndklearning;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//                println(getResult(10,5));
//                println(add(10,5));
        println(multi2(10,5));
        println(callMylibASub(10,5));
    }

    static {
        System.loadLibrary("mylib_c");
//                System.loadLibrary("mylib_a");
//                System.loadLibrary("mylib_b");
    }

//    public static void main(String[] argvs){
//        //        println(getResult(10,5));
        //        println(add(10,5));
//        println(multi(10,5));
//    }
//    public native int getResult(int x,int y);
//    public native int add(int x,int y);
    public native int multi(int x,int y);
    public native int multi2(int x,int y);
    public native int callMylibASub(int x,int y);

    private static void println(Object object){
        System.out.println(object);
    }
}
