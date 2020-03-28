package com.example.flappybird;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static int main_width; // мы считаем это высоту только один раз и потом она не меняеться
    static int main_height; // мы считаем это высоту только один раз и потом она не меняеться

    static int speed = 10; // скорость движения

    static int numberOfObstacles = 6;
    static ImageView[] obstacles = new ImageView[numberOfObstacles];

    // этот параметр будет изменяться в AcyncTask, а отображаться в UI отоке
    static int[] obstacles_x = new int[numberOfObstacles];
    static int[] obstacles_y = new int[numberOfObstacles];
    static int[] obstacles_rotation = new int[numberOfObstacles];

    static int[] obstacles_width = new int[numberOfObstacles];
    static int[] obstacles_height = new int[numberOfObstacles];

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // открываем приложение на весь экран
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        // вытаскиваем из layout все необходимые нам элементы
        obstacles[0] = findViewById(R.id.obstacle_0);
        obstacles[1] = findViewById(R.id.obstacle_1);
        obstacles[2] = findViewById(R.id.obstacle_2);
        obstacles[3] = findViewById(R.id.obstacle_3);
        obstacles[4] = findViewById(R.id.obstacle_4);
        obstacles[5] = findViewById(R.id.obstacle_5);

        // узнаём ширину и высоту экрана
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        main_width = size.x;
        main_height = size.y;
        Log.d("main_width = ", main_width + "");
        Log.d("main_height = ", main_height + "");

        // узнатём высоты и ширины всех препятствий
        for(int i = 0; i < obstacles.length; i++){
            obstacles[i].measure(0,0);
            obstacles_width[i] = obstacles[i].getMeasuredWidth();
            obstacles_height[i] = obstacles[i].getMeasuredHeight();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        new ObstacleAsyncTask(0, 4, 5, 1, 2, 3).execute();

    }

    static void draw(int number_of_obstacle){
        obstacles[number_of_obstacle].setTranslationX(obstacles_x[number_of_obstacle]);
        obstacles[number_of_obstacle].setTranslationY(obstacles_y[number_of_obstacle]);
        obstacles[number_of_obstacle].setRotation(obstacles_rotation[number_of_obstacle]);
    }


}
