package kr.hs.emirim.w2027.project13_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list1;
    TextView texMusic;
    ProgressBar proBar;
    ArrayList<String> arrList;
    String selectedMusic;
    String musicPatch = Environment.getExternalStorageDirectory().getPath() + "/";
    MediaPlayer media;
    Button btnStart, btnStop, btnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MP3 Player");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        arrList = new ArrayList<String>();
        File[] listFiles = new File(musicPatch).listFiles();
        String fileName, extName;
        for(File file : listFiles){//enhanced for(개선된 for문)
            fileName = file.getName();
            extName = fileName.substring(fileName.length()-3);
            if(extName.equals("mp3"))
                arrList.add(fileName);
        }

        list1 = findViewById(R.id.list1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, arrList);
        list1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list1.setAdapter(adapter);
        list1.setItemChecked(0,true);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedMusic = arrList.get(position);
            }
        });
        selectedMusic = arrList.get(0); //리스트를 클릭하지 않았을 때 초기값

        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnPause = findViewById(R.id.btn_pause);
        texMusic = findViewById(R.id.text_music);
        proBar = findViewById(R.id.progress);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media = new MediaPlayer();
                try {
                    media.setDataSource(musicPatch + selectedMusic);
                    media.prepare();
                    media.start();
                    btnStart.setClickable(false);
                    btnStop.setClickable(true);
                    texMusic.setText(selectedMusic+" : ");
                    proBar.setVisibility(View.VISIBLE);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPause.getText().equals("일시정지")){
                    media.pause();
                    btnPause.setText("이어듣기");
                    proBar.setVisibility(View.INVISIBLE);
                }else if(btnPause.getText().equals("이어듣기")){
                    media.start();
                    btnPause.setText("일시정지");
                    proBar.setVisibility(View.VISIBLE);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.stop();
                media.reset();
                btnStart.setClickable(true);
                btnStop.setClickable(false);
                texMusic.setText("실행음악 중지 : ");
                proBar.setVisibility(View.INVISIBLE);
            }
        });


        btnStop.setClickable(false);
    }
}