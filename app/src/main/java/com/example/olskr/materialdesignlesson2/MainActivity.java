package com.example.olskr.materialdesignlesson2;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableAllSingle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "M1";
    //добавили стороннюю библиотеку butterknife и уменьшили код инициализации компонентов через аннотации
    @BindView(R.id.button) Button button;
    @BindView(R.id.edit_text_name)EditText editTextName;
    @BindView(R.id.edit_text_second_name) EditText editTextSecondName;
    @BindView(R.id.edit_text_password) EditText editTextPassword;
    //ак же можно и ресурсы, чтобы вместо etApplicationContext().getResources().getColor(R.color.colorAccent)
    // можно было писать просто color в (1)
    @BindColor(R.color.colorAccent) int color;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); //чтобы заработала библиотека butterknife
        initUI();
    }

    private void initUI(){
        initListener();
    }

    private void initListener(){
        //реализация Snackbar
        button.setOnClickListener(v -> {
            Snackbar                              //по нажатию кнопки выскакивает Snackbar
                    .make(v, "Email deleted", Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", view -> {
                        editTextName.setText("Andrey");}) // у Snackbar активируем кнопку
                    .setActionTextColor(color) //(1) задаем цвет текста у кнопки в Snackbar
                    .show();
        });

        button.setOnClickListener(v -> startRx());
    }

    private void startRx() {
        Random random = new Random();
       disposable = Observable
               .just(1,2,3,4,5,6,7,8)
               .doOnNext(it -> Log.d(TAG, "before: " + it))
               .map(integer -> integer * random.nextInt(3))
               .filter(integer -> integer > 12)
               .doOnNext(it -> Log.d(TAG, "after: " + it))
               .subscribe(
                       s -> {
                           Log.d(TAG, "startRx: " + s);
                       },
                       error -> {
                           Log.e(TAG, "error: " + error);
                       },
                       () -> Log.w(TAG, "complete: " ));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
